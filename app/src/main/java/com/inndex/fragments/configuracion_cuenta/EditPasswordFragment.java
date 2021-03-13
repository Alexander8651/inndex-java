package com.inndex.fragments.configuracion_cuenta;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.inndex.R;

public class EditPasswordFragment extends Fragment {

    private View view;
    private EditText newPass, newPassConfir;
    private Button guardarCamb;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_edit_password, container, false);

        newPass = view.findViewById(R.id.etPasswordNew);
        newPassConfir = view.findViewById(R.id.etPasswordNewConfir);
        guardarCamb = view.findViewById(R.id.btnGuardarCambiosContra);

        guardarCamb.setOnClickListener(v -> {
            String contraNueva = String.valueOf(newPass.getText());
            String contraNuevaConfir = String.valueOf(newPassConfir.getText());
            if (contraNueva.equals(contraNuevaConfir)) {
                Toast.makeText(view.getContext(), "Cambio exitoso", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(view.getContext(), "Por favor verifica que las contraseñas coincidan", Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }
}