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
    public RelativeLayout layButtonsConfirmarCompra;

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
        layMenuInferior = v.findViewById(R.id.lay_menu_inferior);

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

        imgBtnHome = v.findViewById(R.id.img_btn_home);
        imgBtnFiltros = v.findViewById(R.id.img_btn_eds);
        imgBtnFavoritos = v.findViewById(R.id.img_btn_favoritos);

        viewFirstDivision = v.findViewById(R.id.menu_main_first_division);
        viewSecondDivision = v.findViewById(R.id.menu_main_second_division);

        tvHome = v.findViewById(R.id.tv_home);
        tvFiltros = v.findViewById(R.id.tv_filtros);
        tvFavoritos = v.findViewById(R.id.tv_favoritos);

        imgBtnHome.setOnClickListener(v1 -> onItemMenuClick(HOME_CLICKED));
        imgBtnFavoritos.setOnClickListener(v1 -> onItemMenuClick(FAVOURITES_CLICKED));
        imgBtnFiltros.setOnClickListener(v1 -> onItemMenuClick(FILTER_CLICKED));
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
                            layMenuInferior.setVisibility(View.VISIBLE);
                            layButtonsStationSelected.setVisibility(View.GONE);
                            break;
                    }
                });

    }


    private void onItemMenuClick(int itemClicked) {

        switch (itemClicked) {
            case HOME_CLICKED:
                layButtonsConfirmarCompra.setVisibility(View.GONE);
                layMenuInferior.setVisibility(View.VISIBLE);
                layBtnNavegar.setVisibility(View.GONE);
                navController.navigate(R.id.estacionesMapFragment);
                imgBtnHome.setImageResource(R.drawable.home_negro);
                imgBtnFiltros.setImageResource(R.drawable.filtro_gris);
                imgBtnFavoritos.setImageResource(R.drawable.favorito_gris);
                tvFiltros.setTextColor(getResources().getColor(R.color.gris_menu_main, null));
                tvHome.setTextColor(getResources().getColor(R.color.colorPrimary, null));
                tvFavoritos.setTextColor(getResources().getColor(R.color.gris_menu_main, null));
                viewFirstDivision.setBackgroundColor(getResources().getColor(R.color.colorPrimary, null));
                viewSecondDivision.setBackgroundColor(getResources().getColor(R.color.colorPrimary, null));
                break;
            case FILTER_CLICKED:
                imgBtnHome.setImageResource(R.drawable.home_gris);
                imgBtnFiltros.setImageResource(R.drawable.filtro_negro);
                imgBtnFavoritos.setImageResource(R.drawable.favorito_gris);
                tvFiltros.setTextColor(getResources().getColor(R.color.colorPrimary, null));
                tvHome.setTextColor(getResources().getColor(R.color.gris_menu_main, null));
                tvFavoritos.setTextColor(getResources().getColor(R.color.gris_menu_main, null));
                viewFirstDivision.setBackgroundColor(getResources().getColor(R.color.colorPrimary, null));
                viewSecondDivision.setBackgroundColor(getResources().getColor(R.color.gris_menu_main, null));
                navController.navigate(R.id.estacionesFiltrosFragment);
                break;
            case FAVOURITES_CLICKED:
                imgBtnHome.setImageResource(R.drawable.home_gris);
                imgBtnFiltros.setImageResource(R.drawable.filtro_gris);
                imgBtnFavoritos.setImageResource(R.drawable.favorito_negro);
                tvFiltros.setTextColor(getResources().getColor(R.color.gris_menu_main, null));
                tvHome.setTextColor(getResources().getColor(R.color.gris_menu_main, null));
                tvFavoritos.setTextColor(getResources().getColor(R.color.colorPrimary, null));
                viewSecondDivision.setBackgroundColor(getResources().getColor(R.color.colorPrimary, null));
                viewFirstDivision.setBackgroundColor(getResources().getColor(R.color.gris_menu_main, null));
                navController.navigate(R.id.estacionesFavoritasFragment);
                break;
        }
    }
}