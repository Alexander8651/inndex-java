package com.inndex.activities.mainactivity;

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
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.inndex.R;
import com.inndex.activities.mainactivity.adapters.PlacesAdapter;
import com.inndex.database.DataBaseHelper;
import com.inndex.fragments.combustible.IngresadoFragment;
import com.inndex.fragments.compra.CompraFragment;
import com.inndex.fragments.configuracion_cuenta.NuevoVehiculo;
import com.inndex.fragments.estaciones.EstacionesFiltrosFragment;
import com.inndex.fragments.estaciones.EstacionesServiciosFragment;
import com.inndex.fragments.estaciones.EstacionesTabsFragment;
import com.inndex.fragments.recorridos.RecorridosDatos;
import com.inndex.model.Estaciones;
import com.inndex.model.Vehiculo;
import com.inndex.places.EstacionesPlaces;
import com.inndex.retrofit.MedidorApiAdapter;
import com.inndex.retrofit.responseapifoursquare.LocationResposePlaceFourSquare;
import com.inndex.services.InndexLocationService;
import com.inndex.services.MapService;
import com.inndex.services.RecorridoService;
import com.inndex.utils.Constantes;
import com.inndex.utils.CustomProgressDialog;
import com.inndex.utils.SetArrayValuesForInndex;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        OnMapReadyCallback, SetArrayValuesForInndex,
        IngresadoFragment.OnFragmentInteractionListener, NuevoVehiculo.OnFragmentInteractionListener,
        RecorridosDatos.OnFragmentInteractionListener, EstacionesTabsFragment.OnFragmentInteractionListener {

    private DrawerLayout drawer;
    private NavigationView navigationView;

    private TextView tvDefaultPlaca;
    private CustomProgressDialog mCustomProgressDialog;
    private SharedPreferences myPreferences;
    private DataBaseHelper helper;
    private View viewMap;
    private Estaciones estacionMasCercana;
    private EstacionesPlaces estacionesPlaces;
    private Fragment miFragment;
    //@BindView(R.id.linearCard)
    @BindView(R.id.card_places_search)
    public CardView cardSearchPlaces;

    private Timer timerInndexDeviceListener;
    private Integer tipoUsuario;
    private RecorridoService recorridoService;
    private long idUsuario;
    private Long idVehiculo;

    private MapService mapService;
    private String values;

    private String placa;
    private Toolbar toolbar;
    private Typeface light;

    private static final int HOME_CLICKED = 1;
    private static final int EDS_CLICKED = 2;
    private static final int STORE_CLICKED = 3;

    @BindView(R.id.img_btn_home)
    public ImageView imgBtnHome;
    @BindView(R.id.img_btn_eds)
    public ImageView imgBtnEds;
    @BindView(R.id.img_btn_favoritos)
    public ImageView imgBtnFavoritos;

    @BindView(R.id.btnBack2)
    public ImageView btnBack;
    //@BindView(R.id.tv_toolbar)
    //public TextView tvTitulo;


    @BindView(R.id.tv_home)
    public TextView tvHome;
    @BindView(R.id.tv_eds)
    public TextView tvEds;
    @BindView(R.id.tv_favoritos)
    public TextView tvFavoritos;
    @BindView(R.id.tv_toolbar_nombre_estacion)
    public TextView tvToolbarNombreEstacion;
    @BindView(R.id.menu_main_first_division)
    public View viewFirstDivision;
    @BindView(R.id.menu_main_second_division)
    public View viewSecondDivision;

    @BindView(R.id.fab_ubicacion)
    public FloatingActionButton fabUbicacion;
    //    @BindView(R.id.fab_navegacion)
//    public FloatingActionButton fabNavegacion;
    @BindView(R.id.btn_menu)
    public FloatingActionButton btnMenu;

    @BindView(R.id.lay_menu_inferior)
    public LinearLayout layMenuInferior;
    @BindView(R.id.lay_buttons_station_selected)
    public LinearLayout layButtonsStationSelected;
    @BindView(R.id.lay_lista)
    public LinearLayout layLista;
    @BindView(R.id.lay_btn_navegar)
    public RelativeLayout layBtnNavegar;
    @BindView(R.id.lay_btn_ver_servicios)
    public RelativeLayout layBtnVerServicios;

    @BindView(R.id.lay_btn_indicaciones)
    public RelativeLayout layBtnIndicaciones;
    @BindView(R.id.lay_buttons_confirmar_compra)
    public LinearLayout layButtonsConfirmarCompra;
    @BindView(R.id.lay_btn_reclamar_ahora)
    public LinearLayout layBtnReclamarAhora;

    @BindView(R.id.lay_btn_confirmar_compra)
    public RelativeLayout layBtnConfirmarCompra;
    

    private Presentador iPresentador;
    SearchView buscarlugar;
    private RecyclerView recycler;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

/*        recycler = findViewById(R.id.rv_places);
        iPresentador = new Presentador(this, recycler);
        buscarlugar = findViewById(R.id.buscar_lugar);

        buscarlugar.setOnCloseListener(this);
        buscarlugar.setFocusable(true);
        buscarlugar.setIconified(false);
        buscarlugar.clearFocus();

        buscarlugar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                iPresentador.getPlacesNear(query);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.length() > 3) {
                    iPresentador.getPlacesNear(newText);
                }
                return false;
            }
        });*/
        this.getWindow().setStatusBarColor(Color.TRANSPARENT);
        //setStatusBarTranslucent(true);
        helper = OpenHelperManager.getHelper(MainActivity.this, DataBaseHelper.class);
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
        
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
        myPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        placa = myPreferences.getString(Constantes.DEFAULT_PLACA, "");
        idVehiculo = myPreferences.getLong(Constantes.DEFAULT_VEHICLE_ID, 0);
        idUsuario = myPreferences.getInt(Constantes.DEFAULT_USER_ID, 0);

        tipoUsuario = myPreferences.getInt("tipoUsuario", 8);


        //inicioFragment = new InicioFragment(bold, this);
        //getSupportFragmentManager().beginTransaction().replace(R.id.content_main, inicioFragment).commit();
        initControls();

        
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
        if (idVehiculo != null && idVehiculo > 0)
            initRecorrido();

        ButterKnife.bind(this);
        imgBtnHome.setImageResource(R.drawable.home_gris);
        Typeface robotoRegular = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");
        tvToolbarNombreEstacion.setTypeface(light);

        tvFavoritos.setTypeface(robotoRegular);
        tvHome.setTypeface(robotoRegular);
        tvEds.setTypeface(robotoRegular);

        onItemMenuClick(HOME_CLICKED);
        
    }

    

    @OnClick(R.id.btnBack2)
    @Override
    public void onBackPressed() {
        toolbar.setVisibility(View.GONE);
        viewMap.setVisibility(View.VISIBLE);
        clickHome();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        miFragment = null;
        boolean fragmentSeleccionado = false;
        //if (id == R.id.nav_config) {
        //miFragment = new ConfiguracionTabs(MainActivity.this);
        //   fragmentSeleccionado = true;
        //   viewMap.setVisibility(View.GONE);
        //   toolbar.setVisibility(View.VISIBLE);

        //}
        if (id == R.id.logout) {
            //logout();
        }
        if (fragmentSeleccionado) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main, miFragment).commit();
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapService.setmMap(googleMap);
        
        

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mapService.getmMap().setMyLocationEnabled(true);

            inndexLocationService.setLocationManager((LocationManager) getSystemService(Context.LOCATION_SERVICE));
            mapService.setMyLocation(inndexLocationService.getLocationManager().getLastKnownLocation(LocationManager.PASSIVE_PROVIDER));
            mapService.getmMap().setMyLocationEnabled(true);
            if (mapService.getMyLocation() != null) {
                LatLng newPosition = new LatLng(mapService.getMyLocation().getLatitude(), mapService.getMyLocation().getLongitude());
                SharedPreferences.Editor editor = myPreferences.edit();
                editor.putString("latitud", String.valueOf(mapService.getMyLocation().getLatitude()));
                editor.putString("longitud", String.valueOf(mapService.getMyLocation().getLongitude()));
                editor.apply();
                mapService.getmMap().animateCamera(CameraUpdateFactory.newLatLngZoom(newPosition, 14));
                //mMap.addMarker(new MarkerOptions().position(newPosition).flat(true).title("Mi ubicaci??n"));
                EstacionesPlaces places = new EstacionesPlaces();
                try {
                    Estaciones estacionMasCercana = places.getEstacionMasCercana(newPosition, helper);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } else {
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
        dialog.setMessage("??Seguro que quiere Cerrar Sesi??n?");
        dialog.setPositiveButton("Aceptar", (dialogInterface, i) -> {

        });
        dialog.setNegativeButton("Cancelar", (dialogInterface, i) -> dialogInterface.cancel());
        dialog.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SuppressLint("SetTextI18n")
    private void initControls() {
        light = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf");

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
        /*mapService.getmMap().addMarker(new MarkerOptions().position(latLng)
                .title(estacion.getMarca()).snippet(estacion.getDireccion())
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker)));
        */
    }

    public void cancelTimers() {
        if (timerInndexDeviceListener != null) {
            timerInndexDeviceListener.cancel();
            timerInndexDeviceListener.purge();
        }
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

    //M??todo que detiene el recorrido actual, lo sube al servidor y empieza un nuevo recorrido
    public void stopRecorrido() {
        if (recorridoService != null) {
            recorridoService.pararRecorrido();
            inndexLocationService.setDistancia(0);
            recorridoService = null;
        }
    }

    

    public void upateDefaultVehicle(Vehiculo uhmc) {

        uhmc.setModeloCarros(null);
        //myPreferences.edit().putInt(Constantes.DEFAULT_GAL_CANT, (int)uhmc.getModeloCarros().getGalones()).apply();
        myPreferences.edit().putLong(Constantes.DEFAULT_VEHICLE_ID, uhmc.getId()).apply();
//        myPreferences.edit().putLong("defaultModeloCarroId", uhmc.getModeloCarros().getId()).apply();
        myPreferences.edit().putString(Constantes.DEFAULT_PLACA, uhmc.getPlaca()).apply();
        idVehiculo = uhmc.getId();

        //values = uhmc.getValoresAdq();

        String newPlaca = myPreferences.getString(Constantes.DEFAULT_PLACA, "");
        tvDefaultPlaca.setText(newPlaca);

        if (values != null && !values.equals("")) {

            IntentFilter filter = new IntentFilter();
            filter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
            filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED);
            filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
//            this.registerReceiver(mReceiver, filter);
        }
        initRecorrido();
    }

    public CustomProgressDialog getmCustomProgressDialog() {
        return mCustomProgressDialog;
    }

    @OnClick(R.id.btn_menu)
    public void openSideMenu() {
        this.getDrawer().openDrawer(this.getNavigationView());
    }

    

    public void onChangeRouteButtonIcon() {
//        inicioFragment.onChangeRouteButtonIcon();
    }

    @OnClick(R.id.img_btn_eds)
    public void goToEstacionesFiltros() {
        miFragment = new EstacionesFiltrosFragment(this, helper);
        viewMap.setVisibility(View.GONE);
        //        toolbar.setVisibility(View.VISIBLE);
        getSupportFragmentManager().beginTransaction().replace(R.id.content_main, miFragment).commit();
        onItemMenuClick(EDS_CLICKED);
        /*
        miFragment = new EstacionesTabsFragment(this);
        btnBack.setVisibility(View.VISIBLE);
        viewMap.setVisibility(View.GONE);
        toolbar.setVisibility(View.VISIBLE);
        getSupportFragmentManager().beginTransaction().replace(R.id.content_main, miFragment).commit();
        */
    }

    public void filtrarEstaciones() {
        viewMap.setVisibility(View.VISIBLE);
        //miFragment.onDestroy();
        //getSupportFragmentManager().beginTransaction().replace(R.id.content_main, miFragment).commit();
        getSupportFragmentManager().beginTransaction().remove(miFragment).commit();
//        toolbar.setVisibility(View.VISIBLE);
        onItemMenuClick(HOME_CLICKED);
    }

    private void onItemMenuClick(int itemClicked) {

        switch (itemClicked) {
            case HOME_CLICKED:
                imgBtnHome.setImageResource(R.drawable.home_negro);
                imgBtnEds.setImageResource(R.drawable.filtro_gris);
                imgBtnFavoritos.setImageResource(R.drawable.favorito_gris);
                tvEds.setTextColor(getResources().getColor(R.color.gris_menu_main, null));
                tvHome.setTextColor(getResources().getColor(R.color.colorPrimary, null));
                tvFavoritos.setTextColor(getResources().getColor(R.color.gris_menu_main, null));
                viewFirstDivision.setBackgroundColor(getResources().getColor(R.color.colorPrimary, null));
                viewSecondDivision.setBackgroundColor(getResources().getColor(R.color.colorPrimary, null));
                btnMenu.show();
                fabUbicacion.show();
                layLista.setVisibility(View.VISIBLE);
                layButtonsConfirmarCompra.setVisibility(View.GONE);
                layMenuInferior.setVisibility(View.VISIBLE);
                layBtnReclamarAhora.setVisibility(View.GONE);
                toolbar.setVisibility(View.GONE);
                layBtnVerServicios.setVisibility(View.GONE);
                layBtnNavegar.setVisibility(View.GONE);
                cardSearchPlaces.setVisibility(View.VISIBLE);
                break;
            case EDS_CLICKED:
                imgBtnHome.setImageResource(R.drawable.home_gris);
                imgBtnEds.setImageResource(R.drawable.filtro_negro);
                imgBtnFavoritos.setImageResource(R.drawable.favorito_gris);
                tvEds.setTextColor(getResources().getColor(R.color.colorPrimary, null));
                tvHome.setTextColor(getResources().getColor(R.color.gris_menu_main, null));
                tvFavoritos.setTextColor(getResources().getColor(R.color.gris_menu_main, null));
                viewFirstDivision.setBackgroundColor(getResources().getColor(R.color.colorPrimary, null));
                toolbar.setVisibility(View.VISIBLE);
                viewSecondDivision.setBackgroundColor(getResources().getColor(R.color.gris_menu_main, null));
                btnMenu.hide();
                fabUbicacion.hide();
                layLista.setVisibility(View.GONE);
                tvToolbarNombreEstacion.setText(getText(R.string.filtros));
                layBtnVerServicios.setVisibility(View.VISIBLE);
                cardSearchPlaces.setVisibility(View.GONE);
                break;
            case STORE_CLICKED:
                imgBtnHome.setImageResource(R.drawable.home_gris);
                imgBtnEds.setImageResource(R.drawable.eds_gris);
                imgBtnFavoritos.setImageResource(R.drawable.favorito_negro);
                tvEds.setTextColor(getResources().getColor(R.color.gris_menu_main, null));
                tvHome.setTextColor(getResources().getColor(R.color.gris_menu_main, null));
                tvFavoritos.setTextColor(getResources().getColor(R.color.colorPrimary, null));
                viewFirstDivision.setBackgroundColor(getResources().getColor(R.color.colorPrimary, null));
                viewSecondDivision.setBackgroundColor(getResources().getColor(R.color.gris_menu_main, null));
                btnMenu.show();
                fabUbicacion.show();
                layLista.setVisibility(View.VISIBLE);
                break;
        }
    }

    @OnClick(R.id.img_btn_home)
    public void clickHome() {
        if (miFragment != null)
            getSupportFragmentManager().beginTransaction().remove(miFragment).commit();

        if (viewMap.getVisibility() != View.VISIBLE)
            viewMap.setVisibility(View.VISIBLE);
        onItemMenuClick(HOME_CLICKED);
        this.viewMap.setClickable(true);
    }

    @OnClick(R.id.lay_btn_indicaciones)
    public void drawRoute() {
        this.mapService.drawSationRoute();
/*        if (miFragment != null)
            getSupportFragmentManager().beginTransaction().remove(miFragment).commit();
        clickHome();
        layButtonsStationSelected.setVisibility(View.GONE);
        layMenuInferior.setVisibility(View.VISIBLE);
        gotToWaze();*/
    }

    @OnClick(R.id.lay_btn_ver_servicios)
    public void onClickVerServicios() {
        Estaciones estacionSeleccionada = this.mapService.getEstacionSeleccionada();

        if (inndexLocationService.getMyLocation() == null) {

            Toast.makeText(MainActivity.this, "NO ES POSIBLE DETERMINAR TU UBICACI??N.", Toast.LENGTH_SHORT).show();
            return;
        }

        Call<Estaciones> getEstacionById = MedidorApiAdapter.getApiService().getEstacionById(estacionSeleccionada.getId());
        getEstacionById.enqueue(new Callback<Estaciones>() {
            @Override
            public void onResponse(Call<Estaciones> call, Response<Estaciones> response) {

                if (response.isSuccessful()) {

                    Estaciones estResponse = response.body();
                    miFragment = new EstacionesServiciosFragment(MainActivity.this, light);
                    viewMap.setVisibility(View.GONE);
                    LatLng myPosition = new LatLng(inndexLocationService.getMyLocation().getLatitude(),
                            inndexLocationService.getMyLocation().getLongitude());
                    float distancia = Constantes.getDistance(myPosition, estResponse.getCoordenadas());
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(Constantes.ESTACION_SELECCOINADA_KEY, estResponse);
                    bundle.putFloat("distancia", distancia);
                    miFragment.setArguments(bundle);
                    verServiciosButtonClicked = true;
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    btnMenu.hide();
                    layBtnVerServicios.setVisibility(View.GONE);
                    layBtnNavegar.setVisibility(View.VISIBLE);
                    cardSearchPlaces.setVisibility(View.GONE);

                    getSupportFragmentManager().beginTransaction().replace(R.id.content_main, miFragment).commit();
                }
            }

            @Override
            public void onFailure(Call<Estaciones> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Ocurri?? un error consultando la estaci??n.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.lay_btn_confirmar_compra)
    public void onClickConfirmarCompra() {
        layButtonsConfirmarCompra.setVisibility(View.GONE);
        layBtnIndicaciones.setVisibility(View.VISIBLE);
        layButtonsStationSelected.setVisibility(View.VISIBLE);
        layBtnReclamarAhora.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.lay_btn_reclamar_ahora)
    public void onClickReclamarAhora() {
        ((CompraFragment) miFragment).showNumeroIslasDialog();
    }

//    @OnClick(R.id.fab_navegacion)
//    public void onClickNavegacion() {
//    }




    //@Override
    public PlacesAdapter crearAdaptador(ArrayList<LocationResposePlaceFourSquare> myPlaces) {
        return null;
        //return new PlacesAdapter(myPlaces, this, buscarlugar, recycler);
    }

    //@Override
    public void inicializarAdaptadorRV(PlacesAdapter adaptador) {
        //recycler.setAdapter(adaptador);
    }

    //@Override
    public boolean onClose() {

        recycler.setVisibility(View.GONE);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                buscarlugar.setFocusable(true);
                buscarlugar.setIconified(false);
                buscarlugar.clearFocus();
            }
        }, 10);
        return false;
    }

    
}
