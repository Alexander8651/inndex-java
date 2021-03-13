package com.inndex.fragments.estaciones;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.inndex.R;

public class EstacionesServiciosFragment extends Fragment {



    public EstacionesServiciosFragment() {
        // Required empty public constructor
    }

    public static EstacionesServiciosFragment newInstance(String param1, String param2) {
        EstacionesServiciosFragment fragment = new EstacionesServiciosFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_estaciones_servicios, container, false);


        return root;
    }


}