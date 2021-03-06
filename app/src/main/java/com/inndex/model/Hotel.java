package com.inndex.model;

import java.io.Serializable;


public class Hotel implements Serializable {

    private static final long serialVersionUID = -3004153775198805047L;

    private Long id;

    private String nombre;

    private int numeroHabitaciones;

	private Estaciones estaciones;

	public Hotel() {
	}

	public Estaciones getEstaciones() {
		return estaciones;
	}

	public void setEstaciones(Estaciones estaciones) {
		this.estaciones = estaciones;
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

	public int getNumeroHabitaciones() {
		return numeroHabitaciones;
	}

	public void setNumeroHabitaciones(int numeroHabitaciones) {
		this.numeroHabitaciones = numeroHabitaciones;
	}
}
