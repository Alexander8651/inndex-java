package com.inndex.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(tableName = "tiendas")
public class Tiendas implements Serializable {

	private static final long serialVersionUID = -3004153775198805047L;

	@DatabaseField
	private Long id;
	@DatabaseField
	private String nombre;

	public Tiendas() {
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
