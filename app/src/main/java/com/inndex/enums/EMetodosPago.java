package com.inndex.enums;

public enum EMetodosPago {

    EFECTIVO(1L),
    AMERICAN_EXPRESS(2L),
    DINERS_CLUB(3L),
    DISCOVER(4L),
    GOPASS(5L),
    MASTERCARD(6L),
    MERCADO_PAGO(7L),
    PAGO_CLICK(8L),
    PSE(9L),
    PUNTOS_COLOMBIA(10L),
    TPAGA(11L),
    VISA(12L),
    MAESTRO(13L),
    CREDITO_FACIL_CODENSA(14L);

    private Long id;

    public static EMetodosPago getMetodosPagoById(Long id) {
        for (EMetodosPago e : EMetodosPago.values()) {
            if (e.getId().equals(id)) {
                return e;
            }
        }
        return null;
    }

    EMetodosPago(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
