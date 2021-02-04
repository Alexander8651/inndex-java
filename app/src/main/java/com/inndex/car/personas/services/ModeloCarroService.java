package com.inndex.car.personas.services;

import android.content.Context;

import com.inndex.car.personas.database.DataBaseHelper;
import com.inndex.car.personas.model.LineasVehiculos;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

public class ModeloCarroService {

    private DataBaseHelper helper;
    private Dao<LineasVehiculos, Integer> daoModeloCarros;
    private Context context;

    public ModeloCarroService() {

    }

    public ModeloCarroService(DataBaseHelper helper) {
        this.helper = helper;
        try {
            daoModeloCarros = helper.getDaoModelos();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
