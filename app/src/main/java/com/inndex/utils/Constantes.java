package com.inndex.utils;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class Constantes {

    public static final String BASE_URL_FOURSQUARE = "https://api.foursquare.com/v2/venues/";
    public static final int LIMIT_RESPONSE = 5;

    public static final String ESTACION_BUNDLE = "estacionIs";

    public static final String LATITUD_KEY = "latitud";
    public static final String LONGITUD_KEY = "longitud";
    public static final String FILTROS_KEY = "filtros";
    public static final String SHARED_PREFERENCES_FILE_KEY = "inndex_preferences";

    //FORESQUARE
    public static final String FORESQUARE_CLIENT_ID = "JBTWYW5ECKIM0VH5GHYFAMRKXD320PHVV33L5ELFILGMJZFH";
    public static final String FORESQUARE_CLIENT_SECRET = "QXD3QHZ5OIA2EYKV43HCDZWZXH3YWUIGNHPCQFOPEKYCWKWM";

    //USUARIO
    public static final String POST_LOGIN_USER = "users/postLogin/";
    public static final String POST_REGISTER_USER = "users/postRegistrar";

    //ESTACIONES
    public static final String GET_ALL_ESTACIONES = "estaciones/getAll";
    public static final String GET_BY_ID = "estaciones/getById";
    public static final String GET_BY_USUARIO_ADMIN = "estaciones-usuario/getAllByUsuarioAdmin";
    public static final String POST_REGISTER_STATION = "estaciones/postRegistrar";
    public static final String UPDATE_STATION_GENERAL_DATA = "estaciones/update-general-data";
    public static final String UPDATE_STATION_SCHEDULE = "estaciones/update-schedule";
    public static final String UPDATE_STATION_OTHER_SERVICES = "estaciones/update-other-services";
    public static final String POST_CONSULT_COUNT_BY_FILTERS = "estaciones/post_query_count_by_filters";

    //TANQUEADAS
    public static final String POST_REGISTRAR_TANQUEADA = "tanqueadas/postRegistrar";
    public static final String GET_TANQUEADAS_BY_USER = "tanqueadas/getByUser/";

    //Vehiculos
    public static final String POST_SAVE_VEHICLE = "usuarioHasModeloCarros/";
    public static final String PUT_UPDATE_VEHICLE = "usuarioHasModeloCarros/update";
    public static final String GET_VEHICLES_BY_USER_ID = "vehiculos/getAllByUser";
    public static final String DELETE_VEHICLES_BY_ID = "usuarioHasModeloCarros/getAllByUser/";

    //COMBUSTIBLES
    public static final String GET_COMBUSTIBLES_ALL = "combustibles/getAll";

    //PAIS
    public static final String GET_ALL_PAIS = "pais/getAll/";

    //DEPARTAMENTO
    public static final String GET_DEPARTAMENTOS_BY_PAIS_ID = "departamento/get-by-pais-id/";

    //MUNICIPIOS
    public static final String GET_MUNICIPIOS_BY_DEPT_ID = "municipio/get-by-departamento-id/";

    //API KEY DE PONTON

    //public static final String API_KEY_PLACES = "AIzaSyBHLkx2pOIKyoiTYjw-c78ValF4iHktcjc";
    //public static final String API_KEY_PLACES = "AIzaSyBizdZCPln0q6WvLBJ4DGorvKgQ857k8qw";
    public static final String API_KEY_PLACES = "AIzaSyBizdZCPln0q6WvLBJ4DGorvKgQ857k8qw";

    //RECORRIDOS
    public static final String POST_RECORRIDOS_BULK = "recorridos/bulk";

    public static final String GET_RECORRIDOS_BY_USER = "recorridos/idUsuario";

    public static final String URL_PLACES = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location={lat},{long}&radius=1200&type=gas_station&key=";
    public static final String URL_HTTP = "https://maps.googleapis.com/maps/api/directions/json?origin=";

    //CERTIFICADOS
    public static final String GET_CERTIFICADOS = "certificados/getAll";

    /**
     * MARCAS VEHICULOS
     */
    public static final String GET_MARCAS_VEHICULOS = "marcas-vehiculos/getAll";

    /**
     * LINEAS VEHICULOS
     */
    public static final String GET_LINEAS_VEHICULOS_BY_MARCA = "lineas-vehiculos/getByMarcaId";

    public static final String DEFAULT_USER_ID = "idUsuario";

    public static final String GET_ESTADOS = "estados/getAll";
    public static final String POST_SAVE_HISTORIAL_ESTADO = "historial-estados-vehiculos/save";
    public static final String ESTACION_SELECCIONADA_KEY = "estacionSeleccionada";

    public static final String GET_USER_INFO_BY_ID = "users/get-by-id";
    public static final String UPDATE_USER = "users/update-account-info";
    
    public static final String GET_BANCOS = "bancos/getAll";
    public static final String GET_PUNTOS_PAGO = "puntos-pago/getAll";
    public static final String GET_TIENDAS = "tiendas/getAll";
    public static final String GET_SOAT = "soat/getAll";
    public static final String ADMIN_PHONENUMBER = "573194617069";

    public static int ROTATION = 0;

    public static int ARRAY_DATA_SIZE = 5;

    public static final String DEFAULT_BLUETOOTH_VALUE_ARRAY = "defaultValuesBluetoothArray";
    public static final String DEFAULT_BLUETOOTH_MAC = "defaultBluetoothMac";
    public static final String CONTENT_TYPE_JSON = "application/json";
    public static final String DEFAULT_GAL_CANT = "defaultGalCant";
    public static final String MODEL_HAS_TWO_TANKS = "modelHasTwoTanks";
    public static final String STOP_RECORRIDO_INTENT_FILTER = "RECORRIDO_INTENT_FILTER";
    public static final String START_RECORRIDO_INTENT_FILTER = "START_RECORRIDO_INTENT_FILTER";
    public static final String DEFAULT_VEHICLE_ID = "defaultUhmcId";
    public static final String DEFAULT_PLACA = "defaultPlaca";
    public static final String DEFAULT_STATE = "defaultState";
    public static final String DEFAULT_STATE_ID = "defaultStateId";

    public static final String SESION_ACTIVE = "sesionMedidor";

    public static final double LAT_LNG_TOLERANCE = 0.002;

    public static final long DELAY_RECORRIDO = 1000;
    public static final long LIMIT_UNIT_RECORRIDO = 2000;
    //    public static final long DELAY_UPLOAD_RECORIDOS = 1800000;
    public static final long DELAY_UPLOAD_RECORIDOS = 300000;
    public static final long INTERVAL_UPLOAD_UNIT_RECORRIDO = 600000;

    public static final long TIMEOUT = 500;

    public static final SimpleDateFormat SDF_FOR_BACKEND = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);

    public static final SimpleDateFormat SDF_DATE_ONLY = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

    public static final SimpleDateFormat SDF_DATE_RECORRIDO = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);

    public static final SimpleDateFormat SDF_HOUR_RECORRIDO = new SimpleDateFormat("HH:mm:ss", Locale.US);

    public static String generateRandomRecorridoCode() {

        SimpleDateFormat sdfRandom = new SimpleDateFormat("yyyyMMddHHmm", Locale.US);
        return sdfRandom.format(new Date()) + UUID.randomUUID().toString();

    }

    public static float getDistance(LatLng myPosition, LatLng estacionLatLng) {

        float[] results = {0};
        Location.distanceBetween(myPosition.latitude, myPosition.longitude, estacionLatLng.latitude, estacionLatLng.longitude, results);
        return results[0];
    }

    public static String[] getYearsModelsCars() {

        int year = Calendar.getInstance().get(Calendar.YEAR);
        String[] years = new String[50];
        years[0] = String.valueOf(year);

        for (int i = 1; i < 50; i++) {
            years[i] = String.valueOf(year - i);
        }

        return years;
    }

    /**
     * Constants for FetchAddressIntentService
     */
    public static final int SUCCESS_RESULT = 0;
    public static final int FAILURE_RESULT = 1;
    public static final String PACKAGE_NAME =
            "com.google.android.gms.location.sample.locationaddress";
    public static final String RECEIVER = PACKAGE_NAME + ".RECEIVER";
    public static final String RESULT_DATA_KEY = PACKAGE_NAME +
            ".RESULT_DATA_KEY";
    public static final String LOCATION_DATA_EXTRA = PACKAGE_NAME +
            ".LOCATION_DATA_EXTRA";

    public static String obtenerDireccionesURL(Double latitud_origen, Double longitud_origen, Double latitud_destino,
                                               Double longitud_destino, String mapsApiKey) {

        String str_key = "key=" + mapsApiKey;
        String str_origin = "origin=" + latitud_origen + "," + longitud_origen;
        String str_dest = "destination=" + latitud_destino + "," + longitud_destino;
        String sensor = "sensor=false";
        String parameters = str_key + "&" + str_origin + "&" + str_dest + "&" + sensor;
        String url = "https://maps.googleapis.com/maps/api/directions/json?" + parameters;
        return url;
    }
}
