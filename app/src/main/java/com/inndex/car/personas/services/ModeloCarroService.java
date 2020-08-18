package com.inndex.car.personas.services;

import android.content.Context;

import com.inndex.car.personas.database.DataBaseHelper;
import com.inndex.car.personas.model.ModeloCarros;
import com.inndex.car.personas.retrofit.MedidorApiAdapter;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModeloCarroService {

    private DataBaseHelper helper;
    private Dao<ModeloCarros, Integer> daoModeloCarros;
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
