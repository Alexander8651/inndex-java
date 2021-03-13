package com.inndex.enums;

public enum ETiendas {

    ALTOQUE(1L),
    ARA(2L),
    D_UNO(3L),
    EXITO_EXPRESS(4L),
    JUSTO_Y_BUENO(5L),
    OLIMPICA(6L),
    TIGER_MARKET(7L),
    BEST_MART(8L),
    DOLLARCITY(9L),
    OXXO(10L),
    SPACIO_UNO(11L),
    ON_THE_RUN(12L),
    LA_DESPENSA(13L),
    EXPRESS(14L),
    MAKRO(15L),
    STAR_MART(16L),
    SURTIMAX(17L),
    METRO(18L),
    OTRA(19L);

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
