package com.inndex.car.personas.fragments.estaciones.admin.presenteredsotrosserviciosfragment;

public interface IPresenterEdsOtrosServicios {

    void obtenerBancos();
    void obtenerPuntosPago();
    void obtenerTiendasConvivencia();
    void obtenerSeguros();

    void mostrarDialogoCajeros();
    void mostrarDialogoCorresponsales();
    void mostrarDialogoPuntosPago();
    void mostrarDialogoTiendas();
    void mostrarDialogoSoat();

    void setearChecbox();
    void guardarUsuario();

}
