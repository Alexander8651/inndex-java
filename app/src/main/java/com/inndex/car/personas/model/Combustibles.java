package com.inndex.car.personas.model;

import java.io.Serializable;

public class Combustibles implements Serializable {

    private Long idEstacion;
    private Long precio;
    private Long nombre;

    public Combustibles() {
    }

    public Long getPrecio() {
        return precio;
    }

    public void setPrecio(Long precio) {
        this.precio = precio;
    }
}
