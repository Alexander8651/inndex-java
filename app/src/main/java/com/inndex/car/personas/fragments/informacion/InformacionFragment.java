package com.inndex.car.personas.fragments.informacion;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.inndex.car.personas.R;
public class InformacionFragment extends Fragment {

    ImageButton btnBack;
    TextView titulo;

    LinearLayout terminosConiciones;
    LinearLayout PoliticaPrivacidad;
    LinearLayout Autorizacion;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_informacion, container, false);

        terminosConiciones = root.findViewById(R.id.terminos_condiciones);
        PoliticaPrivacidad = root.findViewById(R.id.politica_privacidad);
        Autorizacion = root.findViewById(R.id.autorizacion_trataiento_datos);

        btnBack = root.findViewById(R.id.btnBack);
        titulo = root.findViewById(R.id.tv_toolbar_titulo);

        btnBack.setOnClickListener(v -> Navigation.findNavController(v).navigateUp());
        titulo.setText("Información");

        terminosConiciones.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_informacionFragment2_to_terminosYCondicionesFragment));
        PoliticaPrivacidad.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_informacionFragment2_to_politicaPrivacidadFragment));
        Autorizacion.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_informacionFragment2_to_autorizacionFragment));


        return root;
    }
}