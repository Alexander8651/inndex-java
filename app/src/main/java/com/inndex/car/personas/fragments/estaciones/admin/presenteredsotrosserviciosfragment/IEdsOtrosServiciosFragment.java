package com.inndex.car.personas.fragments.estaciones.admin.presenteredsotrosserviciosfragment;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public interface IEdsOtrosServiciosFragment {

    CheckBox restaurante();

    CheckBox hotel();

    CheckBox baniosPublicos();

    CheckBox lubricantes();

    CheckBox llanteria();

    CheckBox lavadero();

    CheckBox cbFarmacia();

    CheckBox cbServiteca();

    CheckBox cbCafeteria();
    CheckBox cbCambioAceite();
    CheckBox cbVentaLlantas();
    CheckBox cbVentaBaterias();
    CheckBox cbFerreteria();
    CheckBox cbLicoreria();
    CheckBox cbBebidas();
    CheckBox cbCda();
    CheckBox cbMecanicaGeneral();

    Button botonGuardar();

    TextView cajerosSeleccionados();

    TextView corresponsalesSeleccionados();

    TextView puntosPagoSeleccionados();

    TextView tiendasSeleccionados();

    TextView segurosSeleccionados();

    TextView metodosPagoSeleccionados();

    TextView mensajeriaSeleccionados();

    TextView tvAccesoriosSeleccionados();

    TextView tvCompraYventaSeleccionados();
}
