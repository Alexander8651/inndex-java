package com.inndex.car.personas.model;

import java.io.Serializable;
import java.sql.Time;

public class Horario implements Serializable {

	private static final long serialVersionUID = 5753635278466659563L;

	private Long id;

	private Time inicio;

	private Time fin;

	private Long dia;

	private Boolean abiertoSiempre;

	private Estaciones estaciones;

	public Horario() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Time getInicio() {
		return inicio;
	}

	public void setInicio(Time inicio) {
		this.inicio = inicio;
	}

	public Time getFin() {
		return fin;
	}

	public void setFin(Time fin) {
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
