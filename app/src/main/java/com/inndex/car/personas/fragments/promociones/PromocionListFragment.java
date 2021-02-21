package com.inndex.car.personas.fragments.promociones;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.inndex.car.personas.R;
import com.inndex.car.personas.adapter.AdapterPromociones;
import com.inndex.car.personas.fragments.promociones.presentador.IPresenterPromocionList;
import com.inndex.car.personas.fragments.promociones.presentador.IPromocionListFragment;
import com.inndex.car.personas.fragments.promociones.presentador.PreseenterPromocionList;
import com.inndex.car.personas.model.Estaciones;
import com.inndex.car.personas.model.Promocion;

import java.util.ArrayList;

public class PromocionListFragment extends Fragment implements IPromocionListFragment {

    //private PromocionListViewModel mViewModel;

    public static PromocionListFragment newInstance() {
        return new PromocionListFragment();
    }

    TextView titulo;
    ImageButton btnBack;
    RecyclerView promocionesrv;
    FloatingActionButton agregar_promocion;

    IPresenterPromocionList iPresenterPromocionList;

    private Estaciones estacion;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            estacion = getArguments().getParcelable("estacionIs");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_promocion_list, container, false);

        titulo = root.findViewById(R.id.tv_toolbar_titulo);
        btnBack = root.findViewById(R.id.btnBack);
        promocionesrv = root.findViewById(R.id.promocionesRV);
        iPresenterPromocionList = new PreseenterPromocionList(this, estacion.getId());
        agregar_promocion = root.findViewById(R.id.agregar_promocion);

        titulo.setText("Promociones");

        btnBack.setOnClickListener(v ->{
            Navigation.findNavController(v).navigateUp();
        });

        agregar_promocion.setOnClickListener(v ->{
            Navigation.findNavController(v).navigate(R.id.action_promocionListFragment_to_promocionFormFragment);
        });

        return root;
    }


    @Override
    public void inicializarRv(AdapterPromociones promociones) {
        promocionesrv.setAdapter(promociones);
    }

    @Override
    public AdapterPromociones crearAdapter(ArrayList<Promocion> promocions) {
        return new AdapterPromociones(promocions);
    }

    /*
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(PromocionListViewModel.class);
        // TODO: Use the ViewModel
    }

     */



}