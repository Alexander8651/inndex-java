package com.inndex.model;

import java.io.Serializable;

public class Textos implements Serializable {

    private Long id;

    private String texto;

    public Textos() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
}
