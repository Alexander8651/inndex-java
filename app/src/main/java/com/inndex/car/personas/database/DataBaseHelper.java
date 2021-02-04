package com.inndex.car.personas.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.inndex.car.personas.R;
import com.inndex.car.personas.model.Estaciones;
import com.inndex.car.personas.model.Estados;
import com.inndex.car.personas.model.HistorialEstadoVehiculos;
import com.inndex.car.personas.model.LineasVehiculos;
import com.inndex.car.personas.model.Recorrido;
import com.inndex.car.personas.model.Tanqueadas;
import com.inndex.car.personas.model.UnidadRecorrido;
import com.inndex.car.personas.model.Usuario;
import com.inndex.car.personas.model.Vehiculo;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class DataBaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "medidor.db";
    private static final int VERSION = 1;

    private Dao<Usuario, Integer> daoUsuario = null;
    private RuntimeExceptionDao<Usuario, Integer> usuarioRuntimeDao = null;

    private Dao<Tanqueadas, Integer> daoTanqueadas = null;
    private RuntimeExceptionDao<Tanqueadas, Integer> tanqueadasRuntimeDao = null;

    private Dao<Estaciones, Integer> daoEstaciones = null;
    private RuntimeExceptionDao<Estaciones, Integer> estacionesRuntimeDao = null;

    private Dao<Recorrido, Integer> daoRecorrido = null;
    private RuntimeExceptionDao<Recorrido, Integer> recorridoRuntimeDao = null;

    private Dao<LineasVehiculos, Integer> daoModelos = null;
    private RuntimeExceptionDao<LineasVehiculos, Integer> modelosRuntimeDao = null;

    private Dao<Vehiculo, Integer> daoUsuarioHasModeloCarros = null;
    private RuntimeExceptionDao<Vehiculo, Integer> usuarioHasModeloCarroRuntimeDao = null;

    private Dao<UnidadRecorrido, Integer> daoUnidadRecorrido = null;
    private RuntimeExceptionDao<UnidadRecorrido, Integer> unidadRecorridoRuntimeDao = null;

    private Dao<Estados, Integer> daoEstados= null;
    private RuntimeExceptionDao<Estados, Integer> estadoRuntimeDao = null;

    private Dao<HistorialEstadoVehiculos, Integer> daoHistorialEstados= null;
    private RuntimeExceptionDao<HistorialEstadoVehiculos, Integer> historialEstadosRuntimeDao = null;

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION, R.raw.medidor_config);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {

        try {
            TableUtils.createTable(connectionSource, Estaciones.class);
            TableUtils.createTable(connectionSource, Estados.class);
            TableUtils.createTable(connectionSource, HistorialEstadoVehiculos.class);
            TableUtils.createTable(connectionSource, LineasVehiculos.class);
            TableUtils.createTable(connectionSource, Recorrido.class);
            TableUtils.createTable(connectionSource, Tanqueadas.class);
            TableUtils.createTable(connectionSource, UnidadRecorrido.class);
            TableUtils.createTable(connectionSource, Usuario.class);
            TableUtils.createTable(connectionSource, Vehiculo.class);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connection, int oldVersion, int newVersion) {

        try {
            TableUtils.dropTable(connection, Estados.class, true);
            TableUtils.dropTable(connection, Usuario.class, true);
            TableUtils.dropTable(connection, Estaciones.class, true);
            TableUtils.dropTable(connection, Tanqueadas.class, true);
            TableUtils.dropTable(connection, Recorrido.class, true);
            TableUtils.dropTable(connection, LineasVehiculos.class,true);
            TableUtils.dropTable(connection, UnidadRecorrido.class,true);
            TableUtils.dropTable(connection, Vehiculo.class,true);
            TableUtils.dropTable(connection, HistorialEstadoVehiculos.class,true);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    public Dao<Usuario, Integer> getDaoUsuario() throws SQLException {
        if(daoUsuario == null) daoUsuario = getDao(Usuario.class);
        return daoUsuario;
    }

    public RuntimeExceptionDao<Usuario, Integer> getUsuarioRuntimeDao() {
        if(usuarioRuntimeDao == null) usuarioRuntimeDao = getRuntimeExceptionDao(Usuario.class);
        return usuarioRuntimeDao;
    }

    public Dao<Tanqueadas, Integer> getDaoTanqueadas() throws SQLException {
        if(daoTanqueadas == null) daoTanqueadas = getDao(Tanqueadas.class);
        return daoTanqueadas;
    }

    public RuntimeExceptionDao<Tanqueadas, Integer> getTanqueadasRuntimeDao() {
        if(tanqueadasRuntimeDao == null) tanqueadasRuntimeDao = getRuntimeExceptionDao(Tanqueadas.class);
        return tanqueadasRuntimeDao;
    }

    public Dao<Estaciones, Integer> getDaoEstaciones() throws SQLException {
        if(daoEstaciones == null) daoEstaciones = getDao(Estaciones.class);
        return daoEstaciones;
    }

    public RuntimeExceptionDao<Estaciones, Integer> getEstacionesRuntimeDao() {
        if(estacionesRuntimeDao == null) estacionesRuntimeDao = getRuntimeExceptionDao(Estaciones.class);
        return estacionesRuntimeDao;
    }

    public Dao<Recorrido, Integer> getDaoRecorridos() throws SQLException {
        if (daoRecorrido == null) daoRecorrido = getDao(Recorrido.class);
        return daoRecorrido;
    }

    public RuntimeExceptionDao<Recorrido, Integer> getRecorridosRuntimeDao() {
        if(recorridoRuntimeDao == null) recorridoRuntimeDao = getRuntimeExceptionDao(Recorrido.class);
        return recorridoRuntimeDao;
    }



    public Dao<LineasVehiculos, Integer> getDaoModelos() throws SQLException {
        if (daoModelos == null) daoModelos = getDao(LineasVehiculos.class);
        return daoModelos;
    }

    public RuntimeExceptionDao<LineasVehiculos, Integer> getUsua() {
        if(modelosRuntimeDao == null) modelosRuntimeDao = getRuntimeExceptionDao(LineasVehiculos.class);
        return modelosRuntimeDao;
    }

    public Dao<Vehiculo, Integer> getDaoUsuarioHasModeloCarros() throws SQLException {
        if (daoUsuarioHasModeloCarros == null) daoUsuarioHasModeloCarros = getDao(Vehiculo.class);
        return daoUsuarioHasModeloCarros;
    }

    public RuntimeExceptionDao<Vehiculo, Integer> getUsuarioHasModeloCarroIntegerRuntimeDao() {
        if(usuarioHasModeloCarroRuntimeDao == null) usuarioHasModeloCarroRuntimeDao = getRuntimeExceptionDao(Vehiculo.class);
        return usuarioHasModeloCarroRuntimeDao;
    }

    public Dao<UnidadRecorrido, Integer> getDaoUnidadRecorrido() throws SQLException {
        if (daoUnidadRecorrido == null) daoUnidadRecorrido = getDao(UnidadRecorrido.class);

        return daoUnidadRecorrido;
    }

    public RuntimeExceptionDao<UnidadRecorrido, Integer> getUnidadRecorridoRuntimeDao() {
        if(unidadRecorridoRuntimeDao == null) unidadRecorridoRuntimeDao = getRuntimeExceptionDao(UnidadRecorrido.class);

        return unidadRecorridoRuntimeDao;
    }

    public Dao<Estados, Integer> getDaoEstados() throws SQLException {
        if (daoEstados == null) daoEstados= getDao(Estados.class);
        return daoEstados;
    }

    public RuntimeExceptionDao<Estados, Integer> getEstadoRuntimeDao() {
        if(estadoRuntimeDao == null) estadoRuntimeDao = getRuntimeExceptionDao(Estados.class);
        return estadoRuntimeDao;
    }

    public Dao<HistorialEstadoVehiculos, Integer> getDaoHistorialEstados() throws SQLException {
        if (daoHistorialEstados == null) daoHistorialEstados= getDao(HistorialEstadoVehiculos.class);
        return daoHistorialEstados;
    }

    public RuntimeExceptionDao<HistorialEstadoVehiculos, Integer> getHistorialEstadosRuntimeDao() {
        if(historialEstadosRuntimeDao == null) historialEstadosRuntimeDao = getRuntimeExceptionDao(HistorialEstadoVehiculos.class);
        return historialEstadosRuntimeDao;
    }

    @Override
    public void close() {
        super.close();
        daoUsuario = null;
        daoEstaciones = null;
        daoTanqueadas = null;
        daoRecorrido = null;
        daoModelos = null;
        daoUsuarioHasModeloCarros = null;
        tanqueadasRuntimeDao = null;
        estacionesRuntimeDao = null;
        recorridoRuntimeDao = null;
        usuarioRuntimeDao = null;
        modelosRuntimeDao = null;
        usuarioHasModeloCarroRuntimeDao = null;
    }
}
