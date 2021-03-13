package com.inndex.model;

import java.io.Serializable;

public class EstacionReporte implements Serializable {

    private static final long serialVersionUID = 5753635278466659563L;

    private Long id;

    private TipoReporte tipoReporte;

    private Estaciones estaciones;

    public EstacionReporte() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoReporte getTipoReporte() {
        return tipoReporte;
    }

    public void setTipoReporte(TipoReporte tipoReporte) {
        this.tipoReporte = tipoReporte;
    }

    public Estaciones getEstaciones() {
        return estaciones;
    }

    public void setEstaciones(Estaciones estaciones) {
        this.estaciones = estaciones;
    }
}
