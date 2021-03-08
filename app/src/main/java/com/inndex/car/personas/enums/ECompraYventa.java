package com.inndex.car.personas.enums;

public enum ECompraYventa {

    CARROYA_PUNTO_COM(1L),
    OLX_AUTOS(2L),
    TUCARRO_PUNTO_COM(3L),
    OTRA(4L);

    private Long id;

    public static ECompraYventa getEECompraYventaById(Long id) {
        for (ECompraYventa e : ECompraYventa.values()) {
            if (e.getId().equals(id)) {
                return e;
            }
        }
        return null;
    }

    ECompraYventa(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
