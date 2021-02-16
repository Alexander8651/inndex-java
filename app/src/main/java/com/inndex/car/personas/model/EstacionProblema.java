package com.inndex.car.personas.model;

import java.io.Serializable;

public class EstacionProblema implements Serializable {

    private static final long serialVersionUID = 5753635278466659564L;

    private Long id;

    private String nombre;

    public EstacionProblema() {
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
