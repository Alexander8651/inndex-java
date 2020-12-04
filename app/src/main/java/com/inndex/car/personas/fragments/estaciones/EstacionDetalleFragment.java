package com.inndex.car.personas.fragments.estaciones;

import android.graphics.Typeface;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.model.LatLng;
import com.inndex.car.personas.R;
import com.inndex.car.personas.activities.MainActivity;
import com.inndex.car.personas.model.Estaciones;
import com.inndex.car.personas.utils.Constantes;

import java.util.Locale;

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
    @BindView(R.id.tv_est_det_combustible_principal_titulo)
    public TextView tvCombustiblePrincipalTitulo;
    @BindView(R.id.tv_est_det_combustible_principal_precio)
    public TextView tvCombustiblePrincipalPrecio;
    @BindView(R.id.tv_est_det_combustible_second_titulo)
    public TextView tvCombustibleSecondTitulo;
    @BindView(R.id.tv_est_det_combustible_second_precio)
    public TextView tvCombustibleSecondPrecio;
    @BindView(R.id.tv_est_det_combustible_third_titulo)
    public TextView tvCombustibleThirdTitulo;
    @BindView(R.id.tv_est_det_combustible_third_precio)
    public TextView tvCombustibleThirdPrecio;



    private Estaciones estacion;
    private MainActivity mainActivity;
    private Typeface light;
    private LatLng myPosition;

    public EstacionDetalleFragment(Estaciones estacion, MainActivity mainActivity,
                                   Typeface light, Location position) {
        this.mainActivity = mainActivity;
        this.estacion = estacion;
        this.light = light;
        this.myPosition = new LatLng(position.getLatitude(), position.getLongitude());
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
        tvCombustiblePrincipalPrecio.setTypeface(light);
        tvCombustiblePrincipalTitulo.setTypeface(light);
        tvCombustibleSecondPrecio.setTypeface(light);
        tvCombustibleSecondTitulo.setTypeface(light);
        tvCombustibleThirdPrecio.setTypeface(light);
        tvCombustibleThirdTitulo.setTypeface(light);
        tvDireciion.setText(estacion.getDireccion());
        tvMarca.setText(estacion.getMarca());
        //tvCalificacion.setText(estacion.getCa());
        tvNombre.setText(estacion.getNombre());
        tvHorario.setText(estacion.getHorario());
        float distancia = Constantes.getDistance(myPosition, estacion.getCoordenadas());
        if(distancia < 1000) {
            tvDistancia.setText(String.format(Locale.ENGLISH,"%.2f m", distancia));
        } else {
            distancia = distancia / 1000;
            tvDistancia.setText(String.format(Locale.ENGLISH,"%.2f km", distancia));
        }
        return view;
    }
}