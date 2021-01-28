package com.inndex.car.personas.fragments.estaciones.admin;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

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


public class DatosGeneralesFragment extends Fragment implements OnMapReadyCallback {

    private View view;
    private GoogleMap map;
    private MapView mapView;
    private Button btnActualizar, btnGuardarCambios;
    private float lat;
    private float lon;
    private ImageView img_estacion;
    private Spinner spMarcaGaso;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        btnGuardarCambios.setBackgroundColor(Color.BLACK);
        btnGuardarCambios.setTextColor(Color.WHITE);
        btnActualizar.setBackgroundColor(Color.BLACK);
        btnActualizar.setTextColor(Color.WHITE);

        ArrayAdapter<CharSequence> adapterMarcaGaso = ArrayAdapter.createFromResource(this.getContext(),R.array.marcas_estaciones,
                android.R.layout.simple_spinner_item);
        adapterMarcaGaso.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spMarcaGaso.setAdapter(adapterMarcaGaso);

        spMarcaGaso.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //Toast.makeText(parent.getContext(), "Seleccionaste: " + parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnGuardarCambios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(view.getContext(), "Se han guardado los cambios", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (mapView != null){
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Navigation.findNavController(v).navigate(R.id.action_datos_generales_to_fijarUbicacionFragment);
            }
        });

        lat = 4.6043166F;//(float) estaciones.getLatitud();
        lon = -74.0651728F;//(float) estaciones.getLongitud();

        img_estacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Navigation.findNavController(v).navigate(R.id.action_datos_generales_to_fotoEdsFragment);
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        map = googleMap;

        map = googleMap;
        float zoom = 16F;
        LatLng centerMap = new LatLng(lat,lon);

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(centerMap,zoom));
        //map.addMarker(new MarkerOptions().position(centerMap).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_radio)));
        map.addMarker(new MarkerOptions().position(centerMap));
    }
}