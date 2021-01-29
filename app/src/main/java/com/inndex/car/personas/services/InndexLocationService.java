package com.inndex.car.personas.services;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.inndex.car.personas.fragments.estaciones.EstacionesMapFragment;

public class InndexLocationService {

    private FusedLocationProviderClient mFusedLocationClient;
    private static final int locationRequestCode = 1000;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;

    private LocationManager locationManager;
    public static final int LOCATION_REQUEST_CODE = 1;
    private Location myLocation;
    private double distancia_temp = 0;
    private Context context;
    private double distancia;
    private ILocationService iLocationService;

    public InndexLocationService(Context context, ILocationService iLocationService, EstacionesMapFragment fragment) {
        this.locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        this.mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(15000);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {

                Log.e("ON", "LOCATION CALLBACK");
                if (locationResult == null) {
                    return;
                }
                if (myLocation == null) {
                    myLocation = locationResult.getLastLocation();
                    iLocationService.onLocationChanged(myLocation);
                    return;
                }

                if (locationResult.getLastLocation() != null) {
                    //mainActivity.updateLocation(myLocation);
                    distancia_temp = myLocation.distanceTo(locationResult.getLastLocation());
                    //mainActivity.getMapService().updateMyPosition();
                    //mainActivity.getMapService().setMyLocation(myLocation);
                    if (distancia_temp > 2) {
                        distancia += distancia_temp;
                    }
                    if (locationResult.getLastLocation() != null) {
                        myLocation = locationResult.getLastLocation();
                        iLocationService.onLocationChanged(myLocation);
                    }
                }
            }
        };
        if (ActivityCompat.checkSelfPermission(fragment.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(fragment.getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {


        } else {

            mFusedLocationClient.getLastLocation().addOnSuccessListener(location -> {
                Log.e("ON", "GET LAST LOCATION");

                if (location != null) {
                    myLocation = location;
                    //mainActivity.updateLocation(myLocation);
                }
            });
            mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
        }

    }


/*    public void init() {

        if (ActivityCompat.checkSelfPermission(this.context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this.context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // reuqest for permission
            ActivityCompat.requestPermissions(((EstacionesMapFragment)this.context).getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    locationRequestCode);
        } else {
            // already permission granted

        }
    }*/


    public LocationManager getLocationManager() {
        return locationManager;
    }

    public void setLocationManager(LocationManager locationManager) {
        this.locationManager = locationManager;
    }

    public Location getMyLocation() {
        return myLocation;
    }

    public void setMyLocation(Location myLocation) {
        this.myLocation = myLocation;
    }

    public double getDistancia_temp() {
        return distancia_temp;
    }

    public void setDistancia_temp(double distancia_temp) {
        this.distancia_temp = distancia_temp;
    }

    public double getDistancia() {
        return distancia;
    }

    public void setDistancia(double distancia) {
        this.distancia = distancia;
    }
}
