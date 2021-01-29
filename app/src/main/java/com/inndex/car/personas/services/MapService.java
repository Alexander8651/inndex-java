package com.inndex.car.personas.services;

import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.clustering.ClusterManager;
import com.inndex.car.personas.model.Estaciones;
import com.inndex.car.personas.renderer.EstacionRenderer;
import com.inndex.car.personas.rutas.DirectionFinder;
import com.inndex.car.personas.rutas.PasarUbicacion;
import com.inndex.car.personas.rutas.Route;
import com.inndex.car.personas.to.InndexMarkerItem;
import com.inndex.car.personas.utils.Constantes;

import java.util.ArrayList;
import java.util.List;

public class MapService implements PasarUbicacion,  GoogleMap.OnCameraMoveListener {

    private GoogleMap mMap;
    private Marker markerMyPosition;
    private List<Polyline> polylinePaths = new ArrayList<>();
    private Location myLocation;
    private Context context;
    //private MainActivity mainActivity;
    private List<Estaciones> estaciones;
    private ClusterManager<InndexMarkerItem> mClusterManager;
    private InndexMarkerItem itemStationSelected;
    IMapService imapService;
    public MapService(GoogleMap mMap, Context context, IMapService imapService) {
        this.mMap = mMap;
        this.context = context;
        this.imapService = imapService;
        initMap();
    }

    public void drawSationRoute() {
        if (itemStationSelected == null) {
            Toast.makeText(context, "DEBE SELECCIONAR UNA ESTACIÓN!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (myLocation != null) {

            LatLng latLng = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
            LatLng destino = new LatLng(itemStationSelected.getPosition().latitude, itemStationSelected.getPosition().longitude);
            //return;
            DirectionFinder buscador = new DirectionFinder(this, latLng, destino,
                    Constantes.API_KEY_PLACES);
            buscador.peticionRutas();
        } else {
            Toast.makeText(context, "ERROR, No se pudo detectar tu ubicación", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void trazarRutas(List<Route> rutas) {

        if (rutas != null && rutas.size() > 0) {
            if (polylinePaths != null && polylinePaths.size() > 0) {
                polylinePaths.forEach(Polyline::remove);
            }
            polylinePaths = new ArrayList<>();
            for (Route route : rutas) {
                PolylineOptions polylineOptions = new PolylineOptions().
                        geodesic(true).
                        color(Color.BLACK).
                        width(6);

                for (int i = 0; i < route.points.size(); i++)
                    polylineOptions.add(route.points.get(i));
                polylinePaths.add(mMap.addPolyline(polylineOptions));
            }
            //mainActivity.onChangeRouteButtonIcon();
        }
    }

    public void mostrarUbicacion() {
        //initLocation();
        if (myLocation != null) {
            LatLng newPosition = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
            if (markerMyPosition == null)
                markerMyPosition = mMap.addMarker(new MarkerOptions().position(newPosition));
            if (mMap != null)
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(newPosition, 14));
        } else {
            Toast.makeText(context, "NO SE PUEDE MOSTRAR TU UBICACIÓN. INTENTALO MAS TARDE.", Toast.LENGTH_SHORT).show();
        }
    }

    public void mostrarUbicacionPlace(LatLng position, String placeName) {
        markerMyPosition = mMap.addMarker(new MarkerOptions().position(position).title(placeName));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position, 14));
    }

    public GoogleMap getmMap() {
        return mMap;
    }

    public void setmMap(GoogleMap mMap) {
        this.mMap = mMap;
        initMap();
    }

    private void initMap() {
        UiSettings uiSettings = this.mMap.getUiSettings();
        uiSettings.setAllGesturesEnabled(true);
        uiSettings.setMapToolbarEnabled(false);
        uiSettings.setMyLocationButtonEnabled(false);
//        mClusterManager.getMarkerCollection().setInfoWindowAdapter(new CustomInfoViewAdapter(LayoutInflater.from(mainActivity)));

//        mMap.setInfoWindowAdapter(mClusterManager.getMarkerManager());
        //this.mMap.setOnMarkerClickListener(this);
    }

    public void addStations() {
        mClusterManager = new ClusterManager<>(context, mMap);
        mClusterManager.setRenderer(new EstacionRenderer(context, mMap, mClusterManager));
        this.mMap.setOnMarkerClickListener(mClusterManager);
        this.mMap.setOnCameraIdleListener(mClusterManager);
        int sum = 0;
        for (Estaciones estacion :
                estaciones) {
            LatLng latLng = new LatLng(estacion.getLatitud(), estacion.getLongitud());
            InndexMarkerItem item = new InndexMarkerItem(estacion.isCertificada(), latLng, estacion.getMarca(), estacion.getDireccion(),
                    estacion.getId(), sum);
            mClusterManager.addItem(item);
            /*
            if (!estacion.isCertificada())
                mapService.getmMap().addMarker(new MarkerOptions().position(latLng).title(estacion.getMarca())
                        .snippet(estacion.getDireccion()).icon(BitmapDescriptorFactory.fromResource(R.drawable.eds_sin_certificado)));
            else
                mapService.getmMap().addMarker(new MarkerOptions().position(latLng).title(estacion.getMarca())
                        .snippet(estacion.getDireccion()).icon(BitmapDescriptorFactory.fromResource(R.drawable.eds_certificada)));*/
            sum++;
        }
        mClusterManager.cluster();
        mClusterManager.setOnClusterItemClickListener(item -> {
            itemStationSelected = item;
            imapService.onEstacionMarkerClick(getEstacionSeleccionada());
            return false;
        });
    }

    public void updateMyPosition() {
        if (myLocation == null) {
            return;
        }
        LatLng newPosition = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
        if (markerMyPosition == null) {
            markerMyPosition = mMap.addMarker(new MarkerOptions().position(newPosition));
        } else {
            markerMyPosition.setPosition(newPosition);
        }
    }

    public Location getMyLocation() {
        return myLocation;
    }

    public void setMyLocation(Location myLocation) {
        this.myLocation = myLocation;
    }

    @Override
    public void onCameraMove() {
//        mainActivity.onMapPositionChange();
    }

    public List<Estaciones> getEstaciones() {
        return estaciones;
    }

    public void setEstaciones(List<Estaciones> estaciones) {
        this.estaciones = estaciones;
    }

    public Estaciones getEstacionSeleccionada() {
        if (this.itemStationSelected != null) {
            return this.estaciones.get(itemStationSelected.getPositionInList());
        }
        return null;
    }




}
