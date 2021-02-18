package com.inndex.car.personas.fragments.estaciones.admin.presenteredsotrosserviciosfragment;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.cardview.widget.CardView;


public interface IEdsOtrosServiciosFragment {
    CheckBox restaurante();
    CheckBox hotel();
    CheckBox baniosPublicos();
    CheckBox lubricantes();
    CheckBox llanteria();
    CheckBox lavadero();

    Button botonGuardar();

    TextView cajerosSeleccionados();
    TextView corresponsalesSeleccionados();
    TextView puntosPagoSeleccionados();
    TextView tiendasSeleccionados();
    TextView segurosSeleccionados();
}
