package com.inndex.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
@DatabaseTable(tableName = "soat")
public class Soat implements Serializable {

	private static final long serialVersionUID = 7040708063406647146L;

	@DatabaseField
	private Long id;

	@DatabaseField
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
