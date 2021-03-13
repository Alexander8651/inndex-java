package com.inndex.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.inndex.R;
import com.inndex.model.Bancos;
import com.inndex.model.Combustibles;
import com.inndex.model.Estaciones;
import com.inndex.model.Estados;
import com.inndex.model.LineasVehiculos;
import com.inndex.model.MarcaEstacion;
import com.inndex.model.MetodoPago;
import com.inndex.model.PuntoPago;
import com.inndex.model.Recorrido;
import com.inndex.model.Soat;
import com.inndex.model.Tanqueadas;
import com.inndex.model.Tiendas;
import com.inndex.model.UnidadRecorrido;
import com.inndex.model.Usuario;
import com.inndex.model.Vehiculo;
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

    private Dao<Estados, Integer> daoEstados = null;
    private RuntimeExceptionDao<Estados, Integer> estadoRuntimeDao = null;

    private Dao<MarcaEstacion, Long> daoMarcaEstacion = null;
    private RuntimeExceptionDao<MarcaEstacion, Long> MarcaEstacionRuntimeDao = null;

    private Dao<Combustibles, Long> daoCombustibles = null;
    private RuntimeExceptionDao<Combustibles, Long> combustiblesRuntimeDao = null;

    private Dao<Tiendas, Long> daoTiendas = null;
    private RuntimeExceptionDao<Tiendas, Long> tiendasRuntimeDao = null;

    private Dao<Bancos, Long> daoBancos = null;
    private RuntimeExceptionDao<Bancos, Long> bancosRuntimeDao = null;

    private Dao<Soat, Long> daoSoat = null;
    private RuntimeExceptionDao<Soat, Long> soatRuntimeDao = null;

    private Dao<PuntoPago, Long> daoPuntoPago = null;
    private RuntimeExceptionDao<PuntoPago, Long> puntoPagoRuntimeDao = null;

    private Dao<MetodoPago, Long> daoMetodoPago = null;
    private RuntimeExceptionDao<MetodoPago, Long> metodoPagoRuntimeDao = null;

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION, R.raw.medidor_config);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {

        try {
            TableUtils.createTable(connectionSource, MetodoPago.class);
            TableUtils.createTable(connectionSource, PuntoPago.class);
            TableUtils.createTable(connectionSource, Tiendas.class);
            TableUtils.createTable(connectionSource, Bancos.class);
            TableUtils.createTable(connectionSource, Soat.class);
            TableUtils.createTable(connectionSource, Combustibles.class);
            TableUtils.createTable(connectionSource, Estaciones.class);
            TableUtils.createTable(connectionSource, Estados.class);
            TableUtils.createTable(connectionSource, LineasVehiculos.class);
            TableUtils.createTable(connectionSource, MarcaEstacion.class);
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
            TableUtils.dropTable(connection, MetodoPago.class, true);
            TableUtils.dropTable(connection, PuntoPago.class, true);
            TableUtils.dropTable(connection, Tiendas.class, true);
            TableUtils.dropTable(connection, Bancos.class, true);
            TableUtils.dropTable(connection, Soat.class, true);
            TableUtils.dropTable(connection, Combustibles.class, true);
            TableUtils.dropTable(connection, Estados.class, true);
            TableUtils.dropTable(connection, Usuario.class, true);
            TableUtils.dropTable(connection, Estaciones.class, true);
            TableUtils.dropTable(connection, Tanqueadas.class, true);
            TableUtils.dropTable(connection, Recorrido.class, true);
            TableUtils.dropTable(connection, LineasVehiculos.class, true);
            TableUtils.dropTable(connection, MarcaEstacion.class, true);
            TableUtils.dropTable(connection, UnidadRecorrido.class, true);
            TableUtils.dropTable(connection, Vehiculo.class, true);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    public Dao<Usuario, Integer> getDaoUsuario() throws SQLException {
        if (daoUsuario == null) daoUsuario = getDao(Usuario.class);
        return daoUsuario;
    }

    public RuntimeExceptionDao<Usuario, Integer> getUsuarioRuntimeDao() {
        if (usuarioRuntimeDao == null) usuarioRuntimeDao = getRuntimeExceptionDao(Usuario.class);
        return usuarioRuntimeDao;
    }

    public Dao<Estaciones, Integer> getDaoEstaciones() throws SQLException {
        if (daoEstaciones == null) daoEstaciones = getDao(Estaciones.class);
        return daoEstaciones;
    }

    public RuntimeExceptionDao<Estaciones, Integer> getEstacionesRuntimeDao() {
        if (estacionesRuntimeDao == null)
            estacionesRuntimeDao = getRuntimeExceptionDao(Estaciones.class);
        return estacionesRuntimeDao;
    }

    public Dao<Recorrido, Integer> getDaoRecorridos() throws SQLException {
        if (daoRecorrido == null) daoRecorrido = getDao(Recorrido.class);
        return daoRecorrido;
    }

    public RuntimeExceptionDao<Recorrido, Integer> getRecorridosRuntimeDao() {
        if (recorridoRuntimeDao == null)
            recorridoRuntimeDao = getRuntimeExceptionDao(Recorrido.class);
        return recorridoRuntimeDao;
    }


    public Dao<LineasVehiculos, Integer> getDaoModelos() throws SQLException {
        if (daoModelos == null) daoModelos = getDao(LineasVehiculos.class);
        return daoModelos;
    }

    public RuntimeExceptionDao<LineasVehiculos, Integer> getUsua() {
        if (modelosRuntimeDao == null)
            modelosRuntimeDao = getRuntimeExceptionDao(LineasVehiculos.class);
        return modelosRuntimeDao;
    }

    public Dao<Vehiculo, Integer> getDaoUsuarioHasModeloCarros() throws SQLException {
        if (daoUsuarioHasModeloCarros == null) daoUsuarioHasModeloCarros = getDao(Vehiculo.class);
        return daoUsuarioHasModeloCarros;
    }

    public RuntimeExceptionDao<Vehiculo, Integer> getUsuarioHasModeloCarroIntegerRuntimeDao() {
        if (usuarioHasModeloCarroRuntimeDao == null)
            usuarioHasModeloCarroRuntimeDao = getRuntimeExceptionDao(Vehiculo.class);
        return usuarioHasModeloCarroRuntimeDao;
    }

    public Dao<UnidadRecorrido, Integer> getDaoUnidadRecorrido() throws SQLException {
        if (daoUnidadRecorrido == null) daoUnidadRecorrido = getDao(UnidadRecorrido.class);

        return daoUnidadRecorrido;
    }

    public RuntimeExceptionDao<UnidadRecorrido, Integer> getUnidadRecorridoRuntimeDao() {
        if (unidadRecorridoRuntimeDao == null)
            unidadRecorridoRuntimeDao = getRuntimeExceptionDao(UnidadRecorrido.class);

        return unidadRecorridoRuntimeDao;
    }

    public Dao<Estados, Integer> getDaoEstados() throws SQLException {
        if (daoEstados == null) daoEstados = getDao(Estados.class);
        return daoEstados;
    }

    public RuntimeExceptionDao<Estados, Integer> getEstadoRuntimeDao() {
        if (estadoRuntimeDao == null) estadoRuntimeDao = getRuntimeExceptionDao(Estados.class);
        return estadoRuntimeDao;
    }

    public Dao<MarcaEstacion, Long> getDaoMarcaEstacion() throws SQLException {
        if (daoMarcaEstacion == null) daoMarcaEstacion = getDao(MarcaEstacion.class);
        return daoMarcaEstacion;
    }

    public RuntimeExceptionDao<MarcaEstacion, Long> getMarcaEstacionRuntimeDao() {
        if (MarcaEstacionRuntimeDao == null)
            MarcaEstacionRuntimeDao = getRuntimeExceptionDao(MarcaEstacion.class);
        return MarcaEstacionRuntimeDao;
    }

    public Dao<Combustibles, Long> getDaoCombustibles() throws SQLException {
        if (daoCombustibles == null) daoCombustibles = getDao(Combustibles.class);
        return daoCombustibles;
    }

    public RuntimeExceptionDao<Combustibles, Long> getCombustiblesRuntimeDao() {
        if (combustiblesRuntimeDao == null)
            combustiblesRuntimeDao = getRuntimeExceptionDao(Combustibles.class);
        return combustiblesRuntimeDao;
    }

    public Dao<Tiendas, Long> getDaoTiendas() throws SQLException {
        if (daoTiendas == null)
            daoTiendas = getDao(Tiendas.class);
        return daoTiendas;
    }

    public RuntimeExceptionDao<Tiendas, Long> getTiendasRuntimeDao() {
        if (tiendasRuntimeDao == null)
            tiendasRuntimeDao = getRuntimeExceptionDao(Tiendas.class);
        return tiendasRuntimeDao;
    }

    public Dao<Bancos, Long> getDaoBancos() throws SQLException {
        if (daoBancos == null)
            daoBancos = getDao(Bancos.class);
        return daoBancos;
    }

    public RuntimeExceptionDao<Bancos, Long> getBancosRuntimeDao() {
        if (bancosRuntimeDao == null)
            bancosRuntimeDao = getRuntimeExceptionDao(Bancos.class);
        return bancosRuntimeDao;
    }

    public Dao<Soat, Long> getDaoSoat() throws SQLException {
        if (daoSoat == null)
            daoSoat = getDao(Soat.class);
        return daoSoat;
    }

    public RuntimeExceptionDao<Soat, Long> getSoatRuntimeDao() {
        if (soatRuntimeDao == null)
            soatRuntimeDao = getRuntimeExceptionDao(Soat.class);
        return soatRuntimeDao;
    }

    public Dao<PuntoPago, Long> getDaoPuntoPago() throws SQLException {
        if (daoPuntoPago == null)
            daoPuntoPago = getDao(PuntoPago.class);
        return daoPuntoPago;
    }

    public RuntimeExceptionDao<PuntoPago, Long> getPuntoPagoRuntimeDao() {
        if (puntoPagoRuntimeDao == null)
            puntoPagoRuntimeDao = getRuntimeExceptionDao(PuntoPago.class);
        return puntoPagoRuntimeDao;
    }

    public Dao<MetodoPago, Long> getDaoMetodoPago() throws SQLException {
        if (daoMetodoPago == null)
            daoMetodoPago = getDao(MetodoPago.class);
        return daoMetodoPago;
    }

    public RuntimeExceptionDao<MetodoPago, Long> getMetodoPagoRuntimeDao() {
        if (metodoPagoRuntimeDao == null)
            metodoPagoRuntimeDao = getRuntimeExceptionDao(MetodoPago.class);
        return metodoPagoRuntimeDao;
    }

    @Override
    public void close() {
        super.close();
        daoMetodoPago = null;
        daoSoat = null;
        daoBancos = null;
        daoTiendas = null;
        daoCombustibles = null;
        daoMarcaEstacion = null;
        daoUsuario = null;
        daoEstaciones = null;
        daoRecorrido = null;
        daoModelos = null;
        daoUsuarioHasModeloCarros = null;
        estacionesRuntimeDao = null;
        recorridoRuntimeDao = null;
        usuarioRuntimeDao = null;
        modelosRuntimeDao = null;
        usuarioHasModeloCarroRuntimeDao = null;
    }
}
