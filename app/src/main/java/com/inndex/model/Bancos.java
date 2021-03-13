package com.inndex.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(tableName = "bancos")
public class Bancos implements Serializable {

    private static final long serialVersionUID = 5753635278466659564L;

    @DatabaseField
    private Long id;

    @DatabaseField
    private String nombre;

    @DatabaseField
    private Boolean corresponsal;

    @DatabaseField
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
