package com.inndex.car.personas.enums;

public enum ECombustibles {

    CORRIENTE(1L),
    EXTRA(2L),
    DIESEL(3L),
    BIODIESEL(4L),
    GNV(5L);
    private Long id;

    ECombustibles(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
