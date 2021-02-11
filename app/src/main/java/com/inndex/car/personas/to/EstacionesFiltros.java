package com.inndex.car.personas.to;

import com.inndex.car.personas.model.Combustibles;

import java.io.Serializable;
import java.util.List;

public class EstacionesFiltros implements Serializable {

    private static final long serialVersionUID = -5773954076315195114L;

    private Long id;

    private List<String> listMarcas;

    private List<Long> listIdValues;

    private List<Combustibles> listCombustibles;

    private Long idCalificacion;

}
