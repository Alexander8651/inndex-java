package com.inndex.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.inndex.R;
import com.inndex.enums.EEvents;
import com.inndex.shared.SharedViewModel;

public class HomeFragment extends Fragment {

    private HomeViewModel mViewModel;
    private NavController navController;

    private BottomNavigationView bottomNavigationView;

    public LinearLayout layButtonsStationSelected;
    public RelativeLayout layBtnNavegar;

    public RelativeLayout layBtnIndicaciones;

    private SharedViewModel sharedViewModel;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.home_fragment, container, false);
        layButtonsStationSelected = v.findViewById(R.id.lay_buttons_station_selected);
        bottomNavigationView = v.findViewById(R.id.bottomNavigation);
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

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(requireActivity(), R.id.fragContentApp);
        //navController.navigate(R.id.estacionesMapFragment);
        NavigationUI.setupWithNavController(bottomNavigationView,
                navController);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        sharedViewModel = new ViewModelProvider(getActivity()).get(SharedViewModel.class);
        sharedViewModel.getEvents().observe(getViewLifecycleOwner(),
                integer -> {
                    switch (EEvents.getEventsById(integer)) {
                        case ESTACION_MARKER_SELECTED:
                            bottomNavigationView.setVisibility(View.GONE);
                            layButtonsStationSelected.setVisibility(View.VISIBLE);
                            break;
                        case SHOW_ORIGINAL_MENU:
                            layButtonsStationSelected.setVisibility(View.GONE);
                            bottomNavigationView.setVisibility(View.VISIBLE);
                            break;
                    }
                });


    }

}