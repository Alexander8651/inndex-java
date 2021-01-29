package com.inndex.car.personas.services;

import android.widget.Toast;

import com.inndex.car.personas.activities.mainactivity.MainActivity;
import com.inndex.car.personas.database.DataBaseHelper;
import com.inndex.car.personas.model.Recorrido;
import com.inndex.car.personas.model.Tanqueadas;
import com.inndex.car.personas.model.UnidadRecorrido;
import com.inndex.car.personas.model.Vehiculo;
import com.inndex.car.personas.utils.Constantes;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class RecorridoService {

    private Recorrido recorrido;
    private Timer mTimer;
    private boolean modelHasTwoTanks;
    private MainActivity mainActivity;
    private long time;
    private DataBaseHelper helper;

    private Dao<UnidadRecorrido, Integer> daoUnidadRecorrido;

    private short contUpdateRecorrido = 0;
    private String placa;


    public RecorridoService() {
    }

    public RecorridoService(MainActivity mainActivity, DataBaseHelper helper, long idUsuario,
                            long idUsuarioModeloCarro, String placa) {
        this.mainActivity = mainActivity;
        this.helper = helper;
        contUpdateRecorrido = 0;
        this.placa = placa;
        try {
            daoUnidadRecorrido = helper.getDaoUnidadRecorrido();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public RecorridoService(MainActivity mainActivity, boolean modelHasTwoTanks,
                            DataBaseHelper helper, long idUsuario, long idUsuarioModeloCarro, String placa) {
        this.mainActivity = mainActivity;
        this.modelHasTwoTanks = modelHasTwoTanks;
        recorrido = new Recorrido();
        recorrido.setDisanciaRecorrida(0.0);
        recorrido.setGalonesPerdidos(0.0);
        recorrido.setFechaInicio(Constantes.SDF_FOR_BACKEND.format(new Date()));
        recorrido.setStFechaInicio(Constantes.SDF_FOR_BACKEND.format(new Date()));
        recorrido.setUploaded(false);
        recorrido.setListTanqueadas(new ArrayList<>());
        recorrido.setListUnidadRecorrido(new ArrayList<>());
        recorrido.setRecorridoCode(Constantes.generateRandomRecorridoCode());

        recorrido.setFecha(Constantes.SDF_DATE_ONLY.format(new Date()));
        recorrido.setVehiculo(new Vehiculo(idUsuarioModeloCarro));
        time = 0;
        this.helper = helper;
        try {
            daoUnidadRecorrido = helper.getDaoUnidadRecorrido();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        contUpdateRecorrido = 0;
        this.placa = placa;
    }

    public void initTimmers() {
        mTimer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                updateRecorrido();
            }
        };
        mTimer.schedule(task, 1000, Constantes.DELAY_RECORRIDO);
    }

    private void updateRecorrido() {

        UnidadRecorrido unidadRecorrido = new UnidadRecorrido();
        //Location location = mainActivity.getInndexLocationService().getMyLocation();
        /*if (location != null) {
            unidadRecorrido.setLatitud(mainActivity.getInndexLocationService().getMyLocation().getLatitude());
            unidadRecorrido.setLongitud(mainActivity.getInndexLocationService().getMyLocation().getLongitude());
            unidadRecorrido.setAltitud(mainActivity.getInndexLocationService().getMyLocation().getAltitude());
        }*/
        unidadRecorrido.setHora(Constantes.SDF_HOUR_RECORRIDO.format(new Date()));
        unidadRecorrido.setFecha(Constantes.SDF_DATE_ONLY.format(new Date()));

        time += Constantes.DELAY_RECORRIDO;

        try {
            this.daoUnidadRecorrido.create(unidadRecorrido);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void pararRecorrido() {

        if (mTimer != null) {
            mTimer.cancel();
            mTimer.purge();
        }
        Toast.makeText(mainActivity, "RECORRIDO COMPLETED", Toast.LENGTH_SHORT).show();
    }


    public Recorrido getCurrentUnCompletedRecorrido(String fecha) {
        Recorrido recorrido = null;
        try {
            Dao<Recorrido, Integer> dao = helper.getDaoRecorridos();
            List<Recorrido> recorridoList = dao.queryForEq("fecha", fecha);
            if (recorridoList != null && recorridoList.size() > 0) {
                recorrido = recorridoList.get(0);
                //if(recorrido.isCompleted()){
//                    recorrido = null;}
            }
        } catch (SQLException | IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        return recorrido;
    }

    public Recorrido getRecorrido() {
        return recorrido;
    }



    public boolean isModelHasTwoTanks() {
        return modelHasTwoTanks;
    }

    public void setModelHasTwoTanks(boolean modelHasTwoTanks) {
        this.modelHasTwoTanks = modelHasTwoTanks;
    }

    public void pushTanqueada(Tanqueadas tanqueada) {
        recorrido.getListTanqueadas().add(tanqueada);
    }

    /*public boolean isInUploadingProccess() {
        return inUploadingProccess;
    }*/
}
