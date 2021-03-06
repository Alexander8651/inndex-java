package com.inndex.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "unidad_recorrido")
public class UnidadRecorrido {

    @DatabaseField(generatedId = true, columnName = "id", unique = true)
    private Long id;
    @DatabaseField
    private double latitud;
    @DatabaseField
    private double longitud;
    @DatabaseField
    private double altitud;
    @DatabaseField
    private Double galones;
    @DatabaseField
    private Double galonesTankTwo;
    @DatabaseField
    private Integer valorBluetooh;
    @DatabaseField
    private Integer valorBluetoothTankTwo;
    @DatabaseField
    private String hora;
    @DatabaseField
    private String fecha;
    //@DatabaseField(foreign = true, foreignAutoRefresh = true)
    //@Expose(serialize = false, deserialize = false)
    @DatabaseField
    private Long idRecorrido;

    @DatabaseField
    private Integer estado;

    public UnidadRecorrido() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public Double getGalones() {
        return galones;
    }

    public void setGalones(Double galones) {
        this.galones = galones;
    }

    public Integer getValorBluetooh() {
        return valorBluetooh;
    }

    public void setValorBluetooh(Integer valorBluetooh) {
        this.valorBluetooh = valorBluetooh;
    }

    public Double getGalonesTankTwo() {
        return galonesTankTwo;
    }

    public void setGalonesTankTwo(Double galonesTankTwo) {
        this.galonesTankTwo = galonesTankTwo;
    }

    public Integer getValorBluetoothTankTwo() {
        return valorBluetoothTankTwo;
    }

    public void setValorBluetoothTankTwo(Integer valorBluetoothTankTwo) {
        this.valorBluetoothTankTwo = valorBluetoothTankTwo;
    }

    public double getAltitud() {
        return altitud;
    }

    public void setAltitud(double altitud) {
        this.altitud = altitud;
    }

    public Long getIdRecorrido() {
        return idRecorrido;
    }

    public void setIdRecorrido(Long idRecorrido) {
        this.idRecorrido = idRecorrido;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
