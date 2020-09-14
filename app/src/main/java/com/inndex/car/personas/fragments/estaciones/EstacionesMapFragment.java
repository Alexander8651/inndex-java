package com.inndex.car.personas.fragments.estaciones;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.inndex.car.personas.R;
import com.inndex.car.personas.activities.MainActivity;


public class EstacionesMapFragment extends Fragment {

    private MainActivity mainActivity;

    public EstacionesMapFragment(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public EstacionesMapFragment() {
        // Required empty public constructor
    }


    public static EstacionesMapFragment newInstance(String param1, String param2) {
        EstacionesMapFragment fragment = new EstacionesMapFragment();
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_estaciones_map, container, false);
    }
}