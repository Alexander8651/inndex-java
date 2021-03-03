package com.inndex.car.personas.enums;

public enum ETipoReporte {

    DUPLICADO(1L),
    CERRADO_O_MOVIDO(2L),
    DETALLES_INCORRECTOS(3L);
    private Long id;

    ETipoReporte(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
