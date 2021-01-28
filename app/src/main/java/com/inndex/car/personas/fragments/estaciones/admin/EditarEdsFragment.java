package com.inndex.car.personas.fragments.estaciones.admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.inndex.car.personas.R;


public class EditarEdsFragment extends Fragment {

    private View view;
    private LinearLayout llDatosGenerales, llCombustiblesYHorarios;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_editar_eds, container, false);

        llDatosGenerales = view.findViewById(R.id.datos_generales);
        llCombustiblesYHorarios = view.findViewById(R.id.combustible_y_horarios);

        llDatosGenerales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Navigation.findNavController(v).navigate(R.id.action_editarEdsFragment_to_datosGeneralesFragment);
            }
        });

        llCombustiblesYHorarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Navigation.findNavController(v).navigate(R.id.action_editarEdsFragment_to_combustibleYHorarioFragment);
            }
        });




        return view;
    }
}