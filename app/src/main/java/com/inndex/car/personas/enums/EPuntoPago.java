package com.inndex.car.personas.enums;

public enum EPuntoPago {

    BALOTO(1L),
    EFECTY(2L),
    PAGA_TODO(3L),
    SU_RED(4L);
    private Long id;

    EPuntoPago(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
