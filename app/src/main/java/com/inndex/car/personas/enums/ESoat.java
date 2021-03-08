package com.inndex.car.personas.enums;

public enum ESoat {

    ALLIANZ(1L),
    AXA_COLPATRIA(2L),
    LIBERTY_SEGUROS(3L),
    MAPRE_SEGUROS(4L),
    MUNDIAL_DE_SEGUROS(5L),
    SEGUROS_BOLIVAR(6L),
    SEGUROS_DEL_ESTADO(7L),
    SEGUROS_EXITO(8L),
    SEGUROS_FALABELLA(9L),
    SEGUROS_LA_EQUIDAD(10L),
    SURA_SEGUROS(11L),
    DEO_SEGUROS(12L),
    OTRA(13L);

    private Long id;

    ESoat(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
