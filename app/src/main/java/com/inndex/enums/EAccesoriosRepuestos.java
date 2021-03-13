package com.inndex.enums;

public enum EAccesoriosRepuestos {

    CARROS(1L),
    MOTOS(2L);

    private Long id;

    public static EAccesoriosRepuestos getEAccesoriosById(Long id) {
        for (EAccesoriosRepuestos e : EAccesoriosRepuestos.values()) {
            if (e.getId().equals(id)) {
                return e;
            }
        }
        return null;
    }

    EAccesoriosRepuestos(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
