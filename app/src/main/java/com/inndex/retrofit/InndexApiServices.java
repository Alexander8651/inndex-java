package com.inndex.retrofit;

import com.inndex.constantes.IApiUrlConstants;
import com.inndex.model.AccesoriosYrepuestos;
import com.inndex.model.Bancos;
import com.inndex.model.Certificados;
import com.inndex.model.Combustibles;
import com.inndex.model.CompraYventa;
import com.inndex.model.Departamento;
import com.inndex.model.EstacionCalificacion;
import com.inndex.model.EstacionCombustibles;
import com.inndex.model.EstacionReporte;
import com.inndex.model.Estaciones;
import com.inndex.model.Estados;
import com.inndex.model.LineasVehiculos;
import com.inndex.model.MarcaEstacion;
import com.inndex.model.MarcaVehiculos;
import com.inndex.model.Mensajeria;
import com.inndex.model.MetodoPago;
import com.inndex.model.Municipio;
import com.inndex.model.Pais;
import com.inndex.model.Promocion;
import com.inndex.model.PuntoPago;
import com.inndex.model.Soat;
import com.inndex.model.Tanqueadas;
import com.inndex.model.Textos;
import com.inndex.model.Tiendas;
import com.inndex.model.UnidadRecorrido;
import com.inndex.model.Usuario;
import com.inndex.model.Vehiculo;
import com.inndex.to.EstacionesFiltros;
import com.inndex.utils.Constantes;
import com.inndex.utils.ResponseServices;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface InndexApiServices {

    @POST(Constantes.POST_LOGIN_USER)
    Call<Usuario> postLogin(@Header("Content-Type") String headerContentType,
                            @Body Usuario usuario);

    @POST(Constantes.POST_REGISTER_USER)
    Call<Usuario> postRegisterUser(@Body Usuario usuario);

    /**
     * USUARIO
     */
    @GET(Constantes.GET_USER_INFO_BY_ID)
    Call<Usuario> getUserInfoById(@Query("id") Long idUsuario);

    @POST(Constantes.UPDATE_USER)
    Call<Usuario> updateUser(@Body Usuario usuario);

    @PUT(IApiUrlConstants.UPDATE_USER_ACCOUNT_STATE)
    Call<Usuario> updateUserAccountState(@Body Usuario usuario);

    /**
     * ESTACIONES
     */
    @GET(Constantes.GET_ALL_ESTACIONES)
    Call<List<Estaciones>> getEstaciones();

    @GET(Constantes.GET_BY_ID)
    Call<Estaciones> getEstacionById(@Query("id") Long id);

    @GET(Constantes.GET_BY_USUARIO_ADMIN)
    Call<List<Estaciones>> getEstacionesByUserAdmin(@Query("id") Long idUsuario);

    @GET(IApiUrlConstants.GET_ESTACIONES_NEAR_USER)
    Call<List<Estaciones>> getEstacionesNearUser(@Query("latitud") Double latitud, @Query("longitud") Double longitud);

    @GET(IApiUrlConstants.GET_ESTACIONES_NEAR_USER_WITH_FUEL)
    Call<List<Estaciones>> getEstacionesNearUserWithFuel(@Query("latitud") Double latitud, @Query("longitud") Double longitud);

    @POST(Constantes.POST_REGISTER_STATION)
    Call<ResponseServices> postRegisterStation(@Header("Content-Type") String headerContentType,
                                               @Body Estaciones estaciones);

    @PUT(Constantes.UPDATE_STATION_GENERAL_DATA)
    Call<Estaciones> updateStationGeneralData(
            @Body Estaciones estaciones);

    @PUT(Constantes.UPDATE_STATION_SCHEDULE)
    Call<ResponseServices> updateStationSchedule(
            @Body Estaciones estaciones);

    @PUT(Constantes.UPDATE_STATION_OTHER_SERVICES)
    Call<ResponseServices> updateStationOtherServices(
            @Body Estaciones estaciones);


    /**
     * VEHÍCULOS
     */
    @GET(Constantes.GET_VEHICLES_BY_USER_ID)
    Call<List<Vehiculo>> getVehiclesByUser(@Query("idUsuario") Long idUsuario);

    @POST(Constantes.POST_CONSULT_COUNT_BY_FILTERS)
    Call<Long> postQueryCountByFilters(
            @Body List<EstacionesFiltros> filtros, @Query("latitud") double latitud, @Query("longitud") double longitud);

    @POST(IApiUrlConstants.POST_CONSULT_ESTACIONES_BY_FILTER)
    Call<List<Estaciones>> postQueryByFilters(
            @Body List<EstacionesFiltros> filtros, @Query("latitud") double latitud, @Query("longitud") double longitud);

    //TANQUEADAS
    @POST(Constantes.POST_REGISTRAR_TANQUEADA)
    Call<ResponseServices> postRegisterTanqueada(@Header("Content-Type") String headerContentType,
                                                 @Body Tanqueadas tanqueada);

    @GET(Constantes.GET_TANQUEADAS_BY_USER + "{id}")
    Call<List<Tanqueadas>> getTanqueadasByUser(@Path("id") String id);

    /**
     * ESTACION COMBUSTIBLES
     */
    @PUT(IApiUrlConstants.POST_SAVE_ALL_ESTACION_COMBUSTIBLE)
    Call<List<EstacionCombustibles>> postSaveAllEstacionesCombustibles(@Query("idEstacion") Long idEstacion, @Body List<EstacionCombustibles> listEstacionCombustibles);

    /**
     * PROMOCIONES
     */
    @GET(IApiUrlConstants.GET_PROMOCIONES_BY_ESTACION_ID)
    Call<List<Promocion>> getPromocionesByEstacionId(@Query("estacionId") Long estacionId);

    @POST(IApiUrlConstants.POST_SAVE_PROMOCION)
    Call<Promocion> postSavePromocion(@Body Promocion promocion);

    @DELETE(IApiUrlConstants.DELETE_PROMOCION)
    Call<Promocion> deletePromocion(@Query("id") Long promocionId);

    /**
     * COMBUSTIBLES
     */
    @GET(Constantes.GET_COMBUSTIBLES_ALL)
    Call<List<Combustibles>> getCombustiblesAll();

    /**
     * BANCOS
     */
    @GET(Constantes.GET_BANCOS)
    Call<List<Bancos>> getBancos();

    /**
     * PUNTOS PAGO
     */
    @GET(Constantes.GET_PUNTOS_PAGO)
    Call<List<PuntoPago>> getPuntosPago();

    /**
     * TIENDAS
     */
    @GET(Constantes.GET_TIENDAS)
    Call<List<Tiendas>> getTiendas();

    /**
     * SOAT
     */
    @GET(Constantes.GET_SOAT)
    Call<List<Soat>> getSoat();

    /**
     * MARCAS VEHICULOS
     */
    @GET(Constantes.GET_MARCAS_VEHICULOS)
    Call<List<MarcaVehiculos>> getMarcasVehiculos();

    /**
     * MARCAS ESTACIONES
     */
    @GET(IApiUrlConstants.GET_MARCAS_ESTACIONES)
    Call<List<MarcaEstacion>> getMarcasEstaciones();

    /**
     * METODOS PAGO
     */
    @GET(IApiUrlConstants.GET_METODOS_PAGO)
    Call<List<MetodoPago>> getMetodosPago();

    /**
     * MENSAJERIA
     */
    @GET(IApiUrlConstants.GET_MENSAJERIA)
    Call<List<Mensajeria>> getMensajeria();

    /**
     * MODELOS CARROS
     */
    @GET(Constantes.GET_LINEAS_VEHICULOS_BY_MARCA)
    Call<List<LineasVehiculos>> getLineasVehiculosByMarca(
            @Query("idMarca") Long idMarca);

    /**
     * ESTACION REPORTE
     */
    @POST(IApiUrlConstants.POST_SAVE_ESTACION_REPORTE)
    Call<EstacionReporte> postEstacionReporteSave(@Body EstacionReporte estacionReporte);

    /**
     * ESTACION CALIFICACION
     */
    @POST(IApiUrlConstants.POST_ESTACION_CALIFICACION_SAVE)
    Call<EstacionCalificacion> postEstacionCalificacionSave(@Body EstacionCalificacion estacionCalificacion);

    /**
     * RECORRIDOS
     */
    @POST(Constantes.POST_RECORRIDOS_BULK)
    Call<UnidadRecorrido> postRecorridosBulk(@Header("Content-Type") String headerContentType,
                                             @Body List<UnidadRecorrido> lUnidadRecorridos, @Query("placa") String placa);

    /**
     * ESTADOS
     */
    @GET(Constantes.GET_ESTADOS)
    Call<List<Estados>> getEstados();


    /**
     * CERTIFICADOS
     */
    @GET(Constantes.GET_CERTIFICADOS)
    Call<List<Certificados>> getCertificados();

    /**
     * PAIS
     */
    @GET(Constantes.GET_ALL_PAIS)
    Call<List<Pais>> getPaises();

    /**
     * DEPARTAMENTO
     */
    @GET(Constantes.GET_DEPARTAMENTOS_BY_PAIS_ID)
    Call<List<Departamento>> getDepartamentosByPaisId(@Query("id") Long id);

    /**
     * MUNICIPIOS
     */
    @GET(Constantes.GET_MUNICIPIOS_BY_DEPT_ID)
    Call<List<Municipio>> getMunicipiosByDeptId(@Query("id") Long id);

    /**
     * TEXTOS
     */
    @GET(IApiUrlConstants.GET_TEXTO_BY_ID)
    Call<Textos> getTextoById(@Query("id") Long id);

    /**
     * ACCESORIOS Y REPUESTOS
     */
    @GET(IApiUrlConstants.GET_ACCESORRIOS_Y_REPUESTOS)
    Call<List<AccesoriosYrepuestos>> getAccesoriosYrepuestos();

    /**
     * COMPRA Y VENTA
     */
    @GET(IApiUrlConstants.GET_COMPRA_Y_VENTA)
    Call<List<CompraYventa>> getCompraYventa();
}
