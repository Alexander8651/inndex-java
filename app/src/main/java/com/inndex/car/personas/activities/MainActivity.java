package com.inndex.car.personas.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.inndex.car.personas.R;
import com.inndex.car.personas.database.DataBaseHelper;
import com.inndex.car.personas.fragments.DondeTanquearFragment;
import com.inndex.car.personas.fragments.InicioFragment;
import com.inndex.car.personas.fragments.combustible.IngresadoFragment;
import com.inndex.car.personas.fragments.configuracion_cuenta.ConfiguracionTabs;
import com.inndex.car.personas.fragments.configuracion_cuenta.NuevoVehiculo;
import com.inndex.car.personas.fragments.dondetanquear.DondeTanquearTabs;
import com.inndex.car.personas.fragments.estados.EstadosFragment;
import com.inndex.car.personas.fragments.historial.HistorialTabs;
import com.inndex.car.personas.fragments.recorridos.RecorridosDatos;
import com.inndex.car.personas.model.Estaciones;
import com.inndex.car.personas.model.Vehiculo;
import com.inndex.car.personas.places.EstacionesPlaces;
import com.inndex.car.personas.services.InndexLocationService;
import com.inndex.car.personas.services.MapService;
import com.inndex.car.personas.services.RecorridoService;
import com.inndex.car.personas.utils.Constantes;
import com.inndex.car.personas.utils.CustomProgressDialog;
import com.inndex.car.personas.utils.NavTypeFace;
import com.inndex.car.personas.utils.SetArrayValuesForInndex;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        OnMapReadyCallback, SetArrayValuesForInndex,
        DondeTanquearTabs.OnFragmentInteractionListener, InicioFragment.OnFragmentInteractionListener,
        HistorialTabs.OnFragmentInteractionListener, DondeTanquearFragment.OnFragmentInteractionListener,
        ConfiguracionTabs.OnFragmentInteractionListener, IngresadoFragment.OnFragmentInteractionListener,
        NuevoVehiculo.OnFragmentInteractionListener, EstadosFragment.OnFragmentInteractionListener,
        RecorridosDatos.OnFragmentInteractionListener {

    private DrawerLayout drawer;
    private NavigationView navigationView;

    private TextView tvDefaultPlaca;
    private AlertDialog alert = null;
    private CustomProgressDialog mCustomProgressDialog;
    private SharedPreferences myPreferences;
    private List<Estaciones> estaciones;
    private DataBaseHelper helper;
    private ImageButton btnBack;
    private Typeface bold;
    private View viewMap;
    private Estaciones estacionMasCercana;
    private EstacionesPlaces estacionesPlaces;
    private Fragment miFragment;
    private InicioFragment inicioFragment;

    public static MainActivity myInstance;

    private Timer timerInndexDeviceListener;
    private Integer tipoUsuario;
    private RecorridoService recorridoService;
    private long idUsuario;
    private Long idVehiculo;

    private MapService mapService;
    private InndexLocationService inndexLocationService;
    private String values;

    private String placa;
    private Toolbar toolbar;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        getWindow().setNavigationBarColor(Color.BLACK);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        //setStatusBarTranslucent(true);

        helper = OpenHelperManager.getHelper(MainActivity.this, DataBaseHelper.class);
        bold = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Bold.ttf");
        viewMap = findViewById(R.id.map);

        try {
            getAllStations();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        mapService = new MapService(MainActivity.this, this);
        mCustomProgressDialog = new CustomProgressDialog(this);
        mCustomProgressDialog.setCanceledOnTouchOutside(false);
        mCustomProgressDialog.setCancelable(false);
        inndexLocationService = new InndexLocationService(MainActivity.this);
        inndexLocationService.init();

        checkGPSState();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
        myPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        placa = myPreferences.getString(Constantes.DEFAULT_PLACA, "");
        idVehiculo = myPreferences.getLong(Constantes.DEFAULT_VEHICLE_ID, 0);
        idUsuario = myPreferences.getInt(Constantes.DEFAULT_USER_ID, 0);

        tipoUsuario = myPreferences.getInt("tipoUsuario", 8);

        if (values != null && !values.equals("")) {
            IntentFilter filter = new IntentFilter();
            filter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
            filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED);
            filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
//            this.registerReceiver(mReceiver, filter);
        }
        inicioFragment = new InicioFragment(bold, this);
        getSupportFragmentManager().beginTransaction().replace(R.id.content_main, inicioFragment).commit();
        initControls();

        try {
            estacionesPlaces = new EstacionesPlaces();
            LatLng newPosition;
            newPosition = new LatLng(Double.valueOf(myPreferences.getString("latitud", "0")),
                    Double.valueOf(myPreferences.getString("longitud", "0")));
            estacionMasCercana = estacionesPlaces.getEstacionMasCercana(newPosition, helper);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        /*if(recorridoService == null){
            recorridoService = new RecorridoService(MainActivity.this, helper, idUsuario, idUsuarioModeloCarro, placa);
            recorridoService.setModelHasTwoTanks(modelHasTwoTanks);
            try {
                recorridoService.completeUnCompletedRecorridos();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            Recorrido recorrido = recorridoService.getCurrentUnCompletedRecorrido(Constantes.SDF_DATE_ONLY.format(new Date()));
            if (recorrido != null){
                recorridoService.continueCurrentRecorrido(recorrido);
            } else {
                initRecorrido();
            }
        }*/
        /*db.collection("recorridos").document("rrr123").addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                Toast.makeText(MainActivity.this, "ALGO PASO OK", Toast.LENGTH_SHORT).show();
            }
        });*/
        myInstance = this;
        if (idVehiculo != null && idVehiculo > 0)
            initRecorrido();
    }

    private Integer getMaxValueFromAdquisitionArray() {

        Gson gson = new Gson();
        JsonArray jsonArray = gson.fromJson(values, JsonArray.class);
        JsonObject jsonValues = jsonArray.get(0).getAsJsonObject();
        List<Integer> listValues = new ArrayList<>();

        for (String key : jsonValues.keySet()) {
            listValues.add((int) jsonValues.get(key).getAsDouble());
        }

        if (listValues.size() > 0) {
            return Collections.max(listValues);
        } else {
            return 10;
        }
    }

    private void applyFontToMenuItem(MenuItem mi) {
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf");
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new NavTypeFace("", font), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        //mNewTitle.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER), 0, mNewTitle.length(), 0); Use this if you want to center the items
        mi.setTitle(mNewTitle);
    }

    /*@Override
    public void onBackPressed() {
        Toast.makeText(this, "BACK BUTTON PRESSED", Toast.LENGTH_SHORT).show();
        Fragment fragment = new InicioFragment(bold, this);
        getSupportFragmentManager().beginTransaction().replace(R.id.content_main, fragment).commit();
        btnBack.setVisibility(View.GONE);
        viewMap.setVisibility(View.VISIBLE);

    }*/

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        miFragment = null;
        boolean fragmentSeleccionado = false;
        if (id == R.id.nav_config) {
            miFragment = new ConfiguracionTabs(MainActivity.this);
            fragmentSeleccionado = true;
            viewMap.setVisibility(View.GONE);
            toolbar.setVisibility(View.VISIBLE);
        } else if (id == R.id.logout) {
            //logout();
        }
        if (fragmentSeleccionado) {
            btnBack.setVisibility(View.VISIBLE);
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main, miFragment).commit();
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapService.setmMap(googleMap);
        if (inndexLocationService.getMyLocation() != null) {
            mapService.setMyLocation(inndexLocationService.getMyLocation());
            mapService.mostrarUbicacion();
        }
        if (estaciones.size() > 0) {
            mapService.setEstaciones(estaciones);
            mapService.addStations();
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mapService.getmMap().setMyLocationEnabled(true);

            inndexLocationService.setLocationManager((LocationManager) getSystemService(Context.LOCATION_SERVICE));
            //locationManager.req
            //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            mapService.setMyLocation(inndexLocationService.getLocationManager().getLastKnownLocation(LocationManager.PASSIVE_PROVIDER));
            if (mapService.getMyLocation() != null) {
                LatLng newPosition = new LatLng(mapService.getMyLocation().getLatitude(), mapService.getMyLocation().getLongitude());
                SharedPreferences.Editor editor = myPreferences.edit();
                editor.putString("latitud", String.valueOf(mapService.getMyLocation().getLatitude()));
                editor.putString("longitud", String.valueOf(mapService.getMyLocation().getLongitude()));
                editor.apply();
                mapService.getmMap().animateCamera(CameraUpdateFactory.newLatLngZoom(newPosition, 14));
                //mMap.addMarker(new MarkerOptions().position(newPosition).flat(true).title("Mi ubicación"));
                EstacionesPlaces places = new EstacionesPlaces();
                mapService.getmMap().setMyLocationEnabled(true);
                try {
                    Estaciones estacionMasCercana = places.getEstacionMasCercana(newPosition, helper);
                    Gson gson = new Gson();

                } catch (SQLException e) {
                    Log.e("EXCEPCION", "No se pudo obtener la estacion mas cercana");
                    e.printStackTrace();
                }
            }
        } else {
            Log.e("PERMISOS", "HACE FALTA ALGUN PERMISO");
            ActivityCompat.requestPermissions(
                    MainActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    InndexLocationService.LOCATION_REQUEST_CODE);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        inndexLocationService.setLocationManager(null);
        //unregisterReceiver(startReceiver);
        //unregisterReceiver(stopReceiver);
        if (alert != null) {
            alert.dismiss();
        }
    }

    private void logout() {
        drawer.closeDrawers(); // Cerrar drawer
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(getResources().getString(R.string.logout));
        dialog.setMessage("¿Seguro que quiere Cerrar Sesión?");
        dialog.setPositiveButton("Aceptar", (dialogInterface, i) -> {

        });
        dialog.setNegativeButton("Cancelar", (dialogInterface, i) -> dialogInterface.cancel());
        dialog.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SuppressLint("SetTextI18n")
    private void initControls() {
        Typeface light = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf");

        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        View headerLayout = navigationView.getHeaderView(0);
        toolbar = findViewById(R.id.toolbar);

        TextView tvUsuario = headerLayout.findViewById(R.id.tvUsuario);
        tvDefaultPlaca = headerLayout.findViewById(R.id.tvDefaultPlaca);
        //tvDefaultState = headerLayout.findViewById(R.id.tvDefaultState);
        tvDefaultPlaca.setText(placa);
        tvUsuario.setText(myPreferences.getString("nombres", "") + " " +
                myPreferences.getString("apellidos", ""));
        navigationView.setNavigationItemSelectedListener(this);
        Menu m = navigationView.getMenu();

        for (int i = 0; i < m.size(); i++) {
            MenuItem mi = m.getItem(i);
            //for aapplying a font to subMenu ...
            SubMenu subMenu = mi.getSubMenu();
            if (subMenu != null && subMenu.size() > 0) {
                for (int j = 0; j < subMenu.size(); j++) {
                    MenuItem subMenuItem = subMenu.getItem(j);
                    applyFontToMenuItem(subMenuItem);
                }
            }
            //the method we have create in activity
            applyFontToMenuItem(mi);
        }
        btnBack = findViewById(R.id.btnBack2);
        btnBack.setVisibility(View.GONE);
        btnBack.setOnClickListener(v -> {
            Fragment fragment = new InicioFragment(bold, this);
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main, fragment).commit();
            btnBack.setVisibility(View.GONE);
            viewMap.setVisibility(View.VISIBLE);
            toolbar.setVisibility(View.GONE);
        });
    }

    private void getAllStations() throws SQLException {
        final Dao<Estaciones, Integer> dao = helper.getDaoEstaciones();
        estaciones = dao.queryForAll();
    }

    public void irDondeTanquear() {

        Fragment miFragment = null;
        boolean fragmentSeleccionado = false;

        miFragment = new DondeTanquearTabs(this);
        fragmentSeleccionado = true;
        //tvTitulo.setText("¿Donde Tanquear?");
        btnBack.setVisibility(View.VISIBLE);
        //tvTitulo.setVisibility(View.VISIBLE);
        viewMap.setVisibility(View.GONE);
        getSupportFragmentManager().beginTransaction().replace(R.id.content_main, miFragment).commit();
        /*
        tvTitulo.setVisibility(View.VISIBLE);
        tvTitulo.setText("¿Donde Tanquear?");
        viewMap.setVisibility(View.GONE);

        Fragment miFragment = new DondeTanquearFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.content_main, miFragment).commit();
        btnBack.setVisibility(View.VISIBLE);*/

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void updateLocation(Location myLocation) {
        //myLocation = location;
        mapService.setMyLocation(null);
        SharedPreferences.Editor editor = myPreferences.edit();
        editor.putString("latitud", String.valueOf(myLocation.getLatitude()));
        editor.putString("longitud", String.valueOf(myLocation.getLongitude()));
        editor.apply();
    }

    public DrawerLayout getDrawer() {
        return drawer;
    }

    public NavigationView getNavigationView() {
        return navigationView;
    }

    public Estaciones getEstacionMasCercana() {
        return estacionMasCercana;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (Integer.parseInt(android.os.Build.VERSION.SDK) > 5
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            Log.d("CDA", "onKeyDown Called");
            onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public List<Estaciones> getEstaciones() {
        return estaciones;
    }

    public SharedPreferences getMyPreferences() {
        return myPreferences;
    }

    public void irCambiarContrasena() {
    }

    public List<Estaciones> getEstacionesCercanas() throws SQLException {

        return estacionesPlaces.getEstacionesCercanas(new LatLng(Double.valueOf(myPreferences.getString("latitud", "0")),
                Double.valueOf(myPreferences.getString("longitud", "0"))), helper);
    }

    @Override
    public void setValues(String values) {
    }

    public void addNewStationToMap(Estaciones estacion) {
        estaciones.add(estacion);
        LatLng latLng = new LatLng(estacion.getLatitud(), estacion.getLongitud());
        mapService.getmMap().addMarker(new MarkerOptions().position(latLng).title(estacion.getMarca()).snippet(estacion.getDireccion()).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker)));
    }


    public void cancelTimers() {
        if (timerInndexDeviceListener != null) {
            timerInndexDeviceListener.cancel();
            timerInndexDeviceListener.purge();
        }
    }

    public ImageButton getBtnBack() {
        return btnBack;
    }

    public MapService getMapService() {
        return mapService;
    }

    public void setMapService(MapService mapService) {
        this.mapService = mapService;
    }

    public InndexLocationService getInndexLocationService() {
        return inndexLocationService;
    }

    private void checkGPSState() {

        if (!inndexLocationService.getLocationManager().isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("El GPS esta desactivado").setCancelable(false).setPositiveButton("Activar", (dialog, which) -> {
                Intent enableGPSIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(enableGPSIntent);
            });
            alert = builder.create();
            alert.show();
        }
    }

    //Método que detiene el recorrido actual, lo sube al servidor y empieza un nuevo recorrido
    public void stopRecorrido() {
        if (recorridoService != null) {
            recorridoService.pararRecorrido();
            inndexLocationService.setDistancia(0);
            recorridoService = null;
        }
    }

    public void initRecorrido() {
        if (idVehiculo == null || idVehiculo == 0)
            return;
        recorridoService = new RecorridoService(MainActivity.this, false,
                this.helper, idUsuario, idVehiculo, placa);
        inndexLocationService.setDistancia(0);
        recorridoService.initTimmers();
        Toast.makeText(MainActivity.this, "RECORRIDO INICIADO", Toast.LENGTH_SHORT).show();
    }

    public void upateDefaultVehicle(Vehiculo uhmc) {

        uhmc.setModeloCarros(null);
        myPreferences.edit().putString(Constantes.DEFAULT_BLUETOOTH_VALUE_ARRAY, uhmc.getValoresAdq()).apply();
        //myPreferences.edit().putInt(Constantes.DEFAULT_GAL_CANT, (int)uhmc.getModeloCarros().getGalones()).apply();
        myPreferences.edit().putString(Constantes.DEFAULT_BLUETOOTH_MAC, uhmc.getBluetoothMac()).apply();
        myPreferences.edit().putBoolean(Constantes.MODEL_HAS_TWO_TANKS, uhmc.getHasTwoTanks()).apply();
        myPreferences.edit().putLong(Constantes.DEFAULT_VEHICLE_ID, uhmc.getId()).apply();
//        myPreferences.edit().putLong("defaultModeloCarroId", uhmc.getModeloCarros().getId()).apply();
        myPreferences.edit().putString(Constantes.DEFAULT_PLACA, uhmc.getPlaca()).apply();
        idVehiculo = uhmc.getId();

        values = uhmc.getValoresAdq();

        String newPlaca = myPreferences.getString(Constantes.DEFAULT_PLACA, "");
        tvDefaultPlaca.setText(newPlaca);

        if (values != null && !values.equals("")) {

            IntentFilter filter = new IntentFilter();
            filter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
            filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED);
            filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
//            this.registerReceiver(mReceiver, filter);
        }
        //fireBaseRecorridosHelper = null;
        //fireBaseRecorridosHelper = new FireBaseRecorridosHelper(MainActivity.this,
        //      newPlaca);
        //fireBaseRecorridosHelper.setPlaca(newPlaca);
        //fireBaseRecorridosHelper.init();
        initRecorrido();
    }

    public RecorridoService getRecorridoService() {
        return recorridoService;
    }

    public static MainActivity getInstance() {
        return myInstance;
    }

    public CustomProgressDialog getmCustomProgressDialog() {
        return mCustomProgressDialog;
    }

    public void openSideMenu() {
        this.getDrawer().openDrawer(this.getNavigationView());
    }

    public void onMapPositionChange() {
        inicioFragment.onMapPositionChange();
    }

    public void onMapMarkerSelected() {
        inicioFragment.onMapMarkerSelected();
    }

    public void onChangeRouteButtonIcon() {
        inicioFragment.onChangeRouteButtonIcon();
    }
}
