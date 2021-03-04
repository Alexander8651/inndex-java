package com.inndex.car.personas.fragments.informacion;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.inndex.car.personas.R;
import com.inndex.car.personas.fragments.informacion.presenterpoliticasprivacidad.IPoliticasPrivacidadFragment;
import com.inndex.car.personas.fragments.informacion.presenterpoliticasprivacidad.IPresenterPolicas;
import com.inndex.car.personas.fragments.informacion.presenterpoliticasprivacidad.PresenterPoliticas;


public class PoliticaPrivacidadFragment extends Fragment implements IPoliticasPrivacidadFragment {

    ImageButton btnBack;
    TextView titulo, bodyPoliticas;
    IPresenterPolicas iPresenterPolicas;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_politica_privacidad, container, false);

        iPresenterPolicas = new PresenterPoliticas(this, requireContext());

        btnBack = root.findViewById(R.id.btnBack);
        titulo = root.findViewById(R.id.tv_toolbar_titulo);
        bodyPoliticas = root.findViewById(R.id.bodyPoliticas);

        btnBack.setOnClickListener(v -> Navigation.findNavController(v).navigateUp());
        titulo.setText("Políticas de Privacidad");


        return root;
    }

    @Override
    public TextView crearTextviewPoliticasPrivacidad() {
        return bodyPoliticas;
    }
}