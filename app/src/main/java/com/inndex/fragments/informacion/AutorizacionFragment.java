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
import com.inndex.fragments.informacion.presenterautorizacion.IAutorizacionFragment;
import com.inndex.fragments.informacion.presenterautorizacion.IPresenterAutorizacion;
import com.inndex.fragments.informacion.presenterautorizacion.PresenterAutorizacion;

public class AutorizacionFragment extends Fragment implements IAutorizacionFragment {

    ImageButton btnBack;
    TextView titulo, bodyAutorizacion, autorizacionPolitica, autorizacionTerminos;
    IPresenterAutorizacion iPresenterAutorizacion;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_autorizacion, container, false);
        btnBack = root.findViewById(R.id.btnBack);
        titulo = root.findViewById(R.id.tv_toolbar_titulo);
        bodyAutorizacion = root.findViewById(R.id.bodyAutorizacion);
        autorizacionPolitica = root.findViewById(R.id.autorizacionPolitica);
        autorizacionTerminos = root.findViewById(R.id.autorizacionTerminos);

        btnBack.setOnClickListener(v -> Navigation.findNavController(v).navigateUp());
        titulo.setText("Autorización");

        iPresenterAutorizacion = new PresenterAutorizacion(this, requireContext());

        autorizacionPolitica.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_autorizacionFragment_to_politicaPrivacidadFragment));
        autorizacionTerminos.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_informacionFragment2_to_terminosYCondicionesFragment));
        return root;
    }

    @Override
    public TextView creatTextviewBody() {
        return bodyAutorizacion;
    }
}