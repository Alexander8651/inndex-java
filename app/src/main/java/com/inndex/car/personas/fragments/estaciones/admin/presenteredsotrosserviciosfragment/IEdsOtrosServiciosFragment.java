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

    Button botonGuardar();

    TextView cajerosSeleccionados();

    TextView corresponsalesSeleccionados();

    TextView puntosPagoSeleccionados();

    TextView tiendasSeleccionados();

    TextView segurosSeleccionados();

    TextView metodosPagoSeleccionados();
}
