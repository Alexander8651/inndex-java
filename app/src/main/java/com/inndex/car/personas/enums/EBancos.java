package com.inndex.car.personas.enums;

public enum EBancos {

    BANCO_AGRARIO_DE_COLOMBIA(1L),
    BANCO_AV_VILLAS(2L),
    BANCO_BBVA(3L),
    BANCO_CAJA_SOCIAL(4L),
    BANCO_COLPATRIA(5L),
    BANCO_COOPERATIVO_COOPCENTRAL(6L),
    BANCO_CORPBANCA(7L),
    BANCO_ITAU(8L),
    BANCO_DAVIVIENDA(9L),
    BANCO_DE_BOGOTA(10L),
    BANCO_DE_OCCIDENTE(11L),
    BANCO_FALABELLA(12L),
    BANCO_GNB_SUDAMERIS(13L),
    BANCO_OLD_MUTUAL(14L),
    BANCO_PICHINCHA(15L),
    BANCO_POPULAR(16L),
    BANCO_PROCREDIT(17L),
    BANCOLOMBIA(18L),
    BANCOOMEVA(19L),
    CITIBANK(20L),
    ATH(21L),
    SERVY_BANCA(22L);

    private Long id;

    public static EBancos getEBancosById(Long id) {
        for (EBancos e : EBancos.values()) {
            if (e.getId().equals(id)) {
                return e;
            }
        }
        return null;
    }

    EBancos(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
