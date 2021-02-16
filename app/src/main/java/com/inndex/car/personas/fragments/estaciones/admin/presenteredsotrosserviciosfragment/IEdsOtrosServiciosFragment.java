package com.inndex.car.personas.fragments.estaciones.admin.presenteredsotrosserviciosfragment;

import android.widget.Button;
import android.widget.CheckBox;


public interface IEdsOtrosServiciosFragment {
    CheckBox restaurante();
    CheckBox hotel();
    CheckBox baniosPublicos();
    CheckBox lubricantes();
    CheckBox llanteria();
    CheckBox lavadero();

    Button botonGuardar();
}
