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
import com.inndex.car.personas.fragments.informacion.presenterautorizacion.IAutorizacionFragment;
import com.inndex.car.personas.fragments.informacion.presenterautorizacion.IPresenterAutorizacion;
import com.inndex.car.personas.fragments.informacion.presenterautorizacion.PresenterAutorizacion;

public class AutorizacionFragment extends Fragment implements IAutorizacionFragment {

    ImageButton btnBack;
    TextView titulo, bodyAutorizacion;
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

        btnBack.setOnClickListener(v -> Navigation.findNavController(v).navigateUp());
        titulo.setText("Autorización");

        iPresenterAutorizacion = new PresenterAutorizacion(this, requireContext());


        return root;
    }

    @Override
    public TextView creatTextviewBody() {
        return bodyAutorizacion;
    }
}