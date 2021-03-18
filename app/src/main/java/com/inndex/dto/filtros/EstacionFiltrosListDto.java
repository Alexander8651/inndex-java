package com.inndex.dto.filtros;

import com.inndex.to.EstacionesFiltros;

import java.io.Serializable;
import java.util.List;

public class EstacionFiltrosListDto implements Serializable {

    private List<EstacionesFiltros> listEstacionesFiltros;

    public EstacionFiltrosListDto() {
    }

    public EstacionFiltrosListDto(List<EstacionesFiltros> listEstacionesFiltros) {
        this.listEstacionesFiltros = listEstacionesFiltros;
    }

    public List<EstacionesFiltros> getListEstacionesFiltros() {
        return listEstacionesFiltros;
    }

    public void setListEstacionesFiltros(List<EstacionesFiltros> listEstacionesFiltros) {
        this.listEstacionesFiltros = listEstacionesFiltros;
    }
}
