package com.inndex.car.personas.fragments.estaciones;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.inndex.car.personas.R;
import com.inndex.car.personas.database.DataBaseHelper;
import com.inndex.car.personas.model.Estaciones;
import com.inndex.car.personas.services.ILocationService;
import com.inndex.car.personas.services.IMapService;
import com.inndex.car.personas.services.InndexLocationService;
import com.inndex.car.personas.services.MapService;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

public class EstacionesMapFragment extends Fragment implements OnMapReadyCallback, ILocationService, IMapService {

    DrawerLayout drawer;
    GoogleMap mMap;
    private DataBaseHelper helper;
    private MapService mapService;
    private List<Estaciones> estaciones;
    private InndexLocationService inndexLocationService;
    private boolean myLocationZoomed;

    public EstacionesMapFragment() {
        // Required empty public constructor
    }

    public static EstacionesMapFragment newInstance(String param1, String param2) {
        EstacionesMapFragment fragment = new EstacionesMapFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_estaciones_map, container, false);


        drawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        FloatingActionButton fabMenu = v.findViewById(R.id.btn_menu);
        FloatingActionButton fabUbicacion = v.findViewById(R.id.fab_ubicacion);
        helper = OpenHelperManager.getHelper(getActivity(), DataBaseHelper.class);

        fabUbicacion.setOnClickListener(view -> mapService.mostrarUbicacion());
        fabMenu.setOnClickListener(c1 -> drawer.open());
        try {
            getAllStations();
        } catch (SQLException throwables) {
            Toast.makeText(getActivity(), "ERROR CONSULTANDO ESTACIONES", Toast.LENGTH_SHORT).show();
            throwables.printStackTrace();
        }
        inndexLocationService = new InndexLocationService(getActivity(), this, this);
        //inndexLocationService.init();
        checkGPSState();
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map1);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.e("HOME", "MAP READY");
        mMap = googleMap;
        mapService = new MapService(mMap, getContext(), this);
        if (estaciones.size() > 0) {
            mapService.setEstaciones(estaciones);
            mapService.addStations();
        }
        if (inndexLocationService.getMyLocation() != null) {
            mapService.setMyLocation(inndexLocationService.getMyLocation());
            mapService.mostrarUbicacion();
            myLocationZoomed = true;
        }
        //LatLng newPosition = new LatLng(10.466294112577513, -73.25580205076582);
        //googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(newPosition, 14));
    }


    @Override
    public void onLocationChanged(Location location) {
        this.mapService.setMyLocation(location);
        if(!myLocationZoomed) {
            this.mapService.mostrarUbicacion();
            myLocationZoomed = true;
        }
    }

    @Override
    public void onEstacionMarkerClick(Estaciones estacion) {

    }

    public void onMapMarkerSelected(int position) {

        if (this.inndexLocationService.getMyLocation() == null) {
            Toast.makeText(getActivity(), "NO SE PUEDE DETERMINAR TU UBICACIÓN", Toast.LENGTH_SHORT).show();
            return;
        }

 /*       FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        EstacionDetalleFragment detalleFragment = new EstacionDetalleFragment(estacionSelected, this, light,
                this.inndexLocationService.getMyLocation());
        transaction.add(R.id.fl_estacion_detalle_container, detalleFragment);
        transaction.commit();
        fabUbicacion.hide();
        layMenuInferior.setVisibility(View.GONE);
        layButtonsStationSelected.setVisibility(View.VISIBLE);
        layBtnIndicaciones.setVisibility(View.VISIBLE);
        layBtnVerServicios.setVisibility(View.VISIBLE);
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);*/

        //miFragment =
/*        getSupportFragmentManager().beginTransaction().replace(R.id.content_main, miFragment).commit();

        this.viewMap.setClickable(false);*/
    }

    private void getAllStations() throws SQLException {
        final Dao<Estaciones, Integer> dao = helper.getDaoEstaciones();
        estaciones = dao.queryForAll();
    }

    private void checkGPSState() {
        if (!inndexLocationService.getLocationManager().isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("El GPS esta desactivado").setCancelable(false).setPositiveButton("Activar", (dialog, which) -> {
                Intent enableGPSIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(enableGPSIntent);
            });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }



    private void gotToWaze() {
        Estaciones estacionSeleccionada = mapService.getEstacionSeleccionada();
        if (estacionSeleccionada != null) {
            try {
                Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + estacionSeleccionada.getLatitud() + "," + estacionSeleccionada.getLongitud());
                //Uri gmmIntentUri = Uri.parse("google.navigation:q=" + estacionSeleccionada.getLatitud() + "," + estacionSeleccionada.getLongitud());
                Intent intent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                startActivity(intent);
            } catch (ActivityNotFoundException ex) {
                // If Waze is not installed, open it in Google Play:
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.waze"));
                startActivity(intent);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e("ON","onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("ON","onDestroy");
    }
}