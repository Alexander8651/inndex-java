package com.inndex.car.personas.enums;

enum ETiendas {

    ALTOQUE(1L),
    ARA(2L),
    D_UNO(3L),
    EXITO_EXPRESS(4L),
    JUSTO_Y_BUENO(5L),
    OLIMPICA(6L),
    TIGER_MARKET(7L);

    private Long id;

    ETiendas(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
