package com.inndex.car.personas.model;

import java.io.Serializable;


public class Bancos implements Serializable {

    private static final long serialVersionUID = 5753635278466659564L;

    private Long id;

    private String nombre;

    private Boolean corresponsal;

    private Boolean cajero;

    public Bancos() {
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

    public Boolean getCorresponsal() {
        return corresponsal;
    }

    public void setCorresponsal(Boolean corresponsal) {
        this.corresponsal = corresponsal;
    }

    public Boolean getCajero() {
        return cajero;
    }

    public void setCajero(Boolean cajero) {
        this.cajero = cajero;
    }
}
