package com.inndex.car.personas.dto.distance;

import com.inndex.car.personas.rutas.Distance;

import java.io.Serializable;
import java.util.List;

public class DistanceApiResponse implements Serializable {

    private String status;
    private List<DistanceRow> rows;

    public DistanceApiResponse() {
    }

    public List<DistanceRow> getRows() {
        return rows;
    }

    public void setRows(List<DistanceRow> rows) {
        this.rows = rows;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getDistanceValue() {

        if (rows != null && rows.size() > 0) {
            List<Element> elements = rows.get(0).getElements();
            if (elements != null && elements.size() > 0) {
                Element el = elements.get(0);
                if (el != null) {
                    Distance distance = el.getDistance();
                    return distance.getValue();
                }
            }
        }
        return null;
    }
}
