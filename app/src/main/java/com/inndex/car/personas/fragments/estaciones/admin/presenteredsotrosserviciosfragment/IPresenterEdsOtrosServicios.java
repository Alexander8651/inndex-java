package com.inndex.car.personas.fragments.estaciones.admin.presenteredsotrosserviciosfragment;

import android.view.View;

public interface IPresenterEdsOtrosServicios {

    void obtenerBancos();
    void obtenerPuntosPago();
    void obtenerTiendasConvivencia();
    void obtenerSeguros();
    void obtenerMetodosPago();
    void obtenerMensajeria();

    void mostrarDialogoCajeros();
    void mostrarDialogoCorresponsales();
    void mostrarDialogoPuntosPago();
    void mostrarDialogoTiendas();
    void mostrarDialogoSoat();
    void mostrarDialogoMetodosPago();
    void mostrarDialogoMensajeria();


    void setearChecbox();
    void guardarCambios(View v);

    void setearOtrosServicosData();

}
