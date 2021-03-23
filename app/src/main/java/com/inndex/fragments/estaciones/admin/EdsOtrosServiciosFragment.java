package com.inndex.fragments.estaciones.admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.inndex.R;
import com.inndex.fragments.estaciones.admin.presenteredsotrosserviciosfragment.IEdsOtrosServiciosFragment;
import com.inndex.fragments.estaciones.admin.presenteredsotrosserviciosfragment.IPresenterEdsOtrosServicios;
import com.inndex.fragments.estaciones.admin.presenteredsotrosserviciosfragment.PresenterEdsOtrsServiciosFragment;
import com.inndex.model.Estaciones;


public class EdsOtrosServiciosFragment extends Fragment implements IEdsOtrosServiciosFragment {

    private ImageButton btnBack;
    private TextView titulo, cajerosSeleccionados, corresponsalesSeleccionados, puntosPagoSeleccionados, tiendasSeleccionados, segurosSeleccionados;
    private TextView metodosPagoSeleccionados;
    private TextView tvMensajeriaSeleccionados;
    private TextView tvAccesoriosSeleccionados;
    private TextView tvCompraYventaSeleccionados;
    private Estaciones estacion;
    private CardView cajeros, corresponsales, puntosPago, tiendasConvivencia, soat, cvMetodosPago, cvMensajeria;
    private Button guardarUsuario;
    private CheckBox restaurate, hotel, banios, lubricantes, llanteria, lavadero;
    private CheckBox cbFarmacia, cbServiteca;
    private CheckBox cbCafeteria;
    private CheckBox cbCambioAceite;
    private CheckBox cbVentaLlantas;
    private CheckBox cbVentaBaterias;
    private CheckBox cbFerreteria;
    private CheckBox cbLicoreria;
    private CheckBox cbBebidas;
    private CheckBox cbCda;
    private CheckBox cbMecanicaGeneral;

