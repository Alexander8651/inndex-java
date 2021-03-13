package com.inndex.enums;

public enum ECombustibles {

    CORRIENTE(1L),
    EXTRA(2L),
    DIESEL(3L),
    BIODIESEL(4L),
    GNV(5L),
    MAX_PRO_DIESEL(6L);
    private Long id;

    public static ECombustibles getECombustiblesById(Long id) {
        for (ECombustibles e : ECombustibles.values()) {
            if (e.getId().equals(id)) {
                return e;
            }
        }
        return null;
    }

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
