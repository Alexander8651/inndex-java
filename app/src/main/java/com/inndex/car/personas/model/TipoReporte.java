package com.inndex.car.personas.model;

import java.io.Serializable;

public class TipoReporte implements Serializable {

    private static final long serialVersionUID = 5753235278466659563L;

    private Long id;

    private String nombre;

    public TipoReporte() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
