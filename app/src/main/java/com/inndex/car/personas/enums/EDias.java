package com.inndex.car.personas.enums;

public enum EDias {

    DOMINGO(1L,"Domingo"),
    LUNES(2L,"Lunes"),
    MARTES(3L,"Martes"),
    MIERCOLES(4L,"Miercoles"),
    JUEVES(5L,"Jueves"),
    VIERNES(6L,"Viernes"),
    SABADO(7L,"Sábado");

    private Long id;
    private String nombre;

    EDias(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
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
}
