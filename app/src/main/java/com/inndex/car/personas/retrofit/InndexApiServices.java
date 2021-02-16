package com.inndex.car.personas.retrofit;

import com.inndex.car.personas.constantes.IApiUrlConstants;
import com.inndex.car.personas.model.Bancos;
import com.inndex.car.personas.model.Certificados;
import com.inndex.car.personas.model.Combustibles;
import com.inndex.car.personas.model.Departamento;
import com.inndex.car.personas.model.EstacionCombustibles;
import com.inndex.car.personas.model.Estaciones;
import com.inndex.car.personas.model.Estados;
import com.inndex.car.personas.model.HistorialEstadoVehiculos;
import com.inndex.car.personas.model.LineasVehiculos;
import com.inndex.car.personas.model.MarcaVehiculos;
import com.inndex.car.personas.model.Municipio;
import com.inndex.car.personas.model.Pais;
import com.inndex.car.personas.model.Promocion;
import com.inndex.car.personas.model.PuntoPago;
import com.inndex.car.personas.model.Soat;
import com.inndex.car.personas.model.Tanqueadas;
import com.inndex.car.personas.model.Tiendas;
import com.inndex.car.personas.model.UnidadRecorrido;
import com.inndex.car.personas.model.Usuario;
import com.inndex.car.personas.model.Vehiculo;
import com.inndex.car.personas.to.EstacionesFiltros;
import com.inndex.car.personas.utils.Constantes;
import com.inndex.car.personas.utils.ResponseServices;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
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

    @PUT(Constantes.UPDATE_USER)
    Call<Usuario> updateUser(@Header("Content-Type") String headerContentType,
                             @Body Usuario usuario);

    /**
     * ESTACIONES
     */
    @GET(Constantes.GET_ALL_ESTACIONES)
    Call<List<Estaciones>> getEstaciones();

    @GET(Constantes.GET_BY_ID)
    Call<Estaciones> getEstacionById(@Query("id") Long id);

    @GET(Constantes.GET_BY_USUARIO_ADMIN)
    Call<List<Estaciones>> getEstacionesByUserAdmin(@Query("id") Long idUsuario);

    @POST(Constantes.POST_REGISTER_STATION)
    Call<ResponseServices> postRegisterStation(@Header("Content-Type") String headerContentType,
                                               @Body Estaciones estaciones);

    @PUT(Constantes.UPDATE_STATION_GENERAL_DATA)
    Call<ResponseServices> updateStationGeneralData(
                                                    @Body Estaciones estaciones);

    @PUT(Constantes.UPDATE_STATION_FUEL_AND_SCHEDULE)
    Call<ResponseServices> updateStationFuelAndSchedule(@Header("Content-Type") String headerContentType,
                                                        @Body Estaciones estaciones);

    @PUT(Constantes.UPDATE_STATION_OTHER_SERVICES)
    Call<ResponseServices> updateStationOtherServices(
                                                      @Body Estaciones estaciones);

    @GET(Constantes.GET_VEHICLES_BY_USER_ID)
    Call<List<Vehiculo>> getVehiclesByUser(@Query("idUsuario") Long idUsuario);

    @POST(Constantes.POST_CONSULT_COUNT_BY_FILTERS)
    Call<Long> postQueryCountByFilters(
            @Body List<EstacionesFiltros> filtros);

    //TANQUEADAS
    @POST(Constantes.POST_REGISTRAR_TANQUEADA)
    Call<ResponseServices> postRegisterTanqueada(@Header("Content-Type") String headerContentType,
                                                 @Body Tanqueadas tanqueada);

    @GET(Constantes.GET_TANQUEADAS_BY_USER + "{id}")
    Call<List<Tanqueadas>> getTanqueadasByUser(@Path("id") String id);

    /**
     * ESTACION COMBUSTIBLES
     */
    @POST(IApiUrlConstants.POST_SAVE_ALL_ESTACION_COMBUSTIBLE)
    Call<List<EstacionCombustibles>> postSaveAllEstacionesCombustibles(@Body List<EstacionCombustibles> listEstacionCombustibles);

    /**
     * PROMOCIONES
     */
    @GET(IApiUrlConstants.GET_PROMOCIONES_BY_ESTACION_ID)
    Call<List<Promocion>> getPromocionesByEstacionId(@Query("estacionId") Long estacionId);

    @POST(IApiUrlConstants.POST_SAVE_PROMOCION)
    Call<Promocion> postSavePromocion(Promocion promocion);

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
     * MODELOS CARROS
     */
    @GET(Constantes.GET_LINEAS_VEHICULOS_BY_MARCA)
    Call<List<LineasVehiculos>> getLineasVehiculosByMarca(
            @Query("idMarca") Long idMarca);

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
     * HISTORIAL ESTADOS
     */
    @POST(Constantes.POST_SAVE_HISTORIAL_ESTADO)
    Call<HistorialEstadoVehiculos> postHistorialEstadosSave(@Header("Content-Type") String headerContentType,
                                                            @Body HistorialEstadoVehiculos historialEstadoVehiculos);

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
}
