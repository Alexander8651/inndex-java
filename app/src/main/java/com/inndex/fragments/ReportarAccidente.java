package com.inndex.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.inndex.R;

public class ReportarAccidente extends Fragment {

    public ReportarAccidente() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ReportarAccidente newInstance(String param1, String param2) {
        ReportarAccidente fragment = new ReportarAccidente();
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
        return inflater.inflate(R.layout.fragment_reportar_accidente, container, false);
    }
}
