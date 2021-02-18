package com.inndex.car.personas.model;

import java.io.Serializable;

public class Horario implements Serializable {

	private static final long serialVersionUID = 5753635278466659563L;

	private Long id;

	private String inicio;

	private String fin;

	private Long dia;

	private boolean abiertoSiempre;

	private Estaciones estaciones;

	public Horario() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getInicio() {
		return inicio;
	}

	public void setInicio(String inicio) {
		this.inicio = inicio;
	}

	public String getFin() {
		return fin;
	}

	public void setFin(String fin) {
		this.fin = fin;
	}

	public Long getDia() {
		return dia;
	}

	public void setDia(Long dia) {
		this.dia = dia;
	}

	public Boolean getAbiertoSiempre() {
		return abiertoSiempre;
	}

	public void setAbiertoSiempre(Boolean abiertoSiempre) {
		this.abiertoSiempre = abiertoSiempre;
	}

	public Estaciones getEstaciones() {
		return estaciones;
	}

	public void setEstaciones(Estaciones estaciones) {
		this.estaciones = estaciones;
	}
}
