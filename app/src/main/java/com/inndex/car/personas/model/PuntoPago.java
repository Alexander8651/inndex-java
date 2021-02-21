package com.inndex.car.personas.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(tableName = "punto_pago")
public class PuntoPago implements Serializable {

	private static final long serialVersionUID = -5911948562682749995L;

	@DatabaseField
	private Long id;
	@DatabaseField
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
