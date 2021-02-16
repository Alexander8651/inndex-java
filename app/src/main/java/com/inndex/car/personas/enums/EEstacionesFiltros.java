package com.inndex.car.personas.enums;

public enum EEstacionesFiltros {

    ABIERTO_AHORA(1L),
    BANIOS(2L),
    CAJEROS(3L),
    CALIFICACION(4L),
    CORRESPONSALES(5L),
    DISTANCIA(6L),
    HOTELES(7L),
    LAVADEROS(8L),
    LLANTERIA(9L),
    LUBRICANTES(10L),
    MARCAS(11L),
    PUNTOS_PAGO(12L),
    RESTAURANTES(13L),
    SOAT(14L),
    TIENDAS(15L),
    TIPO_COMBUSTIBLE(16L);

    private Long id;

    public static EEstacionesFiltros getEEstacionesFiltrosById(Long id) {
        for (EEstacionesFiltros e : EEstacionesFiltros.values()) {
            if (e.getId().equals(id)) {
                return e;
            }
        }
        return null;
    }

    EEstacionesFiltros(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
