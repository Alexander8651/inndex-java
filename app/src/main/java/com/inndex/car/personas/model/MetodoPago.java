package com.inndex.car.personas.model;

import java.io.Serializable;

public class MetodoPago implements Serializable {

    private Long id;

    private String nombre;

    public MetodoPago() {
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

