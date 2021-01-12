package com.inndex.car.personas.model;

import java.io.Serializable;


public class TipoEstacion implements Serializable {

	private static final long serialVersionUID = 8880999884508076166L;

	private Long id;

	private String nombre;

	public TipoEstacion() {
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
