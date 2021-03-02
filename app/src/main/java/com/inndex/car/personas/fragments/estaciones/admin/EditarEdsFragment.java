package com.inndex.car.personas.fragments.estaciones.admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.inndex.car.personas.R;
import com.inndex.car.personas.model.Estaciones;

public class EditarEdsFragment extends Fragment {

    private Estaciones estacion;

    Bundle bundle;

    private boolean isNewStation = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            estacion = getArguments().getParcelable("estacionIs");
        }
        if (estacion == null) {
            estacion = new Estaciones();
            isNewStation = true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_editar_eds, container, false);

        ImageButton btnBack = view.findViewById(R.id.btnBack);
        TextView titulo = view.findViewById(R.id.tv_toolbar_titulo);

        LinearLayout llDatosGenerales = view.findViewById(R.id.llDatosGenerales);
        LinearLayout llCombustiblesYHorarios = view.findViewById(R.id.llCombustibleHorarios);
        LinearLayout llOtrosServicios = view.findViewById(R.id.llOtrosServicios);
        LinearLayout llPromociones = view.findViewById(R.id.llPromociones);

        if (isNewStation) {
            llCombustiblesYHorarios.setVisibility(View.GONE);
            llOtrosServicios.setVisibility(View.GONE);
            llPromociones.setVisibility(View.GONE);
        }

        //TextView editar_eds_otro_servicios = view.findViewById(R.id.editar_eds_otro_servicios);
        //TextView editar_eds_promociones = view.findViewById(R.id.tv_promociones);
        bundle = new Bundle();
        bundle.putParcelable("estacionIs", estacion);

        titulo.setText(R.string.editar_eds);

        btnBack.setOnClickListener(v ->
                Navigation.findNavController(v).navigateUp()
        );
        llDatosGenerales.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_editarEdsFragment_to_datosGeneralesFragment, bundle);
            afterNavigate();
        });
        llOtrosServicios.setOnClickListener(v ->
        {
            Navigation.findNavController(v).navigate(R.id.action_editarEdsFragment_to_edsOtrosServiciosFragment, bundle);
        });
        llPromociones.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.action_editarEdsFragment_to_promocionListFragment, bundle)
        );
        llCombustiblesYHorarios.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_editarEdsFragment_to_combustibleYHorarioFragment, bundle);
        });
        return view;
    }

    private void afterNavigate() {

        if (isNewStation) {
            onDestroy();
        }

    }
}