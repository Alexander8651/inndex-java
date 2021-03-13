package com.inndex.dto.distance;

import com.inndex.rutas.Distance;
import com.inndex.rutas.Duration;

import java.io.Serializable;

public class Element implements Serializable {

    private Duration duration;

    private Distance distance;

    private String status;

    public Element() {

    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public Distance getDistance() {
        return distance;
    }

    public void setDistance(Distance distance) {
        this.distance = distance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
