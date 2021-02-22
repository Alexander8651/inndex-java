package com.inndex.car.personas.fragments.estaciones.admin;

import android.graphics.Color;
import android.os.Bundle;
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
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.inndex.car.personas.R;
import com.inndex.car.personas.database.DataBaseHelper;
import com.inndex.car.personas.fragments.estaciones.admin.presenterdatosGeneralesFragment.IPresenterDataGeneralFragment;
import com.inndex.car.personas.fragments.estaciones.admin.presenterdatosGeneralesFragment.PresenterDatosGeneralesFragment;
import com.inndex.car.personas.model.Estaciones;
import com.inndex.car.personas.model.MarcaEstacion;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;


public class DatosGeneralesFragment extends Fragment implements OnMapReadyCallback {

    private View view;
    private GoogleMap map;
    private MapView mapView;
    private Button btnActualizar, btnGuardarCambios;
    private float lat;
    private float lon;
    //private ImageView img_estacion;
    private Spinner spMarcaGaso;
    private Estaciones estacion;
    private EditText nombreEds;
    String[] array;
    private EditText et_cel, et_direccion_eds;
    private IPresenterDataGeneralFragment iPresenterDataGeneralFragment;

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
            if (estacion.getMarca() != null) {
                for (int i = 0; i < array.length; i++) {
                    if (array[i].equals(estacion.getMarca())) {
                        spMarcaGaso.setSelection(i);
                    }
                }
            }
        }catch (SQLException ex) {
            Toast.makeText(requireActivity(), "NO SE PUDO INICIALIZAR LA MARCA.", Toast.LENGTH_SHORT).show();
        }

        iPresenterDataGeneralFragment = new PresenterDatosGeneralesFragment(requireContext());

        btnGuardarCambios.setBackgroundColor(Color.BLACK);
        btnGuardarCambios.setTextColor(Color.WHITE);
        btnActualizar.setBackgroundColor(Color.BLACK);
        btnActualizar.setTextColor(Color.WHITE);

        nombreEds.setText(estacion.getNombre());
        et_cel.setText(estacion.getTelefono());
        et_direccion_eds.setText(estacion.getDireccion());

        btnGuardarCambios.setOnClickListener(v -> {
            //Toast.makeText(view.getContext(), "Se han guardado los cambios", Toast.LENGTH_SHORT).show();
            estacion.setNombre(nombreEds.getText().toString());
            estacion.setDireccion(et_direccion_eds.getText().toString());
            estacion.setTelefono(et_cel.getText().toString());

            iPresenterDataGeneralFragment.actualizarDataGeneral(estacion, v);
        });
        btnBack.setOnClickListener(v -> {
            Navigation.findNavController(v).navigateUp();
        });


        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (mapView != null) {
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }

        btnActualizar.setOnClickListener(v -> {
            //Navigation.findNavController(v).navigate(R.id.action_datos_generales_to_fijarUbicacionFragment);

        });

        lat = (float) estacion.getLatitud();
        lon = (float) estacion.getLongitud();

        /*img_estacion.setOnClickListener(v -> {
            //Navigation.findNavController(v).navigate(R.id.action_datos_generales_to_fotoEdsFragment);
        });*/
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        float zoom = 16F;
        LatLng centerMap = new LatLng(lat, lon);

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(centerMap, zoom));
        //map.addMarker(new MarkerOptions().position(centerMap).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_radio)));
        map.addMarker(new MarkerOptions().position(centerMap));
        mapView.setClickable(false);
    }
}