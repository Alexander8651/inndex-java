package com.inndex.car.personas.constantes;

public interface IApiUrlConstants {

    // PROMOCIONES
    String POST_SAVE_PROMOCION = "promociones/save";
    String GET_PROMOCIONES_BY_ID = "promociones/getById";
    String GET_PROMOCIONES_BY_ESTACION_ID = "promociones/getByEstacionId";

    // ESTACION COMBUSTIBLES
    String POST_SAVE_ALL_ESTACION_COMBUSTIBLE = "estaciones/update-fuel";

    //ESTACION REPORTE
    String POST_SAVE_ESTACION_REPORTE = "estacion-reporte/save";

    String GET_MARCAS_ESTACIONES = "marca-estacion/getAll";
    String GET_METODOS_PAGO = "metodos-pago/getAll";

    //ESTACION CALIFICACION
    String POST_ESTACION_CALIFICACION_SAVE = "estacion-calificacion/save";

    //ESTACIONES
    String GET_ESTACIONES_NEAR_USER = "estaciones/getNearUser";

    //MENSAJERIA
    String GET_MENSAJERIA = "mensajeria/getAll";

    //textos
    String GET_TEXTO_BY_ID = "textos/getById";

}
