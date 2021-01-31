package com.inndex.car.personas.fragments.configuracion_cuenta;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.inndex.car.personas.R;

public class AcountConfFragment extends Fragment {

   private LinearLayout infoperso;
   View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_acount_conf, container, false);

       infoperso = view.findViewById(R.id.infoPersonal);

       infoperso.setOnClickListener(v ->
               Navigation.findNavController(v).navigate(R.id.editProfileFragment));
        return view;
    }
}