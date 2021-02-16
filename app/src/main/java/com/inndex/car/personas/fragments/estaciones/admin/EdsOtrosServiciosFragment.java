package com.inndex.car.personas.fragments.estaciones.admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.inndex.car.personas.R;
import com.inndex.car.personas.fragments.estaciones.admin.presenteredsotrosserviciosfragment.IEdsOtrosServiciosFragment;
import com.inndex.car.personas.fragments.estaciones.admin.presenteredsotrosserviciosfragment.IPresenterEdsOtrosServicios;
import com.inndex.car.personas.fragments.estaciones.admin.presenteredsotrosserviciosfragment.PresenterEdsOtrsServiciosFragment;
import com.inndex.car.personas.fragments.estaciones.admin.presentermisedsfragment.IPresenterMisEdsFragment;
import com.inndex.car.personas.fragments.estaciones.admin.presentermisedsfragment.PresenterMisEdsFragment;
import com.inndex.car.personas.model.Estaciones;

import java.util.ArrayList;


public class EdsOtrosServiciosFragment extends Fragment implements IEdsOtrosServiciosFragment {

    private ImageButton btnBack;
    private TextView titulo;
    private Estaciones estacion;
    private Button cajeros, corresponsales, puntosPago, tiendasConvivencia, soat, guardarUsuario;
    private CheckBox restaurate, hotel, banios, lubricantes,llanteria, lavadero;

    private IPresenterEdsOtrosServicios iPresenterEdsOtrosServicios;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            estacion = (Estaciones) getArguments().getParcelable("estacionIs");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_eds_otros_servicios, container,false);

        btnBack = root.findViewById(R.id.btnBack);
        titulo = root.findViewById(R.id.tv_toolbar_titulo);

        cajeros =  root.findViewById(R.id.cajeros_electronicos_otros_servicios);
        corresponsales = root.findViewById(R.id.corresponsales_bancarios_otros_servicios);
        puntosPago = root.findViewById(R.id.puntos_pago_otros_servicios);
        tiendasConvivencia = root.findViewById(R.id.tiendas_convivencia_otros_servicios);
        soat = root.findViewById(R.id.soat_otros_servicios);
        guardarUsuario = root.findViewById(R.id.guardar_usuario);

        restaurate = root.findViewById(R.id.estacionRestaurante);
        hotel = root.findViewById(R.id.estacionHotel);
        banios = root.findViewById(R.id.estacionBaniosPublicos);
        lubricantes = root.findViewById(R.id.estacionVentaLubricantes);
        llanteria = root.findViewById(R.id.estacionLlanteria);
        lavadero = root.findViewById(R.id.estacionLavaderos);

        iPresenterEdsOtrosServicios = new PresenterEdsOtrsServiciosFragment(requireContext(),this, estacion);

        titulo.setText("Otros Servicios");





        btnBack.setOnClickListener(v ->{
            Navigation.findNavController(v).navigateUp();
        });

        cajeros.setOnClickListener(v ->{
            iPresenterEdsOtrosServicios.mostrarDialogoCajeros();
        });

        corresponsales.setOnClickListener(v ->{
            iPresenterEdsOtrosServicios.mostrarDialogoCorresponsales();
        });

        puntosPago.setOnClickListener(v ->{
            iPresenterEdsOtrosServicios.mostrarDialogoPuntosPago();
        });

        tiendasConvivencia.setOnClickListener(v ->{
            iPresenterEdsOtrosServicios.mostrarDialogoTiendas();
        });

        soat.setOnClickListener(v ->{
            iPresenterEdsOtrosServicios.mostrarDialogoSoat();
        });


        return root;
    }

    private void restaurantes(){

        if (estacion.getListRestaurantes() != null){
            if (estacion.getListRestaurantes().size() > 0){
                restaurate.setChecked(true);
            }
        }

    }

    @Override
    public CheckBox restaurante() {
        return restaurate;
    }

    @Override
    public CheckBox hotel() {
        return hotel;
    }

    @Override
    public CheckBox baniosPublicos() {
        return banios;
    }

    @Override
    public CheckBox lubricantes() {
        return lubricantes;
    }

    @Override
    public CheckBox llanteria() {
        return llanteria;
    }

    @Override
    public CheckBox lavadero() {
        return lavadero;
    }

    @Override
    public Button botonGuardar() {
        return guardarUsuario;
    }
}