package com.inndex.car.personas.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(tableName = "combustibles")
public class Combustibles implements Serializable {

    private static final long serialVersionUID = -6012888836184992901L;

	@DatabaseField
    private Long id;
	@DatabaseField
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
