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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            estacion = getArguments().getParcelable("estacionIs");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_editar_eds, container, false);

        LinearLayout llCombustiblesYHorarios = view.findViewById(R.id.combustible_y_horarios);
        ImageButton btnBack = view.findViewById(R.id.btnBack);
        TextView titulo = view.findViewById(R.id.tv_toolbar_titulo);
        TextView datosGenerales = view.findViewById(R.id.tv_datos_generales);
        TextView editar_eds_otro_servicios = view.findViewById(R.id.editar_eds_otro_servicios);
        bundle = new Bundle();
        bundle.putParcelable("estacionIs", estacion);

        titulo.setText("Editar EDS");

        btnBack.setOnClickListener(v ->
                Navigation.findNavController(v).navigateUp()
        );

        datosGenerales.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.action_editarEdsFragment_to_datosGeneralesFragment, bundle)
        );


        editar_eds_otro_servicios.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.action_editarEdsFragment_to_edsOtrosServiciosFragment, bundle)
        );

        llCombustiblesYHorarios.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_editarEdsFragment_to_combustibleYHorarioFragment, bundle));

        return view;
    }
}