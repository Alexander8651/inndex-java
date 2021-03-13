package com.inndex.enums;

public enum EGenero {

    FEMENINO(1,"Femenino"),
    MASCULINO(2,"Masculino");

    private Integer id;
    private String nombre;

    EGenero(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }
}
