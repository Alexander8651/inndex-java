package com.inndex.car.personas.enums;

public enum EEvents {

    ESTACION_MARKER_SELECTED(1),
    SHOW_ORIGINAL_MENU(2),
    NAVIGATE(3),
    DRAW_ROUTE(4),
    BACK_BUTTON_PRESSED(5),
    ESTACIONES_MAP_FRAGMENT_VISIBLE(6),
    ESTACIONES_MAP_FRAGMENT_GONE(7);

    private Integer id;

    public static EEvents getEventsById(Integer id) {
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
