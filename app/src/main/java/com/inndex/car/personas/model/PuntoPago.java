package com.inndex.car.personas.model;

import java.io.Serializable;


public class PuntoPago implements Serializable {

	private static final long serialVersionUID = -5911948562682749995L;


	private Long id;

	private String nombre;


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
