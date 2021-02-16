package com.inndex.car.personas.fragments.configuracion_cuenta;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.inndex.car.personas.R;
import com.inndex.car.personas.activities.LoginActivity;
import com.inndex.car.personas.utils.Constantes;

public class AcountConfFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_acount_conf, container, false);
        TextView tvTitulo = view.findViewById(R.id.tv_toolbar_titulo);
        tvTitulo.setText(getText(R.string.config));
        LinearLayout infoperso = view.findViewById(R.id.infoPersonal);
        LinearLayout layCloseSession = view.findViewById(R.id.layCloseSession);
        layCloseSession.setOnClickListener(v -> this.closeSession());
        infoperso.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.editProfileFragment));

        ImageButton btnBack = view.findViewById(R.id.btnBack);

        btnBack.setOnClickListener(v ->
                Navigation.findNavController(v).navigateUp()
        );
        return view;
    }

    private void closeSession() {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(Constantes.SHARED_PREFERENCES_FILE_KEY, Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().apply();
        Intent intent = new Intent(requireActivity(), LoginActivity.class);
        startActivity(intent);
        requireActivity().finish();
    }
}