    private IPresenterEdsOtrosServicios iPresenterEdsOtrosServicios;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            estacion = getArguments().getParcelable("estacionIs");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_eds_otros_servicios, container, false);

        btnBack = root.findViewById(R.id.btnBack);
        titulo = root.findViewById(R.id.tv_toolbar_titulo);

        cajeros = root.findViewById(R.id.cajeros_electronicos_otros_servicios);
        corresponsales = root.findViewById(R.id.corresponsales_bancarios_otros_servicios);
        puntosPago = root.findViewById(R.id.puntos_pago_otros_servicios);
        tiendasConvivencia = root.findViewById(R.id.tiendas_convivencia_otros_servicios);
        soat = root.findViewById(R.id.soat_otros_servicios);
        cvMetodosPago = root.findViewById(R.id.metodos_pago_otros_servicios);
        cvMensajeria = root.findViewById(R.id.cvMensajeria);

        CardView cvCompraYventa = root.findViewById(R.id.cvCompraYventa);
        CardView cvAccesorios = root.findViewById(R.id.cvAccesorios);

        guardarUsuario = root.findViewById(R.id.guardar_usuario);

        cajerosSeleccionados = root.findViewById(R.id.cajerosSeleccionados);
        corresponsalesSeleccionados = root.findViewById(R.id.corresponsalesSeleccionados);
        puntosPagoSeleccionados = root.findViewById(R.id.puntosPagoSeleccionados);
        tiendasSeleccionados = root.findViewById(R.id.tiendasSeleccionados);
        segurosSeleccionados = root.findViewById(R.id.segurosSeleccionados);
        metodosPagoSeleccionados = root.findViewById(R.id.metodosPagoSeleccionados);
        tvMensajeriaSeleccionados = root.findViewById(R.id.tvMensajeria);
        tvAccesoriosSeleccionados = root.findViewById(R.id.tvAccesorios);
        tvCompraYventaSeleccionados = root.findViewById(R.id.tvCompraYventa);

        restaurate = root.findViewById(R.id.estacionRestaurante);
        hotel = root.findViewById(R.id.estacionHotel);
        banios = root.findViewById(R.id.estacionBaniosPublicos);
        lubricantes = root.findViewById(R.id.estacionVentaLubricantes);
        llanteria = root.findViewById(R.id.estacionLlanteria);
        lavadero = root.findViewById(R.id.estacionLavaderos);
        cbFarmacia = root.findViewById(R.id.cbFarmacia);
        cbServiteca = root.findViewById(R.id.cbServiteca);
        cbCafeteria = root.findViewById(R.id.cbCafeteria);
        cbCambioAceite = root.findViewById(R.id.cbCambioAceite);
        cbVentaLlantas = root.findViewById(R.id.cbVentaLlantas);
        cbVentaBaterias = root.findViewById(R.id.cbVentaBaterias);
        cbFerreteria = root.findViewById(R.id.cbFerreteria);
        cbLicoreria = root.findViewById(R.id.cbLicoreria);
        cbBebidas = root.findViewById(R.id.cbBebidas);
        cbCda = root.findViewById(R.id.cbCda);
        cbMecanicaGeneral = root.findViewById(R.id.cbMecanicaGeneral);

        iPresenterEdsOtrosServicios = new PresenterEdsOtrsServiciosFragment(requireContext(), this, estacion);

        titulo.setText("Otros Servicios");

        btnBack.setOnClickListener(v -> {
            Navigation.findNavController(v).navigateUp();
        });

        cajeros.setOnClickListener(v -> {
            iPresenterEdsOtrosServicios.mostrarDialogoCajeros();
        });

        corresponsales.setOnClickListener(v -> {
            iPresenterEdsOtrosServicios.mostrarDialogoCorresponsales();
        });

        puntosPago.setOnClickListener(v -> {
            iPresenterEdsOtrosServicios.mostrarDialogoPuntosPago();
        });

        tiendasConvivencia.setOnClickListener(v -> {
            iPresenterEdsOtrosServicios.mostrarDialogoTiendas();
        });

        soat.setOnClickListener(v -> {
            iPresenterEdsOtrosServicios.mostrarDialogoSoat();
        });
        cvMetodosPago.setOnClickListener(v -> {
            iPresenterEdsOtrosServicios.mostrarDialogoMetodosPago();
        });
        cvMensajeria.setOnClickListener(v -> {
            iPresenterEdsOtrosServicios.mostrarDialogoMensajeria();
        });
        cvAccesorios.setOnClickListener(v ->
                iPresenterEdsOtrosServicios.mostrarDialogoAccesorios()
        );
        cvCompraYventa.setOnClickListener(v ->
                iPresenterEdsOtrosServicios.mostrarDialogoCompraYventa()
        );
        return root;
    }

    private void restaurantes() {

        if (estacion.getListRestaurantes() != null) {
            if (estacion.getListRestaurantes().size() > 0) {
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
    public CheckBox cbFarmacia() {
        return cbFarmacia;
    }

    @Override
    public CheckBox cbServiteca() {
        return cbServiteca;
    }

    @Override
    public CheckBox cbCafeteria() {
        return cbCafeteria;
    }

    @Override
    public CheckBox cbCambioAceite() {
        return cbCambioAceite;
    }

    @Override
    public CheckBox cbVentaLlantas() {
        return cbVentaLlantas;
    }

    @Override
    public CheckBox cbVentaBaterias() {
        return cbVentaBaterias;
    }

    @Override
    public CheckBox cbFerreteria() {
        return cbFerreteria;
    }

    @Override
    public CheckBox cbLicoreria() {
        return cbLicoreria;
    }

    @Override
    public CheckBox cbBebidas() {
        return cbBebidas;
    }

    @Override
    public CheckBox cbCda() {
        return cbCda;
    }

    @Override
    public CheckBox cbMecanicaGeneral() {
        return cbMecanicaGeneral;
    }

    @Override
    public Button botonGuardar() {
        return guardarUsuario;
    }

    @Override
    public TextView cajerosSeleccionados() {
        return cajerosSeleccionados;
    }

    @Override
    public TextView corresponsalesSeleccionados() {
        return corresponsalesSeleccionados;
    }

    @Override
    public TextView puntosPagoSeleccionados() {
        return puntosPagoSeleccionados;
    }

    @Override
    public TextView tiendasSeleccionados() {
        return tiendasSeleccionados;
    }

    @Override
    public TextView segurosSeleccionados() {
        return segurosSeleccionados;
    }

    @Override
    public TextView metodosPagoSeleccionados() {
        return metodosPagoSeleccionados;
    }

    @Override
    public TextView mensajeriaSeleccionados() {
        return tvMensajeriaSeleccionados;
    }

    @Override
    public TextView tvAccesoriosSeleccionados() {
        return tvAccesoriosSeleccionados;
    }

    @Override
    public TextView tvCompraYventaSeleccionados() {
        return tvCompraYventaSeleccionados;
    }
}