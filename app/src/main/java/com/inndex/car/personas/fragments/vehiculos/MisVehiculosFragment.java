package com.inndex.car.personas.fragments.vehiculos;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.inndex.car.personas.R;
import com.inndex.car.personas.adapter.AdapterVehiculo;
import com.inndex.car.personas.fragments.vehiculos.presenters.IVehiculosFragmentPresenter;
import com.inndex.car.personas.fragments.vehiculos.presenters.VehiculosFragmentPresenter;
import com.inndex.car.personas.model.Vehiculo;
import com.inndex.car.personas.utils.Constantes;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;


public class MisVehiculosFragment extends Fragment implements IMisVehiculosFragment {

    AdapterVehiculo adapterVehiculo;
    LinearLayout llAgregarvehi;
    ArrayList<Vehiculo> vehiculos;
    RecyclerView recyclerView;
    View view;
    IVehiculosFragmentPresenter iVehiculosFragmentPresenter;
    private SharedPreferences myPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_mis_vehiculos, container, false);
        recyclerView =view.findViewById(R.id.rvVehiculos);
        llAgregarvehi = view.findViewById(R.id.llAgregarVehiculo);
        vehiculos = new ArrayList<>();

        myPreferences = requireActivity().getSharedPreferences(Constantes.SHARED_PREFERENCES_FILE_KEY, MODE_PRIVATE);
        int userID =  myPreferences.getInt(Constantes.DEFAULT_USER_ID, 0);

        iVehiculosFragmentPresenter = new VehiculosFragmentPresenter(this, userID);

        llAgregarvehi.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.action_misVehiculosFragment_to_agregarVehiculoFragment));
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

    @Override
    public AdapterVehiculo crearAdaptador(ArrayList<Vehiculo> vehiculos) {
        return new AdapterVehiculo(vehiculos ,getContext());
    }

    @Override
    public void inicializarAdaptador(AdapterVehiculo adapterVehiculo) {
        recyclerView.setAdapter(adapterVehiculo);
    }
}