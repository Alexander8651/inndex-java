package com.inndex.fragments.estaciones.admin;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.inndex.R;
import com.inndex.model.Estaciones;


public class FijarUbicacionFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap map;
    private MapView mapView;
    private Button btnListo;
    private View view;
    private Estaciones estaciones;
    private  float lat;
    private  float lon;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_fijar_ubicacion, container, false);

        mapView = view.findViewById(R.id.map_fijar_ubicacion);
        btnListo = view.findViewById(R.id.btn_fijar_ubicacion);


        btnListo.setBackgroundColor(Color.BLACK);
        btnListo.setTextColor(Color.WHITE);
        //estaciones.setLatitud((double) 4.6043166F);
        //estaciones.setLongitud((double)-74.0651728);



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

        btnListo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(view.getContext(), "Actualizado", Toast.LENGTH_SHORT).show();


            }
        });

        lat = 4.6043166F;//(float) estaciones.getLatitud();
        lon = -74.0651728F;//(float) estaciones.getLongitud();


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        float zoom = 16F;
        LatLng centerMap = new LatLng(lat,lon);

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(centerMap,zoom));
        //map.addMarker(new MarkerOptions().position(centerMap).icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_map_da_gene)).draggable(true));
        map.addMarker(new MarkerOptions().position(centerMap).draggable(true));

        new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {
                Log.i("MAPASINICIO", "Psicion" + marker.getPosition());
            }

            @Override
            public void onMarkerDrag(Marker marker) {
                Log.i("MAPASTRANS", "Psicion" + marker.getPosition());
            }

            @Override
            public void onMarkerDragEnd(Marker marker) {

                Log.i("MAPASFIN", "Psicion" + marker.getPosition());

            }
        };



    }
}