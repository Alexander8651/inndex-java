package com.inndex.car.personas.enums;

public enum EEvents {

    ESTACION_MARKER_SELECTED(1),
    SHOW_ORIGINAL_MENU(2);

    private Integer id;

    public static EEvents getEBancosById(Integer id) {
        for (EEvents e : EEvents.values()) {
            if (e.getId().equals(id)) {
                return e;
            }
        }
        return null;
    }

    EEvents(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
