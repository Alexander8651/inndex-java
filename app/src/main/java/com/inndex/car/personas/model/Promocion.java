package com.inndex.car.personas.model;

import java.io.Serializable;

public class Promocion implements Serializable {

    private Long id;

    private String titulo;

    private String descripcion;

    private String foto;

    private Boolean active;

    private Estaciones estaciones;

    public Promocion() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Estaciones getEstaciones() {
        return estaciones;
    }

    public void setEstaciones(Estaciones estaciones) {
        this.estaciones = estaciones;
    }
}
