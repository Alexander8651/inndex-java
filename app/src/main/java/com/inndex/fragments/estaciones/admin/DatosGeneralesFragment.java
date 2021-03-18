package com.inndex.fragments.estaciones.admin;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.inndex.R;
import com.inndex.database.DataBaseHelper;
import com.inndex.fragments.estaciones.admin.presenterdatosGeneralesFragment.IPresenterDataGeneralFragment;
import com.inndex.fragments.estaciones.admin.presenterdatosGeneralesFragment.PresenterDatosGeneralesFragment;
import com.inndex.model.Bancos;
import com.inndex.model.Estaciones;
import com.inndex.model.MarcaEstacion;
import com.inndex.utils.Constantes;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class DatosGeneralesFragment extends Fragment implements OnMapReadyCallback {

    private View view;
    private GoogleMap map;
    private MapView mapView, map_datos_generales_actualizar;
    private Button btnActualizar, btnGuardarCambios, btn_listo;
    private float lat;
    private float lon;
    //private ImageView img_estacion;
    private Spinner spMarcaGaso;
    private Estaciones estacion;
    private EditText nombreEds;
    String[] array;
    private EditText et_cel, et_direccion_eds;
    private IPresenterDataGeneralFragment iPresenterDataGeneralFragment;
    float zoom = 16F;
    private LatLng estacionPosition;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            estacion = getArguments().getParcelable("estacionIs");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_datos_generales, container, false);

        ImageButton btnBack = view.findViewById(R.id.btnBack);
        TextView titulo = view.findViewById(R.id.tv_toolbar_titulo);
        titulo.setText(R.string.datos_generales);

        mapView = view.findViewById(R.id.map_datos_generales);
        //img_estacion = view.findViewById(R.id.img_estacion);
        spMarcaGaso = view.findViewById(R.id.spn_marca_gaso);
        btnGuardarCambios = view.findViewById(R.id.btn_guardar_cambios_eds);
        btnActualizar = view.findViewById(R.id.btn_actualizar);
        nombreEds = view.findViewById(R.id.et_nombre_eds);
        et_cel = view.findViewById(R.id.et_cel);
        et_direccion_eds = view.findViewById(R.id.et_direccion_eds);
        DataBaseHelper helper = OpenHelperManager.getHelper(this.getContext(), DataBaseHelper.class);

        try {
            Dao<MarcaEstacion, Long> marcaEstacionLongDao = helper.getDaoMarcaEstacion();
            List<MarcaEstacion> marcaEstacionList = marcaEstacionLongDao.queryForAll();
            array = new String[marcaEstacionList.size()];
            for (int i = 0; i < marcaEstacionList.size(); i++) {
                array[i] = marcaEstacionList.get(i).getNombre();
            }
            ArrayAdapter<String> adapterMarcaGaso = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_spinner_item, array);
            adapterMarcaGaso.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spMarcaGaso.setAdapter(adapterMarcaGaso);
            spMarcaGaso.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    //Toast.makeText(parent.getContext(), "Seleccionaste: " + parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
                    String marca = (String) parent.getItemAtPosition(position);
                    estacion.setMarca(marca);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            if (estacion != null && estacion.getMarca() != null) {
                for (int i = 0; i < array.length; i++) {
                    if (array[i].equals(estacion.getMarca())) {
                        spMarcaGaso.setSelection(i);
                    }
                }
            }
        } catch (SQLException ex) {
            Toast.makeText(requireActivity(), "NO SE PUDO INICIALIZAR LA MARCA.", Toast.LENGTH_SHORT).show();
        }

        iPresenterDataGeneralFragment = new PresenterDatosGeneralesFragment(requireContext());

        btnGuardarCambios.setBackgroundColor(Color.BLACK);
        btnGuardarCambios.setTextColor(Color.WHITE);
        btnActualizar.setBackgroundColor(Color.BLACK);
        btnActualizar.setTextColor(Color.WHITE);

        initViewsData();

        btnGuardarCambios.setOnClickListener(this::guardarCambios);
        btnBack.setOnClickListener(v ->
                Navigation.findNavController(v).navigateUp()
        );
        btnActualizar.setOnClickListener(v -> showMapDialog());
        return view;
    }

    private void showMapDialog() {
        Log.d("meejecuto", "meejecuto");

        final AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        LayoutInflater inflater1 = LayoutInflater.from(requireContext());
        View view = inflater1.inflate(R.layout.dialogmapadatosgenerales, null);

        builder.setView(view);
        Dialog dialog = builder.show();

        MapView mMapView;
        MapsInitializer.initialize(getActivity());

        mMapView = view.findViewById(R.id.map_datos_generales_actualizar);
        FloatingActionButton fabEditarUbicacion = view.findViewById(R.id.fabEditarUbicacion);


        btn_listo = view.findViewById(R.id.btn_listo);
        mMapView.onCreate(null);
        mMapView.onResume();// needed to get the map to display immediately

        mMapView.getMapAsync(googleMap -> {
            LatLng centerMap = new LatLng(lat, lon);
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(centerMap, zoom));
            if (ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            googleMap.setMyLocationEnabled(true);
            mMapView.setClickable(false);

            googleMap.setOnCameraIdleListener(() ->
                    estacionPosition = googleMap.getCameraPosition().target
            );
            btn_listo.setOnClickListener(v1 -> {
                updateMap();
                dialog.dismiss();
            });
            fabEditarUbicacion.setOnClickListener(v -> {
                SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(Constantes.SHARED_PREFERENCES_FILE_KEY, MODE_PRIVATE);
                double latitud = Double.parseDouble(sharedPreferences.getString(Constantes.LATITUD_KEY, "0.0"));
                double longitud = Double.parseDouble(sharedPreferences.getString(Constantes.LONGITUD_KEY, "0.0"));
                LatLng latLng = new LatLng(latitud, longitud);
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
            });
        });
    }

    private void guardarCambios(View v) {





        String nombre = nombreEds.getText().toString();
        String direccion = et_direccion_eds.getText().toString();
        if (nombre.equals("")) {
            Toast.makeText(getContext(), "DEBE INGRESAR UN NOMBRE", Toast.LENGTH_SHORT).show();
            nombreEds.requestFocus();
            return;
        }

        if (direccion.equals("")) {
            Toast.makeText(getContext(), "DEBE INGRESAR UNA DIRECCIÓN", Toast.LENGTH_SHORT).show();
            et_direccion_eds.requestFocus();
            return;
        }


        estacion.setNombre(nombre);
        estacion.setDireccion(direccion);
        estacion.setTelefono(et_cel.getText().toString());
        if (estacionPosition != null) {
            estacion.setLatitud(estacionPosition.latitude);
            estacion.setLongitud(estacionPosition.longitude);
        }
        iPresenterDataGeneralFragment.actualizarDataGeneral(estacion, v);
    }

    private void updateMap() {
        if (estacionPosition != null) {
            lat = (float) estacionPosition.latitude;
            lon = (float) estacionPosition.longitude;
            onMapReady(map);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (mapView != null) {
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }
        if (estacion.getLatitud() == 0.0 && estacion.getLongitud() == 0.0) {
            SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(Constantes.SHARED_PREFERENCES_FILE_KEY, MODE_PRIVATE);
            double latitud = Double.parseDouble(sharedPreferences.getString(Constantes.LATITUD_KEY, "0.0"));
            double longitud = Double.parseDouble(sharedPreferences.getString(Constantes.LONGITUD_KEY, "0.0"));
            estacion.setLongitud(longitud);
            estacion.setLatitud(latitud);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        lat = (float) estacion.getLatitud();
        lon = (float) estacion.getLongitud();
        map = googleMap;
        map.clear();
        LatLng centerMap = new LatLng(lat, lon);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(centerMap, zoom));
        //map.addMarker(new MarkerOptions().position(centerMap).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_radio)));
        map.addMarker(new MarkerOptions().position(centerMap));
        mapView.setClickable(false);
    }

    private void initViewsData() {

        if (estacion != null) {
            nombreEds.setText(estacion.getNombre());
            et_cel.setText(estacion.getTelefono());
            et_direccion_eds.setText(estacion.getDireccion());
        }
    }
}