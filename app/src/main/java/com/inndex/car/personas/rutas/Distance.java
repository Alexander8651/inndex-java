package com.inndex.car.personas.rutas;
/**
 * Created by oscar on 6/12/16.
 */

import java.io.Serializable;

public class Distance implements Serializable {
    private String text;
    private Integer value;

    public Distance() {
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
