package com.inndex.threads;

import com.inndex.database.DataBaseHelper;

public class DownloadUsuarioHasModeloCarro extends Thread {

    private int idUsuario;
    private DataBaseHelper helper;

    public DownloadUsuarioHasModeloCarro(int idUsuario, DataBaseHelper helper) {

        this.idUsuario = idUsuario;
        this.helper = helper;

    }

    @Override
    public void run() {
/*
        Call<List<Vehiculo>> callGetAllUsuarioHasModelos  = MedidorApiAdapter.getApiService().getUsuarioHasModeloCarros(String.valueOf(idUsuario));
        callGetAllUsuarioHasModelos.enqueue(new Callback<List<Vehiculo>>() {
            @Override
            public void onResponse(Call<List<Vehiculo>> call, Response<List<Vehiculo>> response) {

                try {
                    Dao<Vehiculo, Integer> dao = helper.getDaoUsuarioHasModeloCarros();
                    if(response.body() != null && response.body().size() > 0) {
                        dao.create(response.body());
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<List<Vehiculo>> call, Throwable t) {

            }
        });*/
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public DataBaseHelper getHelper() {
        return helper;
    }

    public void setHelper(DataBaseHelper helper) {
        this.helper = helper;
    }
}
