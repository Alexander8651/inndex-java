package com.inndex.model;

import java.io.Serializable;

public class AccesoriosYrepuestos implements Serializable {


    private Long id;

    private String nombre;

    public AccesoriosYrepuestos() {
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
