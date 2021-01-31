package com.inndex.car.personas.retrofit;

import com.inndex.car.personas.model.Certificados;
import com.inndex.car.personas.model.Departamento;
import com.inndex.car.personas.model.Estaciones;
import com.inndex.car.personas.model.Estados;
import com.inndex.car.personas.model.HistorialEstadoVehiculos;
import com.inndex.car.personas.model.MarcaCarros;
import com.inndex.car.personas.model.ModeloCarros;
import com.inndex.car.personas.model.Municipio;
import com.inndex.car.personas.model.Pais;
import com.inndex.car.personas.model.Tanqueadas;
import com.inndex.car.personas.model.UnidadRecorrido;
import com.inndex.car.personas.model.Usuario;
import com.inndex.car.personas.model.Vehiculo;
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
    Call<ResponseServices> postRegisterUser(@Header("strUsuario") String jsonUsuario,
                                            @Body String dummy);

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

    //TANQUEADAS
    @POST(Constantes.POST_REGISTRAR_TANQUEADA)
    Call<ResponseServices> postRegisterTanqueada(@Header("Content-Type") String headerContentType,
                                                 @Body Tanqueadas tanqueada);

    @GET(Constantes.GET_TANQUEADAS_BY_USER + "{id}")
    Call<List<Tanqueadas>> getTanqueadasByUser(@Path("id") String id);

    /**
     * USUARIO HAS MODELO CARROS
     */
    @POST(Constantes.POST_REGISTRAR_USUARIO_HAS_MODELO_CARRO + "{idMarca}" + "/" + "{linea}")
    Call<Vehiculo> postRegisterUsuarioHasModeloCarro(@Header("Content-Type") String headerContentType,
                                                     @Path("idMarca") String idMarca,
                                                     @Path("linea") String linea,
                                                     @Body Vehiculo uhmc);

    @PUT(Constantes.PUT_UPDATE_USUARIO_HAS_MODELO_CARRO)
    Call<Vehiculo> putUpdateUsuarioHasModeloCarro(@Header("Content-Type") String headerContentType,
                                                  @Body Vehiculo uhmc);

    @GET(Constantes.GET_USUARIO_HAS_MODELO_CARROS_BY_ID_USER + "{idUsuario}")
    Call<List<Vehiculo>> getUsuarioHasModeloCarros(@Path("idUsuario") String idUsuario);

    /**
     * MODELOS CARROS
     */
    @POST(Constantes.POST_REGISTER_MODELO_CARRO)
    Call<ModeloCarros> postRegisterModelo(@Header("Content-Type") String headerContentType,
                                          @Body ModeloCarros modeloCarros);

    @GET(Constantes.GET_MODELOS_CARROS_BY_MARCA + "{idMarca}")
    Call<List<ModeloCarros>> getModelosCarrosByMarca(@Path("idMarca") String idMarca);

    /**
     * MARCAS CARROS
     */
    @GET(Constantes.GET_MARCAS_CARROS)
    Call<List<MarcaCarros>> getMarcasCarros();

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