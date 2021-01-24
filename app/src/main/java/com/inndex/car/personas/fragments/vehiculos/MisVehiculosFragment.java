package com.inndex.car.personas.fragments.vehiculos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.inndex.car.personas.R;
import com.inndex.car.personas.adapter.AdapterVehiculo;
import com.inndex.car.personas.model.Vehiculo;

import java.util.ArrayList;


public class MisVehiculosFragment extends Fragment {

    AdapterVehiculo adapterVehiculo;
    LinearLayout llAgregarvehi;
    ArrayList<Vehiculo> vehiculos;
    RecyclerView recyclerView;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_mis_vehiculos, container, false);
        recyclerView =view.findViewById(R.id.rvVehiculos);
        llAgregarvehi = view.findViewById(R.id.llAgregarVehiculo);
        vehiculos = new ArrayList<>();

        cargarVehiculos();
        mostrarVehiculos();

        //llAgregarvehi.setOnClickListener(v ->
        //        Navigation.findNavController(v).navigate(R.id.action_opt_mis_vehiculos_to_agregarVehiculoFragment));
        return view;
    }

    public void cargarVehiculos(){
        //vehiculos.add(new Vehiculo("HDZ-51C","MAZDA","3 ALL NEW","215"));
    }

    public  void  mostrarVehiculos(){
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterVehiculo = new AdapterVehiculo(vehiculos, getContext());
        recyclerView.setAdapter(adapterVehiculo);

    }



}