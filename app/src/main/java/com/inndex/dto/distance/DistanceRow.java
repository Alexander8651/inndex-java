package com.inndex.dto.distance;

import java.io.Serializable;
import java.util.List;

class DistanceRow implements Serializable {
    List<Element> elements;

    public DistanceRow() {
    }

    public List<Element> getElements() {
        return elements;
    }

    public void setElements(List<Element> elements) {
        this.elements = elements;
    }
}
