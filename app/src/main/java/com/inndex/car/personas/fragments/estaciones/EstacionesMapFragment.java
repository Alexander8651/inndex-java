package com.inndex.car.personas.fragments.estaciones;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.gson.Gson;
import com.inndex.car.personas.R;
import com.inndex.car.personas.database.DataBaseHelper;
import com.inndex.car.personas.databinding.FragmentEstacionesMapBinding;
import com.inndex.car.personas.dto.distance.DistanceApiResponse;
import com.inndex.car.personas.enums.EEvents;
import com.inndex.car.personas.model.Estaciones;
import com.inndex.car.personas.retrofit.MedidorApiAdapter;
import com.inndex.car.personas.retrofit.distanceapi.DistanceApiAdapter;
import com.inndex.car.personas.services.ILocationService;
import com.inndex.car.personas.services.IMapService;
import com.inndex.car.personas.services.InndexLocationService;
import com.inndex.car.personas.services.MapService;
import com.inndex.car.personas.shared.SharedViewModel;
import com.inndex.car.personas.utils.Constantes;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class EstacionesMapFragment extends Fragment implements OnMapReadyCallback, ILocationService, IMapService {

    DrawerLayout drawer;
    GoogleMap mMap;
    private DataBaseHelper helper;
    private MapService mapService;
    private List<Estaciones> estaciones;
    private InndexLocationService inndexLocationService;
    private boolean myLocationZoomed;
    private BottomSheetBehavior mBottomSheetBehavior;
    boolean rutaTrazada;
    private SharedPreferences sharedPreferences;
    private SharedViewModel sharedViewModel;
    private LatLng myLocation;
    private Place selectedPlace;
    float distancia;
    private Estaciones estacionSeleccionada;

    private FragmentEstacionesMapBinding binding;
    private RelativeLayout status_api;

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

        status_api = v.findViewById(R.id.status_api);

        sharedPreferences = getActivity().getSharedPreferences(Constantes.SHARED_PREFERENCES_FILE_KEY, MODE_PRIVATE);
        estaciones = new ArrayList<>();
        //testDistanceApi();

        double latitud = Double.parseDouble(sharedPreferences.getString(Constantes.LATITUD_KEY, "0.0"));
        double longitud = Double.parseDouble(sharedPreferences.getString(Constantes.LONGITUD_KEY, "0.0"));

        if (latitud != 0 && longitud != 0) {
            myLocation = new LatLng(latitud, longitud);
        }

        drawer = getActivity().findViewById(R.id.drawer_layout);
        helper = OpenHelperManager.getHelper(getActivity(), DataBaseHelper.class);


        getAllStations();

        inndexLocationService = new InndexLocationService(getActivity(), this, this);
        //inndexLocationService.init();
        checkGPSState();
        View bottomSheet = v.findViewById(R.id.fl_estacion_detalle_container);
        mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_DRAGGING);
        mBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {
                if (BottomSheetBehavior.STATE_COLLAPSED == i) {
                    binding.fabUbicacion.show();
                    //binding.fabNavegacion.hide();
                    sharedViewModel.setEvents(EEvents.SHOW_ORIGINAL_MENU.getId());
                } else if (BottomSheetBehavior.STATE_EXPANDED == i) {
                }
            }

            @Override
            public void onSlide(@NonNull View view, float v) {

            }
        });
        if (!Places.isInitialized()) {
            Places.initialize(getActivity(), Constantes.API_KEY_PLACES, Locale.US);
        }

        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getChildFragmentManager().findFragmentById(R.id.autocompleteFragment);
        //EditText edtSearchPlaces = v.findViewById(R.id.editText_search);

        if (autocompleteFragment != null) {
            // Specify the types of place data to return.
            autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG));
            //autocompleteFragment.setHint(getString(R.string.a_donde_vas));

            // Set up a PlaceSelectionListener to handle the response.
            autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
                @Override
                public void onPlaceSelected(@NotNull Place place) {
                    autocompleteFragment.setText(place.getName());
                    selectedPlace = place;
                    if (place.getLatLng() != null && mapService.getmMap() != null) {
                        binding.fabNavegacion.show();
                        mapService.mostrarUbicacionPlace(place.getLatLng(), place.getName());
                    }
                }

                @Override
                public void onError(@NotNull Status status) {
                    // TODO: Handle the error.
                    Log.i("PLACE", "An error occurred: " + status);
                }
            });
        }
        return v;
    }

    private void testDistanceApi() {
        Call<DistanceApiResponse> getDistance = DistanceApiAdapter.getApiService().getDistanceBetween("10.465923313985513,-73.24876001986274",
                "10.448584597414639,-73.28423192393828", Constantes.API_KEY_PLACES);
        getDistance.enqueue(new Callback<DistanceApiResponse>() {
            @Override
            public void onResponse(Call<DistanceApiResponse> call, Response<DistanceApiResponse> response) {
                Log.e("RESPONSE", "RES " + response.code());
                if (response.isSuccessful()) {
                    DistanceApiResponse res = response.body();
                    if (res != null)
                        Log.e("DIS", "DISTANMCE IS " + res.getDistanceValue());
                    else
                        Log.e("DIS", "SOMETHING HAPPEND");

                } else {
                    Log.e("RESPONSE", "NOT SUCCESSFULL " + response.code());
                }
            }

            @Override
            public void onFailure(Call<DistanceApiResponse> call, Throwable t) {
                Log.e("FAILURE", "EX " + t.getMessage());
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentEstacionesMapBinding.bind(view);
        binding.fabUbicacion.setOnClickListener(v -> mapService.mostrarUbicacion());
        binding.fabNavegacion.setOnClickListener(v -> {
            if (selectedPlace != null) {
                gotToWaze(selectedPlace.getLatLng());
            } else if (estacionSeleccionada != null) {
                gotToWaze(estacionSeleccionada.getCoordenadas());
            }
        });
        binding.btnMenu.setOnClickListener(c1 -> drawer.open());
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map1);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mapService = new MapService(mMap, getContext(), this);
        if (estaciones.size() > 0) {
            initCluster();
        }
        if (inndexLocationService.getMyLocation() != null) {
            mapService.setMyLocation(inndexLocationService.getMyLocation());
            mapService.mostrarUbicacion();
            myLocationZoomed = true;
        } else if (myLocation != null) {
            Location temp = new Location(LocationManager.GPS_PROVIDER);
            temp.setLatitude(myLocation.latitude);
            temp.setLongitude(myLocation.longitude);
            mapService.setMyLocation(temp);
            mapService.mostrarUbicacion();
            myLocationZoomed = true;
        }
        sharedViewModel.setEvents(EEvents.ESTACIONES_MAP_FRAGMENT_VISIBLE.getId());
        //LatLng newPosition = new LatLng(10.466294112577513, -73.25580205076582);
        //googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(newPosition, 14));
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location == null) {
            Toast.makeText(getActivity(), "LOCATION NULL", Toast.LENGTH_SHORT).show();
            return;
        }

        getAllStations();
        this.mapService.setMyLocation(location);
        this.myLocation = new LatLng(location.getLatitude(), location.getLongitude());
        if (!myLocationZoomed) {
            this.mapService.mostrarUbicacion();
            myLocationZoomed = true;
        }
        if (location != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(Constantes.LATITUD_KEY, String.valueOf(location.getLatitude()));
            editor.putString(Constantes.LONGITUD_KEY, String.valueOf(location.getLongitude()));
            editor.apply();
            editor.commit();
        }
    }

    @Override
    public void onEstacionMarkerClick(Estaciones estacion) {
        if (this.myLocation == null) {
            Toast.makeText(getActivity(), "NO SE PUEDE DETERMINAR TU UBICACIÓN", Toast.LENGTH_SHORT).show();
            return;
        }

        if (estacion == null) {
            Toast.makeText(getActivity(), "Estacion nula", Toast.LENGTH_SHORT).show();
            return;
        }

        //this.myLocation = new LatLng(inndexLocationService.getMyLocation().getLatitude(), inndexLocationService.getMyLocation().getLongitude());

        try {
            String origins = this.myLocation.latitude + "," + this.myLocation.longitude;
            String destination = estacion.getLatitud() + "," + estacion.getLongitud();

            //openStationBottomSheet(estacion);
            calculateDistance(origins, destination, estacion);

        } catch (Exception ex) {
            Toast.makeText(getActivity(), "EXCEPTION " + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
        //openStationBottomSheet(estacion);
    }

    private void calculateDistance(String origins, String destination, Estaciones estacion) {
        status_api.setVisibility(View.VISIBLE);
        Call<DistanceApiResponse> getDistance = DistanceApiAdapter.getApiService().getDistanceBetween(origins,
                destination, Constantes.API_KEY_PLACES);
        getDistance.enqueue(new Callback<DistanceApiResponse>() {
            @Override
            public void onResponse(Call<DistanceApiResponse> call, Response<DistanceApiResponse> response) {
                if (response.isSuccessful()) {
                    status_api.setVisibility(View.GONE);
                    DistanceApiResponse res = response.body();
                    if (res != null) {

                        distancia = (float) res.getDistanceValue();
                        openStationBottomSheet(estacion);
                    }

                } else {
                    Log.e("RESPONSE", "NOT SUCCESSFULL " + response.code());
                }
            }

            @Override
            public void onFailure(Call<DistanceApiResponse> call, Throwable t) {
                Log.e("FAILURE", "EX " + t.getMessage());
            }
        });

    }

    private void openStationBottomSheet(Estaciones estacion) {
        estacionSeleccionada = estacion;
        Call<Estaciones> getEstacionById = MedidorApiAdapter.getApiService().getEstacionById(estacion.getId());
        getEstacionById.enqueue(new Callback<Estaciones>() {
            @Override
            public void onResponse(Call<Estaciones> call, Response<Estaciones> response) {

                if (response.isSuccessful()) {
                    try {
                        Estaciones estResponse = response.body();
                        EstacionDetalleFragment miFragment = new EstacionDetalleFragment(estResponse, distancia, myLocation);
                        //viewMap.setVisibility(View.GONE);
                    /*LatLng myPosition = new LatLng(inndexLocationService.getMyLocation().getLatitude(),
                            inndexLocationService.getMyLocation().getLongitude());*/
                        //verServiciosButtonClicked = true;

                        if (getActivity() != null) {
                            getActivity().getSupportFragmentManager().beginTransaction().add(R.id.fl_estacion_detalle_container, miFragment).commit();
                            mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);
                            binding.fabUbicacion.hide();
                            binding.fabNavegacion.hide();
                            sharedViewModel.setEvents(EEvents.ESTACION_MARKER_SELECTED.getId());
                        }
                    } catch (Exception ex) {
                        Toast.makeText(getActivity(), "ERROR EX " + ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Estaciones> call, Throwable t) {
                Toast.makeText(getActivity(), "estación " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRutaTrazada() {
        rutaTrazada = true;
    }

    @Override
    public void goToStreetView(String location) {
        //46.414382,10.013988
        Uri gmmIntentUri = Uri.parse("google.streetview:cbll=" + location);

// Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
// Make the Intent explicit by setting the Google Maps package
        mapIntent.setPackage("com.google.android.apps.maps");

// Attempt to start an activity that can handle the Intent
        startActivity(mapIntent);
    }

    private void getAllStations() {
        try {
            final Dao<Estaciones, Integer> dao = helper.getDaoEstaciones();
            if (!(estaciones.size() > 0)) {
                estaciones = dao.queryForAll();
            }
            if (estaciones.size() > 0 || myLocation == null)
                return;
            Call<List<Estaciones>> callGetStations = MedidorApiAdapter.getApiService().getEstacionesNearUser(myLocation.latitude, myLocation.longitude);
            callGetStations.enqueue(new Callback<List<Estaciones>>() {
                @Override
                public void onResponse(@NonNull Call<List<Estaciones>> call, @NonNull Response<List<Estaciones>> response) {
                    if (response.isSuccessful()) {
                        estaciones = response.body();
                        Gson gson = new Gson();
                        if (estaciones != null && estaciones.size() > 0) {
                            for (int i = 0; i < estaciones.size(); i++) {
                                estaciones.get(i).setJsonCombustibles(gson.toJson(estaciones.get(i).getListEstacionCombustibles()));
                            }
                            try {
                                dao.create(estaciones);
                                initCluster();
                            } catch (SQLException e1) {
                                Toast.makeText(getContext(), "Error en la base de datos.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else {
                        Toast.makeText(getContext(), "NO SE PUDIERON DESCARGAR LAS ESTACIONES INTENTALO MAS TARDE.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<List<Estaciones>> call, @NonNull Throwable t) {
                    Toast.makeText(getContext(), "NO SE PUDIERON DESCARGAR LAS ESTACIONES INTENTALO MAS TARDE.", Toast.LENGTH_SHORT).show();
                }
            });

        } catch (SQLException ex) {
            Toast.makeText(getContext(), "ERROR inicializando DAO.", Toast.LENGTH_SHORT).show();
        }

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

    private void gotToWaze(LatLng location) {
        if (location != null) {
            try {
                Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + location.latitude + "," + location.longitude);
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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        sharedViewModel = new ViewModelProvider(getActivity()).get(SharedViewModel.class);

        sharedViewModel.getHomeEvents().observe(getViewLifecycleOwner(), event -> {
            switch (EEvents.getEventsById(event)) {
                case NAVIGATE:
                    LatLng latLng = new LatLng(estacionSeleccionada.getLatitud(), estacionSeleccionada.getLongitud());
                    gotToWaze(latLng);
                    break;
                case DRAW_ROUTE:
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    mapService.drawSationRoute();
                    binding.fabNavegacion.show();
                    break;
                case BACK_BUTTON_PRESSED:
                    this.selectedPlace = null;
                    binding.fabNavegacion.hide();
                    if (rutaTrazada) {
                        mapService.deleteMapPolylines();
                        rutaTrazada = false;
                        mapService.mostrarUbicacion();
                        sharedViewModel.setEvents(EEvents.SHOW_ORIGINAL_MENU.getId());
                    } else if (mBottomSheetBehavior.getState() != BottomSheetBehavior.STATE_COLLAPSED) {
                        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                        sharedViewModel.setEvents(EEvents.SHOW_ORIGINAL_MENU.getId());
                    } else {
                        getActivity().finish();
                    }
                    break;
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    private void initCluster() {
        mapService.setEstaciones(estaciones);
        mapService.addStations();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        sharedViewModel.setEvents(EEvents.ESTACIONES_MAP_FRAGMENT_GONE.getId());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}