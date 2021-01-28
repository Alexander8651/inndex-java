package com.inndex.car.personas.fragments.estaciones.admin.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.inndex.car.personas.R;
import com.inndex.car.personas.adapter.AdapterMisEds;
import com.inndex.car.personas.model.Estaciones;

import java.util.ArrayList;
import java.util.List;


public class MisEdsFragment extends Fragment {

    private AdapterMisEds adapterMisEds;
    private List<Estaciones> estaciones;
    private RecyclerView recyclerView;
    private View root;

    private MisEdsViewModel galleryViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      /*  galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);*/
        root = inflater.inflate(R.layout.fragment_mis_eds, container, false);
        /*final TextView textView = root.findViewById(R.id.text_gallery);
        galleryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/

        recyclerView = root.findViewById(R.id.rv_mis_eds);
        estaciones = new ArrayList<>();

        cargarEstaciones();
        mostarEstaciones();

        return root;
    }

    public void cargarEstaciones(){
       // estaciones.add(new Estaciones("EDS la esperanza","cl.22 # 68d-20","TERPEL", "Bogotá DC","Bogotá DC"));
    }

    public  void mostarEstaciones(){
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterMisEds = new AdapterMisEds(estaciones,getContext());
        recyclerView.setAdapter(adapterMisEds);
    }

}