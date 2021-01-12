package com.inndex.car.personas.model;

import com.google.android.gms.maps.model.LatLng;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.List;

@DatabaseTable(tableName = "estaciones")
public class Estaciones implements Serializable {

    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(canBeNull = false)
    private String nombre;
    @DatabaseField
    private Float distancia;
    @DatabaseField
    private String direccion;
    @DatabaseField
    private String horario;
    @DatabaseField
    private double calificacion;
    @DatabaseField
    private double latitud;
    @DatabaseField
    private double longitud;
    @DatabaseField
    private String marca;
    @DatabaseField
    private String departamento;
    @DatabaseField
    private String municipio;
    @DatabaseField
    private boolean certificada;
    @DatabaseField
    private String descripcionCertificado;

    private Boolean tieneBanios;

    private Boolean tieneLlanteria;

    private Boolean tieneLavadero;

    private Boolean tieneVentaLubricante;

    private List<Restaurante> listRestaurantes;

    private List<Hotel> listHoteles;

    private List<Bancos> listCajeros;

    private List<Bancos> listCorresponsales;

    private List<Horario> listHorarios;

    private List<EstacionCombustibles> listEstacionCombustibles;

    private List<Tiendas> listTiendas;

    private List<PuntoPago> listPuntosPago;

    private Soat soat;

    private TipoEstacion tipoEstacion;

    public Estaciones() {
    }

    public Estaciones(String nombre, double latitud, double longitud) {
        this.nombre = nombre;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public float getDistancia() {
        return 0;
    }

    public void setDistancia(Float distancia) {
        this.distancia = distancia;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public double getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(double calificacion) {
        this.calificacion = calificacion;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) { this.latitud = latitud; }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public LatLng getCoordenadas(){
        return new LatLng(this.latitud, this.longitud);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public boolean isCertificada() {
        return certificada;
    }

    public void setCertificada(boolean certificada) {
        this.certificada = certificada;
    }

    public String getDescripcionCertificado() {
        return descripcionCertificado;
    }

    public void setDescripcionCertificado(String descripcionCertificado) {
        this.descripcionCertificado = descripcionCertificado;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Boolean getTieneBanios() {
        return tieneBanios;
    }

    public void setTieneBanios(Boolean tieneBanios) {
        this.tieneBanios = tieneBanios;
    }

    public Boolean getTieneLlanteria() {
        return tieneLlanteria;
    }

    public void setTieneLlanteria(Boolean tieneLlanteria) {
        this.tieneLlanteria = tieneLlanteria;
    }

    public Boolean getTieneLavadero() {
        return tieneLavadero;
    }

    public void setTieneLavadero(Boolean tieneLavadero) {
        this.tieneLavadero = tieneLavadero;
    }

    public Boolean getTieneVentaLubricante() {
        return tieneVentaLubricante;
    }

    public void setTieneVentaLubricante(Boolean tieneVentaLubricante) {
        this.tieneVentaLubricante = tieneVentaLubricante;
    }

    public List<Restaurante> getListRestaurantes() {
        return listRestaurantes;
    }

    public void setListRestaurantes(List<Restaurante> listRestaurantes) {
        this.listRestaurantes = listRestaurantes;
    }

    public List<Hotel> getListHoteles() {
        return listHoteles;
    }

    public void setListHoteles(List<Hotel> listHoteles) {
        this.listHoteles = listHoteles;
    }

    public List<Bancos> getListCajeros() {
        return listCajeros;
    }

    public void setListCajeros(List<Bancos> listCajeros) {
        this.listCajeros = listCajeros;
    }

    public List<Bancos> getListCorresponsales() {
        return listCorresponsales;
    }

    public void setListCorresponsales(List<Bancos> listCorresponsales) {
        this.listCorresponsales = listCorresponsales;
    }

    public List<Horario> getListHorarios() {
        return listHorarios;
    }

    public void setListHorarios(List<Horario> listHorarios) {
        this.listHorarios = listHorarios;
    }

    public List<EstacionCombustibles> getListEstacionCombustibles() {
        return listEstacionCombustibles;
    }

    public void setListEstacionCombustibles(List<EstacionCombustibles> listEstacionCombustibles) {
        this.listEstacionCombustibles = listEstacionCombustibles;
    }

    public List<Tiendas> getListTiendas() {
        return listTiendas;
    }

    public void setListTiendas(List<Tiendas> listTiendas) {
        this.listTiendas = listTiendas;
    }

    public List<PuntoPago> getListPuntosPago() {
        return listPuntosPago;
    }

    public void setListPuntosPago(List<PuntoPago> listPuntosPago) {
        this.listPuntosPago = listPuntosPago;
    }

    public Soat getSoat() {
        return soat;
    }

    public void setSoat(Soat soat) {
        this.soat = soat;
    }

    public TipoEstacion getTipoEstacion() {
        return tipoEstacion;
    }

    public void setTipoEstacion(TipoEstacion tipoEstacion) {
        this.tipoEstacion = tipoEstacion;
    }
}
