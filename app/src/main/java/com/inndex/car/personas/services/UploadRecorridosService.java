package com.inndex.car.personas.services;

import android.content.Context;
import android.widget.Toast;

import com.inndex.car.personas.database.DataBaseHelper;
import com.inndex.car.personas.model.UnidadRecorrido;
import com.inndex.car.personas.retrofit.MedidorApiAdapter;
import com.inndex.car.personas.utils.Constantes;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;

import java.sql.SQLException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.inndex.car.personas.utils.Constantes.LIMIT_UNIT_RECORRIDO;

public class UploadRecorridosService {

    private Dao<UnidadRecorrido, Integer> daoUnidadRecorrido;
    private DataBaseHelper helper;
    private Context context;
    private boolean inUploadingProccess = false;
    private int totalvaluesForUpload = 0;
    private String placa;
    //private CustomProgressDialog mCustomProgressDialog;

    public UploadRecorridosService(Context context, String placa) {
        this.context = context;
        helper = OpenHelperManager.getHelper(context, DataBaseHelper.class);
        this.placa = placa;
        try {
            if(daoUnidadRecorrido == null)
                daoUnidadRecorrido = helper.getDaoUnidadRecorrido();
            //     if(helper == null)
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void uploadAllNotCompletedAndNotUploaded() {
        try {
            QueryBuilder<UnidadRecorrido, Integer> qbUnidadRecorrido;
            Where<UnidadRecorrido, Integer> whereUR;
            PreparedQuery<UnidadRecorrido> pqUR;
            qbUnidadRecorrido =
                    daoUnidadRecorrido.queryBuilder();
            qbUnidadRecorrido.orderBy("id", true);
            qbUnidadRecorrido.limit(LIMIT_UNIT_RECORRIDO);
            whereUR = qbUnidadRecorrido.where();
            whereUR.gt("id", 0);

            pqUR = qbUnidadRecorrido.prepare();
            QueryBuilder<UnidadRecorrido, Integer> qbUnidadRecorridoCount;

            qbUnidadRecorridoCount =
                    daoUnidadRecorrido.queryBuilder();
            qbUnidadRecorridoCount.setCountOf(true);
            PreparedQuery<UnidadRecorrido> pqURCount = qbUnidadRecorridoCount.prepare();
            Where<UnidadRecorrido, Integer> whereURCount = qbUnidadRecorrido.where();
            whereURCount.gt("id", 0);
            List<UnidadRecorrido> lUnidadRecorrido = daoUnidadRecorrido.query(pqUR);
            totalvaluesForUpload = (int) daoUnidadRecorrido.countOf(pqURCount);
            //lUnidadRecorrido.sort(Comparator.comparing(UnidadRecorrido::getId));

            if(lUnidadRecorrido != null && lUnidadRecorrido.size() > 0)
                this.uploadSingleTrip(lUnidadRecorrido);

        } catch (SQLException e) {
            Toast.makeText(context, "Ocurrió un error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void uploadSingleTrip(List<UnidadRecorrido> listUnidadRecorrido) {
        Call<UnidadRecorrido> uploadAll = MedidorApiAdapter.getApiService().postRecorridosBulk(Constantes.CONTENT_TYPE_JSON,
                listUnidadRecorrido, placa);
        //Toast.makeText(context, "Subiendo PLACA: " + listUnidadRecorrido.size(), Toast.LENGTH_LONG).show();
        Toast.makeText(context, "Subiendo PLACA: " + this.placa, Toast.LENGTH_LONG).show();

        //mCustomProgressDialog.show("");
        uploadAll.enqueue(new Callback<UnidadRecorrido>() {
            @Override
            public void onResponse(Call<UnidadRecorrido> call, Response<UnidadRecorrido> response) {
                //Log.e("RESPONSE", String.valueOf(response.code()));
                if (response.isSuccessful() && response.body() != null) {
                    deleteUploadedUnidadRecorridos(listUnidadRecorrido);
                    if (totalvaluesForUpload > LIMIT_UNIT_RECORRIDO) {
                        uploadAllNotCompletedAndNotUploaded();
                        inUploadingProccess = true;
                    } else
                        inUploadingProccess = false;
                } else {
                    Toast.makeText(context, "ERROR, NO SE PUDO SUBIR TODA LA INFORMACIÓN " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<UnidadRecorrido> call, Throwable t) {
                //mCustomProgressDialog.dismiss("");
                Toast.makeText(context, "ERROR, NO SE PUDO SUBIR TODA LA INFORMACIÓN " + t.getMessage(), Toast.LENGTH_LONG).show();
                inUploadingProccess = false;
            }
        });
    }

      private void deleteUploadedUnidadRecorridos(List<UnidadRecorrido> listUnidadRecorrido) {

        try {
            if (listUnidadRecorrido != null && listUnidadRecorrido.size() > 0) {
                for (int i = 0; i < listUnidadRecorrido.size(); i += 400) {
                    //daoUnidadRecorrido.delete(listUnidadRecorrido.get(i));
                    if ((listUnidadRecorrido.size() - i) < 400) {
                        if (listUnidadRecorrido.size() == 1) {
                            daoUnidadRecorrido.delete(listUnidadRecorrido.get(0));
                        } else {
                            daoUnidadRecorrido.delete(listUnidadRecorrido.subList(i, listUnidadRecorrido.size() - 1));
                        }
                    } else
                        daoUnidadRecorrido.delete(listUnidadRecorrido.subList(i, i + 399));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }
}
