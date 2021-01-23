package com.inndex.car.personas.fragments.configuracion_cuenta;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.inndex.car.personas.R;
import com.inndex.car.personas.activities.mainactivity.MainActivity;
import com.inndex.car.personas.adapter.VehiculosAdapter;
import com.inndex.car.personas.database.DataBaseHelper;
import com.inndex.car.personas.model.Vehiculo;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

public class MiVehiculo extends Fragment {

    private MainActivity mainActivity;

    private OnFragmentInteractionListener mListener;

    public MiVehiculo() {
        // Required empty public constructor
    }

    public MiVehiculo(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    // TODO: Rename and change types and number of parameters
    public static MiVehiculo newInstance(String param1, String param2) {
        MiVehiculo fragment = new MiVehiculo();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_mi_vehiculo, container, false);

        RecyclerView rvVehiculos = v.findViewById(R.id.rv_mis_vehiculos);
        DataBaseHelper helper = OpenHelperManager.getHelper(mainActivity, DataBaseHelper.class);
        List<Vehiculo> vehiculoList;
        try {
            Dao<Vehiculo, Integer> daoUsuarioHasMOdeloCarro = helper.getDaoUsuarioHasModeloCarros();
            vehiculoList = daoUsuarioHasMOdeloCarro.queryForAll();
            VehiculosAdapter adapter = new VehiculosAdapter(vehiculoList, mainActivity, helper);
            rvVehiculos.setLayoutManager(new LinearLayoutManager(getContext()));
            rvVehiculos.setAdapter(adapter);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return v;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
