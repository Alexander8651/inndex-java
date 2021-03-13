package com.inndex.model;

import java.io.Serializable;

public class EstacionCalificacion implements Serializable {

    private static final long serialVersionUID = 5753635278466659563L;

    private Long id;

    private Integer calificacion;

    private String comentario;

    private Usuario usuario;

    private Estaciones estaciones;

    public EstacionCalificacion() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Integer calificacion) {
        this.calificacion = calificacion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Estaciones getEstaciones() {
        return estaciones;
    }

    public void setEstaciones(Estaciones estaciones) {
        this.estaciones = estaciones;
    }
}
