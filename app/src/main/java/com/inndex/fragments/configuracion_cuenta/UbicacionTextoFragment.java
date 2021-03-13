package com.inndex.fragments.configuracion_cuenta;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.inndex.R;
import com.inndex.fragments.configuracion_cuenta.presenterubicaciontexto.IPresenterUbicacionTexto;
import com.inndex.fragments.configuracion_cuenta.presenterubicaciontexto.IUbicacionTextoFragment;
import com.inndex.fragments.configuracion_cuenta.presenterubicaciontexto.PresenterUbicacionTexto;

public class UbicacionTextoFragment extends Fragment implements IUbicacionTextoFragment {

    ImageButton btnBack;
    TextView titulo;
    TextView ubicacion_body;
    IPresenterUbicacionTexto iPresenterUbicacionTexto;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View root = inflater.inflate(R.layout.fragment_ubicacion_texto, container, false);

        ubicacion_body = root.findViewById(R.id.ubicacion_body);
        iPresenterUbicacionTexto = new PresenterUbicacionTexto(this, requireContext());

        btnBack = root.findViewById(R.id.btnBack);
        titulo = root.findViewById(R.id.tv_toolbar_titulo);

        btnBack.setOnClickListener(v -> Navigation.findNavController(v).navigateUp());

        titulo.setText("Ubicación");

        return root;
    }

    @Override
    public TextView crearTextviewBody() {
        return ubicacion_body;
    }
}