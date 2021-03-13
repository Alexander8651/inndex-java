package com.inndex.to;

import com.inndex.model.Combustibles;

import java.io.Serializable;
import java.util.List;

public class EstacionesFiltros implements Serializable {

    private static final long serialVersionUID = -5773954076315195114L;

    private Long id;

    private List<String> listMarcas;

    private List<Long> listIdValues;

    private List<Combustibles> listCombustibles;

    private Long idCalificacion;

    public EstacionesFiltros() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<String> getListMarcas() {
        return listMarcas;
    }

    public void setListMarcas(List<String> listMarcas) {
        this.listMarcas = listMarcas;
    }

    public List<Long> getListIdValues() {
        return listIdValues;
    }

    public void setListIdValues(List<Long> listIdValues) {
        this.listIdValues = listIdValues;
    }

    public List<Combustibles> getListCombustibles() {
        return listCombustibles;
    }

    public void setListCombustibles(List<Combustibles> listCombustibles) {
        this.listCombustibles = listCombustibles;
    }

    public Long getIdCalificacion() {
        return idCalificacion;
    }

    public void setIdCalificacion(Long idCalificacion) {
        this.idCalificacion = idCalificacion;
    }
}
