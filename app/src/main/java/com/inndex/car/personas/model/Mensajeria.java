package com.inndex.car.personas.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(tableName = "mensajeria")
public class Mensajeria implements Serializable {

    private static final long serialVersionUID = 6853635278466659564L;

    @DatabaseField
    private Long id;

    @DatabaseField
    private String nombre;

    public Mensajeria() {
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
