package com.inndex.services;

import android.content.Context;

import com.inndex.database.DataBaseHelper;
import com.inndex.model.LineasVehiculos;
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
