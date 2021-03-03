package com.inndex.car.personas.enums;

public enum EMensajeria {

    CUATRO_SETENTA_Y_DOS(1L),
            AVIANCA(2L),
            CADENA(3L),
            CALI_EXPRESS(4L),
            CARVAJAL(5L),
            CANTAURUS_MENSAJEROS(6L),
            COORDINADORA(7L),
            COPETRAN(8L),
            DEPRISA(9L),
            DHL(10L),
            DOMESA(11L),
            DOMINA(12L),
            EIS(13L),
            ENVIA(14L),
            FEDEX(15L),
            INTERRAPIDISIMO(16L),
            MC(17L),
            SERVI_ENTREGA(18L),
            TIEMPO_EXPRESS(19L),
            THOMAS(20L);

    private Long id;

    public static EMensajeria getEMensajeriaById(Long id) {
        for (EMensajeria e : EMensajeria.values()) {
            if (e.getId().equals(id)) {
                return e;
            }
        }
        return null;
    }

    EMensajeria(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
