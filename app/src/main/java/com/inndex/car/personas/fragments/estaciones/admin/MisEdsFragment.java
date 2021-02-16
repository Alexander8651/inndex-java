package com.inndex.car.personas.fragments.estaciones.admin;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.inndex.car.personas.R;
import com.inndex.car.personas.adapter.AdapterMisEds;
import com.inndex.car.personas.fragments.estaciones.admin.presentermisedsfragment.IMisEdsFragment;
import com.inndex.car.personas.fragments.estaciones.admin.presentermisedsfragment.IPresenterMisEdsFragment;
import com.inndex.car.personas.fragments.estaciones.admin.presentermisedsfragment.PresenterMisEdsFragment;
import com.inndex.car.personas.model.Estaciones;
import com.inndex.car.personas.utils.Constantes;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


public class MisEdsFragment extends Fragment implements IMisEdsFragment {

    private AdapterMisEds adapterMisEds;
    private List<Estaciones> estaciones;
    private RecyclerView recyclerView;
    private View root;
    private SharedPreferences myPreferences;
    private IPresenterMisEdsFragment iPresenterMisEdsFragment;
    private ImageButton btnBack;
    private TextView titulo;


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

        btnBack = root.findViewById(R.id.btnBack);
        titulo = root.findViewById(R.id.tv_toolbar_titulo);

        titulo.setText("Mis EDS");

        btnBack.setOnClickListener(v ->{
            Navigation.findNavController(v).navigateUp();
        });

        recyclerView = root.findViewById(R.id.rv_mis_eds);
        estaciones = new ArrayList<>();

        cargarEstaciones();
        mostarEstaciones();

        myPreferences = requireActivity().getSharedPreferences(Constantes.SHARED_PREFERENCES_FILE_KEY, MODE_PRIVATE);
        int userID =  myPreferences.getInt(Constantes.DEFAULT_USER_ID, 0);

        iPresenterMisEdsFragment = new PresenterMisEdsFragment(this, userID);

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

    @Override
    public AdapterMisEds crearAdater(ArrayList<Estaciones> estaciones) {
        return new AdapterMisEds(estaciones, requireContext());
    }

    @Override
    public void InicializarAdapter(AdapterMisEds adapterMisEds) {
        recyclerView.setAdapter(adapterMisEds);
    }


}