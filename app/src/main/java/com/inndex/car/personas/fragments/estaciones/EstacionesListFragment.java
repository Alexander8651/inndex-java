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
import java.util.Collections;
import java.util.List;

public class EstacionesListFragment extends Fragment {

    private NavController navController;

    private static final Integer ORDER_BY_DISTANCE = 1;
    private static final Integer ORDER_BY_CALIFICATION = 2;
    private static final Integer ORDER_BY_CORRIENTE = 3;
    private static final Integer ORDER_BY_EXTRA = 4;
    private static final Integer ORDER_BY_DIESEL = 5;
    private static final Integer ORDER_BY_BIODIESEL = 6;
    private static final Integer ORDER_BY_GNV = 7;

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
        PopupMenu popupMenu = new PopupMenu(view.getContext(), imgMenu);
        popupMenu.inflate(R.menu.menu_estaciones_list);
        popupMenu.setOnMenuItemClickListener(item -> {

            int itemId = item.getItemId();
            if (itemId == R.id.optOrderByCalificacion) {
                orderStations(ORDER_BY_CALIFICATION);
            } else if (itemId == R.id.optOrderByDistancia) {
                orderStations(ORDER_BY_DISTANCE);
            } else if (itemId == R.id.optOrderByGasolinaCorriente) {
                orderStations(ORDER_BY_CORRIENTE);
            } else if (itemId == R.id.optOrderByGasolinaExtra) {
                orderStations(ORDER_BY_EXTRA);
            } else if (itemId == R.id.optOrderByPrecioBioDiesel) {
                orderStations(ORDER_BY_BIODIESEL);
            } else if (itemId == R.id.optOrderByPrecioDiesel) {
                orderStations(ORDER_BY_DIESEL);
            } else if (itemId == R.id.optOrderByPrecioGNV) {
                orderStations(ORDER_BY_GNV);
            }

            return false;

        });
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

        double latitud = Double.parseDouble(sharedPreferences.getString(Constantes.LATITUD_KEY, "0.0"));
        double longitud = Double.parseDouble(sharedPreferences.getString(Constantes.LONGITUD_KEY, "0.0"));

        LatLng latLng = new LatLng(latitud, longitud);
        float distancia;
        try {
            Dao<Estaciones, Integer> daoEstaciones = helper.getDaoEstaciones();
            List<Estaciones> lEstaciones = daoEstaciones.queryForAll();
            for (int i = 0; i < lEstaciones.size(); i++) {
                lEstaciones.get(i).setListEstacionCombustibles(lEstaciones.get(i).getCombustiblesFromJson());
                distancia = Constantes.getDistance(latLng, lEstaciones.get(i).getCoordenadas());
                lEstaciones.get(i).setDistancia(distancia);
            }
            Collections.sort(lEstaciones, (est1, est2) -> Float.compare(est1.getDistancia(), est2.getDistancia()));
            EstacionesAdapter adapter = new EstacionesAdapter(lEstaciones, getActivity());
            rvEstaciones.setLayoutManager(new LinearLayoutManager(getContext()));
            rvEstaciones.setAdapter(adapter);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return view;
    }

    public void orderStations(Integer option) {

    }

}