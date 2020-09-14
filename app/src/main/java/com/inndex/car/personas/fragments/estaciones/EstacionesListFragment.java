package com.inndex.car.personas.fragments.estaciones;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.inndex.car.personas.R;
import com.inndex.car.personas.activities.MainActivity;
import com.inndex.car.personas.adapter.EstacionesAdapter;
import com.inndex.car.personas.database.DataBaseHelper;
import com.inndex.car.personas.model.Estaciones;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

public class EstacionesListFragment extends Fragment {

    private List<Estaciones> lEstaciones;
    private MainActivity mainActivity;

    public EstacionesListFragment( MainActivity mainActivity) {
        this.mainActivity =  mainActivity;

    }

    public EstacionesListFragment() {
    }

    public static EstacionesListFragment newInstance(String param1, String param2) {
        EstacionesListFragment fragment = new EstacionesListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_estaciones_list, container, false);
        RecyclerView rvEstaciones = view.findViewById(R.id.rv_estaciones);
        DataBaseHelper helper = OpenHelperManager.getHelper(mainActivity, DataBaseHelper.class);
        try {
            Dao<Estaciones, Integer> daoEstaciones = helper.getDaoEstaciones();
            this.lEstaciones = daoEstaciones.queryForAll();
            EstacionesAdapter adapter = new EstacionesAdapter(this.lEstaciones, mainActivity, helper);
            rvEstaciones.setLayoutManager(new LinearLayoutManager(getContext()));
            rvEstaciones.setAdapter(adapter);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return view;
    }
}