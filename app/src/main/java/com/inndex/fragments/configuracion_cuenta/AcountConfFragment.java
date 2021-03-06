package com.inndex.fragments.configuracion_cuenta;

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

import com.inndex.R;
import com.inndex.activities.LoginActivity;
import com.inndex.utils.Constantes;

public class AcountConfFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_acount_conf, container, false);
        TextView tvTitulo = view.findViewById(R.id.tv_toolbar_titulo);
        tvTitulo.setText(getText(R.string.config));
        LinearLayout infoperso = view.findViewById(R.id.infoPersonal);
        LinearLayout layCloseSession = view.findViewById(R.id.layCloseSession);
        LinearLayout info_privacidad = view.findViewById(R.id.info_privacidad);
        layCloseSession.setOnClickListener(v -> this.closeSession());
        infoperso.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.editProfileFragment));

        info_privacidad.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.action_opt_configuracion_to_privacidadFragment));

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