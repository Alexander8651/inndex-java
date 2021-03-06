package com.inndex.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "usuarios_has_modelos_carros")
public class Vehiculo {

    @DatabaseField(id = true)
    private Long id;

    @DatabaseField
    private String placa;

    @DatabaseField
    private Integer anio;

    private String color;

    private Integer colorId;

    private Combustibles combustible;

    private LineasVehiculos linea;

    private Usuario usuario;

    private Estados estado;

    public Vehiculo() {

    }

    public Vehiculo(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }


    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public Estados getEstado() {
        return estado;
    }

    public void setEstado(Estados estado) {
        this.estado = estado;
    }

    public LineasVehiculos getLinea() {
        return linea;
    }

    public void setLinea(LineasVehiculos linea) {
        this.linea = linea;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getColorId() {
        return colorId;
    }

    public void setColorId(Integer colorId) {
        this.colorId = colorId;
    }

    public Combustibles getCombustible() {
        return combustible;
    }

    public void setCombustible(Combustibles combustible) {
        this.combustible = combustible;
    }
}
