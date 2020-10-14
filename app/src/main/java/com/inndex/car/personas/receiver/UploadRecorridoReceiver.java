package com.inndex.car.personas.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.inndex.car.personas.database.DataBaseHelper;
import com.inndex.car.personas.services.UploadRecorridosService;
import com.inndex.car.personas.utils.Constantes;
import com.j256.ormlite.android.apptools.OpenHelperManager;

public class UploadRecorridoReceiver extends BroadcastReceiver {

    private DataBaseHelper helper;
    private UploadRecorridosService uploadRecorridosService;
    private Context context;
    String placa;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        helper = OpenHelperManager.getHelper(context, DataBaseHelper.class);
        init();
        if (this.uploadRecorridosService != null)
            this.uploadRecorridosService.uploadAllNotCompletedAndNotUploaded();
        //MainActivity mainActivity = MainActivity.getInstance();
        //mainActivity.upload();
    }

    private void init() {
        if (placa == null){
            SharedPreferences myPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            placa = myPreferences.getString(Constantes.DEFAULT_PLACA, "");
        }
        if (uploadRecorridosService == null)
            uploadRecorridosService = new UploadRecorridosService(this.context, placa);
    }



}
