package com.inndex.car.personas.model;

import java.io.Serializable;


public class Certificados implements Serializable {

    private Long id;
    private String name;

    public Certificados() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
