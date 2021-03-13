package com.inndex.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(tableName = "marcas_estacion")
public class MarcaEstacion implements Serializable {

    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private String nombre;


    public MarcaEstacion() {
    }

    public MarcaEstacion(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public MarcaEstacion(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
