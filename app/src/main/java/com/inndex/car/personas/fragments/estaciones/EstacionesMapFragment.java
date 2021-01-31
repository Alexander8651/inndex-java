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
import android.widget.EditText;
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

    private FragmentEstacionesMapBinding binding;

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

        sharedPreferences = getActivity().getSharedPreferences(Constantes.SHARED_PREFERENCES_FILE_KEY, MODE_PRIVATE);

        //testDistanceApi();

        double latitud = Double.parseDouble(sharedPreferences.getString(Constantes.LATITUD_KEY, "0.0"));
        double longitud = Double.parseDouble(sharedPreferences.getString(Constantes.LONGITUD_KEY, "0.0"));

        if (latitud != 0 && longitud != 0) {
            myLocation = new LatLng(latitud, longitud);
        }

        drawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        helper = OpenHelperManager.getHelper(getActivity(), DataBaseHelper.class);

        try {
            getAllStations();
        } catch (SQLException throwables) {
            Toast.makeText(getActivity(), "ERROR CONSULTANDO ESTACIONES", Toast.LENGTH_SHORT).show();
            throwables.printStackTrace();
        }
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
        EditText edtSearchPlaces = v.findViewById(R.id.editText_search);

        if (autocompleteFragment != null) {
            // Specify the types of place data to return.
            autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG));
            autocompleteFragment.setHint(getString(R.string.a_donde_vas));

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
            if (selectedPlace != null)
                mapService.drawRouteToPlace(selectedPlace.getLatLng());
        });
        binding.btnMenu.setOnClickListener(c1 -> drawer.open());
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
        } else if (myLocation != null) {
            Location temp = new Location(LocationManager.GPS_PROVIDER);
            temp.setLatitude(myLocation.latitude);
            temp.setLongitude(myLocation.longitude);
            mapService.setMyLocation(temp);
            mapService.mostrarUbicacion();
            myLocationZoomed = true;
        }
        //LatLng newPosition = new LatLng(10.466294112577513, -73.25580205076582);
        //googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(newPosition, 14));
    }

    @Override
    public void onLocationChanged(Location location) {
        this.mapService.setMyLocation(location);
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
        if (this.inndexLocationService.getMyLocation() == null) {
            Toast.makeText(getActivity(), "NO SE PUEDE DETERMINAR TU UBICACIÓN", Toast.LENGTH_SHORT).show();
            return;
        }

        Call<Estaciones> getEstacionById = MedidorApiAdapter.getApiService().getEstacionById(estacion.getId());
        getEstacionById.enqueue(new Callback<Estaciones>() {
            @Override
            public void onResponse(Call<Estaciones> call, Response<Estaciones> response) {

                if (response.isSuccessful()) {
                    float distancia = 2000;

                    Estaciones estResponse = response.body();
                    EstacionDetalleFragment miFragment = new EstacionDetalleFragment(estResponse, distancia, inndexLocationService.getMyLocation());
                    //viewMap.setVisibility(View.GONE);
                    /*LatLng myPosition = new LatLng(inndexLocationService.getMyLocation().getLatitude(),
                            inndexLocationService.getMyLocation().getLongitude());*/
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(Constantes.ESTACION_SELECCIONADA_KEY, estResponse);
                    bundle.putFloat("distancia", distancia);
                    miFragment.setArguments(bundle);
                    //verServiciosButtonClicked = true;
                    getActivity().getSupportFragmentManager().beginTransaction().add(R.id.fl_estacion_detalle_container, miFragment).commit();
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);
                    binding.fabUbicacion.hide();
                }
            }

            @Override
            public void onFailure(Call<Estaciones> call, Throwable t) {
                Toast.makeText(getActivity(), "Ocurrió un error consultando la estación.", Toast.LENGTH_SHORT).show();
            }
        });

         /*       FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        EstacionDetalleFragment detalleFragment = new EstacionDetalleFragment(estacionSelected, this, light,
                this.inndexLocationService.getMyLocation());
        transaction.add(R.id.fl_estacion_detalle_container, detalleFragment);
        transaction.commit();
        fabUbicacion.hide();
        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);*/

        //miFragment =
/*        getSupportFragmentManager().beginTransaction().replace(R.id.content_main, miFragment).commit();

        this.viewMap.setClickable(false);*/

        sharedViewModel.setEvents(EEvents.ESTACION_MARKER_SELECTED.getId());
    }

    @Override
    public void onRutaTrazada() {
        rutaTrazada = true;

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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        sharedViewModel = new ViewModelProvider(getActivity()).get(SharedViewModel.class);
        sharedViewModel.setEvents(EEvents.ESTACIONES_MAP_FRAGMENT_VISIBLE.getId());
        sharedViewModel.getHomeEvents().observe(getViewLifecycleOwner(), event -> {
            switch (EEvents.getEventsById(event)) {
                case NAVIGATE:
                    gotToWaze();
                    break;
                case DRAW_ROUTE:
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    mapService.drawSationRoute();
                    break;
                case BACK_BUTTON_PRESSED:
                    if (rutaTrazada) {
                        mapService.deleteMapPolylines();
                        rutaTrazada = false;
                        mapService.mostrarUbicacion();
                    } else if (mBottomSheetBehavior.getState() != BottomSheetBehavior.STATE_COLLAPSED) {
                        mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        sharedViewModel.setEvents(EEvents.ESTACIONES_MAP_FRAGMENT_GONE.getId());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("ON", "onDestroy");
    }
}