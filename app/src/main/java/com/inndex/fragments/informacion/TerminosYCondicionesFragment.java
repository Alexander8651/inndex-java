package com.inndex.fragments.informacion;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.inndex.R;
import com.inndex.fragments.informacion.presenterterminos.IPresenterTerminosCondiciones;
import com.inndex.fragments.informacion.presenterterminos.ITerminosCondicionesFradment;
import com.inndex.fragments.informacion.presenterterminos.PresenterTerminosCondiciones;

public class TerminosYCondicionesFragment extends Fragment implements ITerminosCondicionesFradment {

    ImageButton btnBack;
    TextView titulo, bodyTerminos, headerTerminos, terminosPolitica, politicaAutorizacion;

    IPresenterTerminosCondiciones iPresenterTerminosCondiciones;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_terminos_y_condiciones, container, false);

        iPresenterTerminosCondiciones = new PresenterTerminosCondiciones(this, requireContext());

        btnBack = root.findViewById(R.id.btnBack);
        titulo = root.findViewById(R.id.tv_toolbar_titulo);
        headerTerminos = root.findViewById(R.id.headerTerminos);
        bodyTerminos = root.findViewById(R.id.bodyTerminos);
        terminosPolitica = root.findViewById(R.id.terminosPolitica);
        politicaAutorizacion = root.findViewById(R.id.politicaAutorizacion);


        btnBack.setOnClickListener(v -> Navigation.findNavController(v).navigateUp());
        titulo.setText("Términos y condiciones");

        headerTerminos.setText("CLAUSULAs DE USO DE LA PLATAFORMA \"Inndex\" (WEB - MOVIL - APLICACIONES \"ANDROID E IOS \") - TÉRMINOS Y CONDICIONES.");
        terminosPolitica.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_terminosYCondicionesFragment_to_politicaPrivacidadFragment));
        politicaAutorizacion.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_terminosYCondicionesFragment_to_autorizacionFragment));

        return root;
    }

    @Override
    public TextView crearTexviewBodyTerminos() {
        return bodyTerminos;
    }
}