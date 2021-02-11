package com.inndex.car.personas.fragments.estaciones;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.model.LatLng;
import com.inndex.car.personas.R;
import com.inndex.car.personas.adapter.EstacionesAdapter;
import com.inndex.car.personas.database.DataBaseHelper;
import com.inndex.car.personas.model.Estaciones;
import com.inndex.car.personas.utils.Constantes;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

public class EstacionesListFragment extends Fragment {

    private NavController navController;

    public EstacionesListFragment() {
    }

    public static EstacionesListFragment newInstance() {
        return new EstacionesListFragment();
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
        DataBaseHelper helper = OpenHelperManager.getHelper(getActivity(), DataBaseHelper.class);
        TextView tvTitulo = view.findViewById(R.id.tv_toolbar_titulo);
        tvTitulo.setText(getString(R.string.lista));
        ImageView imgMenu = view.findViewById(R.id.btnMenuToolbar);
        imgMenu.setVisibility(View.VISIBLE);
        PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
        popupMenu.inflate(R.menu.menu_estaciones_list);
        imgMenu.setOnClickListener(v -> popupMenu.show());

        ImageView imgBack = view.findViewById(R.id.btnBack);
        navController = Navigation.findNavController(requireActivity(), R.id.fragContentApp);
        imgBack.setOnClickListener(v ->
                navController.navigate(R.id.estacionesMapFragment)
        );

        if (getActivity() == null) {
            return null;
        }
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Constantes.SHARED_PREFERENCES_FILE_KEY, Context.MODE_PRIVATE);

        LatLng latLng = new LatLng(Float.parseFloat(sharedPreferences.getString(Constantes.LATITUD_KEY, "0")),
                Float.parseFloat(sharedPreferences.getString(Constantes.LONGITUD_KEY, "0")));

        try {
            Dao<Estaciones, Integer> daoEstaciones = helper.getDaoEstaciones();
            List<Estaciones> lEstaciones = daoEstaciones.queryForAll();
            EstacionesAdapter adapter = new EstacionesAdapter(lEstaciones, getActivity(), latLng);
            rvEstaciones.setLayoutManager(new LinearLayoutManager(getContext()));
            rvEstaciones.setAdapter(adapter);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return view;
    }


}