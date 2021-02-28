package com.inndex.car.personas.constantes;

public interface IApiUrlConstants {

    // PROMOCIONES
    String POST_SAVE_PROMOCION = "promociones/save";
    String GET_PROMOCIONES_BY_ID = "promociones/getById";
    String GET_PROMOCIONES_BY_ESTACION_ID = "promociones/getByEstacionId";

    // ESTACION COMBUSTIBLES
    String POST_SAVE_ALL_ESTACION_COMBUSTIBLE = "estaciones/update-fuel";

    //ESTACION PROBLEMA
    String POST_SAVE_ESTACION_PROBLEMA = "estacion-problema/save";
    String GET_MARCAS_ESTACIONES = "marca-estacion/getAll";
    String GET_METODOS_PAGO = "metodos-pago/getAll";

    //ESTACIONES
    String GET_ESTACIONES_NEAR_USER = "estaciones/getNearUser";

    //MENSAJERIA
    String GET_MENSAJERIA = "mensajeria/getAll";

}
