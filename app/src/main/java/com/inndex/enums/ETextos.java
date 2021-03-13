package com.inndex.enums;

public enum ETextos {

    TERMINOS_Y_CONDICIONES(1L),
    TRATAMIENTO_DE_DATOS(2L),
    AUTORIZACION_USUARIOS(3L),
    UBICACION(4L);

    private Long id;

    ETextos(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
