package com.inndex.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(tableName = "lineas_vehiculos")
public class LineasVehiculos implements Serializable {

    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private String nombre;


    private MarcaVehiculos marca;


    public LineasVehiculos() {
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

    public MarcaVehiculos getMarca() {
        return marca;
    }

    public void setMarca(MarcaVehiculos marca) {
        this.marca = marca;
    }
}
