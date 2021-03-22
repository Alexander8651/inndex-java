package com.inndex.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(tableName = "usuarios")
public class Usuario implements Serializable {

    @DatabaseField
    private long id;
    @DatabaseField
    private String email;
    @DatabaseField
    private String password;
    @DatabaseField
    private String identificacion;
    @DatabaseField
    private String nombres;
    @DatabaseField
    private String apellidos;
    @DatabaseField
    private String celular;
    @DatabaseField
    private String telefono;
    @DatabaseField
    private String telefonoIndicativo;
    @DatabaseField
    private Integer tipo;
    @DatabaseField
    private Integer genero;

    private Integer estadoCuenta;


    //Formato: yyyy-MM-dd
    private String fechaNacimiento;

    public Usuario(String email, String identificacion, String nombres, String apellidos, String celular, String fechaNacimiento) {
        this.email = email;
        this.identificacion = identificacion;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.celular = celular;
        this.fechaNacimiento = fechaNacimiento;
    }

    public Usuario() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getTelefonoIndicativo() {
        return telefonoIndicativo;
    }

    public void setTelefonoIndicativo(String telefonoIndicativo) {
        this.telefonoIndicativo = telefonoIndicativo;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public Integer getGenero() {
        return genero;
    }

    public void setGenero(Integer genero) {
        this.genero = genero;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Integer getEstadoCuenta() {
        return estadoCuenta;
    }

    public void setEstadoCuenta(Integer estadoCuenta) {
        this.estadoCuenta = estadoCuenta;
    }
}
