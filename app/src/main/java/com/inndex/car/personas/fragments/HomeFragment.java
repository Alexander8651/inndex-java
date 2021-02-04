package com.inndex.car.personas.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.inndex.car.personas.R;
import com.inndex.car.personas.enums.EEvents;
import com.inndex.car.personas.shared.SharedViewModel;

public class HomeFragment extends Fragment {

    private HomeViewModel mViewModel;
    private NavController navController;

    public LinearLayout layMenuInferior;
    public LinearLayout layButtonsStationSelected;
    //public LinearLayout layLista;
    public RelativeLayout layBtnNavegar;
    public RelativeLayout layBtnVerServicios;

    public RelativeLayout layBtnIndicaciones;


    public ImageView imgBtnHome;
    public ImageView imgBtnFiltros;
    public ImageView imgBtnFavoritos;

    public TextView tvHome;
    public TextView tvFiltros;
    public TextView tvFavoritos;

    public View viewFirstDivision;
    public View viewSecondDivision;
    private SharedViewModel sharedViewModel;
    private static final int HOME_CLICKED = 1;
    private static final int FILTER_CLICKED = 2;
    private static final int FAVOURITES_CLICKED = 3;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.home_fragment, container, false);
        layButtonsStationSelected = v.findViewById(R.id.lay_buttons_station_selected);

        //layLista = v.findViewById(R.id.lay_lista);
        layBtnNavegar = v.findViewById(R.id.lay_btn_navegar);
        layBtnNavegar.setOnClickListener(view -> {
            sharedViewModel.setHomeEvents(EEvents.NAVIGATE.getId());
        });
        //layBtnVerServicios = v.findViewById(R.id.lay_btn_ver_servicios);

        layBtnIndicaciones = v.findViewById(R.id.lay_btn_indicaciones);
        layBtnIndicaciones.setOnClickListener(view -> {
            sharedViewModel.setHomeEvents(EEvents.DRAW_ROUTE.getId());
        });
        //layBtnReclamarAhora = v.findViewById(R.id.lay_btn_reclamar_ahora);

        //imgBtnHome.setOnClickListener(v1 -> onItemMenuClick(HOME_CLICKED));
        //imgBtnFavoritos.setOnClickListener(v1 -> onItemMenuClick(FAVOURITES_CLICKED));
        //imgBtnFiltros.setOnClickListener(v1 -> onItemMenuClick(FILTER_CLICKED));
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(requireActivity(), R.id.fragContentApp);
        navController.navigate(R.id.estacionesMapFragment);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //mViewModel = new ViewModelProvider(this).get(SharedViewModel.class);

        sharedViewModel = new ViewModelProvider(getActivity()).get(SharedViewModel.class);
        sharedViewModel.getEvents().observe(getViewLifecycleOwner(),
                integer -> {
                    switch (EEvents.getEventsById(integer)) {
                        case ESTACION_MARKER_SELECTED:
                            layMenuInferior.setVisibility(View.GONE);
                            layButtonsStationSelected.setVisibility(View.VISIBLE);
                            break;
                        case SHOW_ORIGINAL_MENU:
                            layButtonsStationSelected.setVisibility(View.GONE);
                            layMenuInferior.setVisibility(View.VISIBLE);
                            break;
                    }
                });
    }

    private void onItemMenuClick(int itemClicked) {

        switch (itemClicked) {
            case HOME_CLICKED:
                break;
            case FILTER_CLICKED:
                navController.navigate(R.id.estacionesFiltrosFragment);
                break;
            case FAVOURITES_CLICKED:
                navController.navigate(R.id.estacionesFavoritasFragment);
                break;
        }
    }
}