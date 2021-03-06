package com.inndex.fragments.dondetanquear;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.inndex.R;
import com.inndex.activities.mainactivity.MainActivity;
import com.inndex.model.Estaciones;

import java.util.ArrayList;
import java.util.List;

public class Calificacion extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private MainActivity mainActivity;

    public Calificacion(MainActivity mainActivity) {
        this.mainActivity =  mainActivity;
    }

    public Calificacion() {
        // Required empty public constructor
    }



    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Calificacion.
     */
    // TODO: Rename and change types and number of parameters
    public static Calificacion newInstance(String param1, String param2) {
        Calificacion fragment = new Calificacion();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
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
        View v = inflater.inflate(R.layout.fragment_calificacion, container, false);
        //List<Estaciones> listEstaciones = mainActivity.getEstaciones();
        List<Estaciones> listEstaciones = new ArrayList<>();
        RecyclerView rv = v.findViewById(R.id.rv_dt_calificacion);
        final LinearLayoutManager llm = new LinearLayoutManager(mainActivity);
        rv.setLayoutManager(llm);
        //RecyclerView.Adapter adapter = new EstacionesAdapter(listEstaciones, mainActivity);
        //rv.setAdapter(adapter);
        return v;
    }








}
