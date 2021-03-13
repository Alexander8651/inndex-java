package com.inndex.enums;

public enum EPuntoPago {

    BALOTO(1L),
    EFECTY(2L),
    SU_RED(4L),
    GIROS_Y_FINANZAS(5L),
    MOVII_RED(6L),
    PAGA_FACIL(7L),
    PUNTO_RED(8L),
    REDEBAN(9L),
    SERVY_PAGOS(10L),
    SUPER_GIROS(11L),
    WESTERN_UNION(12L),
    OTRA(13L);
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
