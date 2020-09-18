package com.inndex.car.personas.fragments.estaciones;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.inndex.car.personas.R;
import com.inndex.car.personas.activities.MainActivity;
import com.inndex.car.personas.database.DataBaseHelper;
import com.inndex.car.personas.model.MarcaCarros;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class EstacionesFiltrosFragment extends Fragment {

    @BindView(R.id.rel_filter_marcas)
    private RelativeLayout relFilterMarcas;
    @BindView(R.id.rel_filter_calificacion)
    private RelativeLayout relFilterCalificacion;
    @BindView(R.id.rel_filter_cerificados)
    private RelativeLayout relFilterCertifiacdos;
    @BindView(R.id.rel_filter_distancia)
    private RelativeLayout relFilterDistancia;

    @BindView(R.id.tv_filtro_con_que_calificacion)
    private TextView tvFiltroCalificacion;
    @BindView(R.id.tv_filtro_con_que_certificados)
    private TextView tvFiltroCertificados;
    @BindView(R.id.tv_filtro_que_marcas_prefieres)
    private TextView tvFiltroMarcas;
    @BindView(R.id.tv_filtro_que_tan_cerca_de_ti)
    private TextView tvFiltroDistancia;


    private MainActivity mainActivity;
    private DataBaseHelper helper;
    private List<MarcaCarros> lMarcasCarros;

    public EstacionesFiltrosFragment(MainActivity mainActivity, DataBaseHelper helper) {
        this.mainActivity = mainActivity;
        this.helper = helper;
    }

    public EstacionesFiltrosFragment() {
        // Required empty public constructor
    }

    public static EstacionesFiltrosFragment newInstance(String param1, String param2) {
        EstacionesFiltrosFragment fragment = new EstacionesFiltrosFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_estaciones_filtros, container, false);
    }

    @OnClick(R.id.rel_filter_cerificados)
    public void showCertificateFilters() {

    }

    @OnClick(R.id.rel_filter_marcas)
    public void showBrandFilters() {

    }

    @OnClick(R.id.rel_filter_distancia)
    public void showDistanceFilters() {
        String[] opciones = getResources().getStringArray(R.array.opciones_filtro_distancia);
        AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);
        builder.setTitle("Distancia");
        builder.setSingleChoiceItems(opciones, -1, (dialog, which) -> {

        });
        builder.setPositiveButton("OK", (dialog, which) -> {
            tvFiltroDistancia.setText(opciones[which]);
            dialog.dismiss();
        });
        builder.setNegativeButton("Cancelar", (dialog, which) -> {
            dialog.dismiss();
        });
    }

    @OnClick(R.id.rel_filter_calificacion)
    public void showCalificacionFilters() {

    }


}