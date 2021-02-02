package com.inndex.car.personas.fragments.estaciones.favoritas;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.inndex.car.personas.R;
import com.inndex.car.personas.adapter.EstacionFavoritaAdapter;
import com.inndex.car.personas.database.DataBaseHelper;
import com.inndex.car.personas.model.Estaciones;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EstacionesFavoritasFragment extends Fragment {

    private EstacionesFavoritasViewModel mViewModel;
    private DataBaseHelper helper;
    private List<Estaciones> estaciones;
    private NavController navController;
    private RecyclerView rvFavoritas;

    private EstacionFavoritaAdapter adapter;


    public static EstacionesFavoritasFragment newInstance() {
        return new EstacionesFavoritasFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        helper = OpenHelperManager.getHelper(getActivity(), DataBaseHelper.class);

        View root = inflater.inflate(R.layout.fragment_estaciones_favoritas, container, false);

        TextView tvTitulo = root.findViewById(R.id.tv_toolbar_titulo);
        ImageView imgBack = root.findViewById(R.id.btnBack);
        navController = Navigation.findNavController(requireActivity(), R.id.fragContentApp);
        imgBack.setOnClickListener(v -> {
            navController.navigate(R.id.estacionesMapFragment);
        });

        tvTitulo.setText("Mis favoritas");

        rvFavoritas = root.findViewById(R.id.rvEstacionesFavoritas);
        try {
            getAllStations();
            Log.d("estacionesss", estaciones.get(0).getNombre());

            adapter = new EstacionFavoritaAdapter((ArrayList<Estaciones>) estaciones, requireActivity());
            rvFavoritas.setAdapter(adapter);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(EstacionesFavoritasViewModel.class);
        // TODO: Use the ViewModel
    }

    public void getAllStations() throws SQLException {
        final Dao<Estaciones, Integer> dao = helper.getDaoEstaciones();
        estaciones = dao.queryForAll();
    }

}