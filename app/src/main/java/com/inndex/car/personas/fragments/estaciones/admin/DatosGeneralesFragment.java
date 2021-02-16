package com.inndex.car.personas.fragments.estaciones.admin;

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
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.inndex.car.personas.R;
import com.inndex.car.personas.fragments.estaciones.admin.presenterdatosGeneralesFragment.IPresenterDataGeneralFragment;
import com.inndex.car.personas.fragments.estaciones.admin.presenterdatosGeneralesFragment.PresenterDatosGeneralesFragment;
import com.inndex.car.personas.model.Estaciones;


public class DatosGeneralesFragment extends Fragment implements OnMapReadyCallback {

    private View view;
    private GoogleMap map;
    private MapView mapView;
    private Button btnActualizar, btnGuardarCambios;
    private float lat;
    private float lon;
    private ImageView img_estacion;
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
        mapView = view.findViewById(R.id.map_datos_generales);
        img_estacion = view.findViewById(R.id.img_estacion);
        spMarcaGaso = view.findViewById(R.id.spn_marca_gaso);
        btnGuardarCambios = view.findViewById(R.id.btn_guardar_cambios_eds);
        btnActualizar = view.findViewById(R.id.btn_actualizar);
        nombreEds = view.findViewById(R.id.et_nombre_eds);
        et_cel = view.findViewById(R.id.et_cel);
        et_direccion_eds = view.findViewById(R.id.et_direccion_eds);

        array = getResources().getStringArray(R.array.marcas_estaciones);

        iPresenterDataGeneralFragment = new PresenterDatosGeneralesFragment(requireContext());

        btnGuardarCambios.setBackgroundColor(Color.BLACK);
        btnGuardarCambios.setTextColor(Color.WHITE);
        btnActualizar.setBackgroundColor(Color.BLACK);
        btnActualizar.setTextColor(Color.WHITE);

        Log.d("tamañooas", String.valueOf(array.length));

        ArrayAdapter<CharSequence> adapterMarcaGaso = ArrayAdapter.createFromResource(this.getContext(), R.array.marcas_estaciones,
                android.R.layout.simple_spinner_item);
        adapterMarcaGaso.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spMarcaGaso.setAdapter(adapterMarcaGaso);

        if (estacion.getMarca() != null) {

            nombreEds.setText(estacion.getNombre());
            et_cel.setText(estacion.getTelefono());
            et_direccion_eds.setText(estacion.getDireccion());


            for (int i = 0; i < array.length; i++) {

                if (array[i].equals(estacion.getMarca())) {
                    spMarcaGaso.setSelection(i);

                }
            }
        }


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

        btnGuardarCambios.setOnClickListener(v -> {
            //Toast.makeText(view.getContext(), "Se han guardado los cambios", Toast.LENGTH_SHORT).show();
            estacion.setNombre(nombreEds.getText().toString());
            estacion.setDireccion(et_direccion_eds.getText().toString());
            estacion.setTelefono(et_cel.getText().toString());

            iPresenterDataGeneralFragment.actualizarDataGeneral(estacion, v);
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

        lat = (float)estacion.getLatitud();
        lon = (float)estacion.getLongitud();

        img_estacion.setOnClickListener(v -> {
            //Navigation.findNavController(v).navigate(R.id.action_datos_generales_to_fotoEdsFragment);
        });

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