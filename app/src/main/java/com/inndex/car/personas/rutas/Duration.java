package com.inndex.car.personas.rutas;

/**
 * Created by oscar on 6/12/16.
 */

import java.io.Serializable;

public class Duration implements Serializable {
    private String text;
    private Integer value;

    public Duration() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
