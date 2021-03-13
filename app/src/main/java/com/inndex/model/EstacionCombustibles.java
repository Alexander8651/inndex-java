package com.inndex.model;

import java.io.Serializable;


public class EstacionCombustibles implements Serializable {

    private static final long serialVersionUID = 5753635278466659563L;

    private Long id;

    private int precio;

    private Combustibles combustible;

    private Estaciones estaciones;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getPrecio() {
		return precio;
	}

	public void setPrecio(int precio) {
		this.precio = precio;
	}

	public Combustibles getCombustible() {
		return combustible;
	}

	public void setCombustible(Combustibles combustible) {
		this.combustible = combustible;
	}

	public Estaciones getEstaciones() {
		return estaciones;
	}

	public void setEstaciones(Estaciones estaciones) {
		this.estaciones = estaciones;
	}
}
