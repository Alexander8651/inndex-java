package com.inndex.car.personas.model;

import java.io.Serializable;

public class Soat implements Serializable {

	private static final long serialVersionUID = 7040708063406647146L;

	private Long id;

	private String nombre;

	public Soat() {
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
