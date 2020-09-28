package com.inndex.car.personas.fragments.estaciones;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.inndex.car.personas.R;
import com.inndex.car.personas.activities.MainActivity;
import com.inndex.car.personas.model.Estaciones;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EstacionDetalleFragment extends Fragment {

    @BindView(R.id.tv_estacion_detalle_calificacion)
    public TextView tvCalificacion;
    @BindView(R.id.tv_estacion_detalle_calificar)
    public TextView tvCalificar;
    @BindView(R.id.tv_estacion_detalle_direccion)
    public TextView tvDireciion;
    @BindView(R.id.tv_estacion_detalle_distancia)
    public TextView tvDistancia;
    @BindView(R.id.tv_estacion_detalle_horario)
    public TextView tvHorario;
    @BindView(R.id.tv_estacion_detalle_marca)
    public TextView tvMarca;
    @BindView(R.id.tv_estacion_detalle_nombre)
    public TextView tvNombre;
    @BindView(R.id.tv_estacion_detalle_ver_opiniones)
    public TextView tvVerOpiniones;


    private Estaciones estacion;
    private MainActivity mainActivity;
    private Typeface light;

    public EstacionDetalleFragment(Estaciones estacion, MainActivity mainActivity,
                                   Typeface light) {
        this.mainActivity = mainActivity;
        this.estacion = estacion;
        this.light = light;
    }

    public EstacionDetalleFragment() {
        // Required empty public constructor
    }

    public static EstacionDetalleFragment newInstance(String param1, String param2) {
        EstacionDetalleFragment fragment = new EstacionDetalleFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_estacion_detalle, container, false);
        ButterKnife.bind(this, view);

        tvCalificacion.setTypeface(light);
        tvCalificar.setTypeface(light);
        tvDireciion.setTypeface(light);
        tvDistancia.setTypeface(light);
        tvHorario.setTypeface(light);
        tvMarca.setTypeface(light);
        tvNombre.setTypeface(light);
        tvVerOpiniones.setTypeface(light);

        tvDireciion.setText(estacion.getDireccion());
        tvMarca.setText(estacion.getMarca());
        //tvCalificacion.setText(estacion.getCa());
        tvNombre.setText(estacion.getNombre());
        tvHorario.setText(estacion.getHorario());


        return view;
    }
}