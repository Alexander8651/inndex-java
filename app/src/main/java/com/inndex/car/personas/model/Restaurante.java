package com.inndex.car.personas.model;

import java.io.Serializable;


public class Restaurante implements Serializable {

    private static final long serialVersionUID = -3004153775198805047L;

    private Long id;

    private String nombre;

    private Estaciones estaciones;

    public Restaurante() {
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

    public Estaciones getEstaciones() {
        return estaciones;
    }

    public void setEstaciones(Estaciones estaciones) {
        this.estaciones = estaciones;
    }
}
