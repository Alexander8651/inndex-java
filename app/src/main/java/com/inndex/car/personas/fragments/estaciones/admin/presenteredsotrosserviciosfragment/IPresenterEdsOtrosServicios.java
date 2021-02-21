package com.inndex.car.personas.fragments.estaciones.admin.presenteredsotrosserviciosfragment;

public interface IPresenterEdsOtrosServicios {

    void obtenerBancos();
    void obtenerPuntosPago();
    void obtenerTiendasConvivencia();
    void obtenerSeguros();
    void obtenerMetodosPago();

    void mostrarDialogoCajeros();
    void mostrarDialogoCorresponsales();
    void mostrarDialogoPuntosPago();
    void mostrarDialogoTiendas();
    void mostrarDialogoSoat();
    void mostrarDialogoMetodosPago();

    void setearChecbox();
    void guardarCambios();

    void setearOtrosServicosData();

}
