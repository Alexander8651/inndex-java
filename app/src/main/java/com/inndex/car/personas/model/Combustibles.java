package com.inndex.car.personas.model;

import java.io.Serializable;

public class Combustibles implements Serializable {

    private static final long serialVersionUID = -6012888836184992901L;

    private Long id;

    private String nombre;

	public Combustibles() {
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
