package com.inndex.fragments.estaciones.admin.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.inndex.R;
import com.inndex.model.Bancos;

import java.util.ArrayList;
import java.util.List;

public class CajerosEdsOtroServiciosAdapter extends BaseAdapter {

    ArrayList<Bancos> bancosServicio;
    ArrayList<Bancos> bancosEstacion;
    ArrayList<Bancos> bancosEditados = new ArrayList<>();
    Context context;

    public CajerosEdsOtroServiciosAdapter(ArrayList<Bancos> bancosList, ArrayList<Bancos> bancosEstacion, Context context) {
        this.bancosServicio = bancosList;
        this.bancosEstacion = bancosEstacion;
        this.context = context;
    }

    @Override
    public int getCount() {
        return bancosServicio.size();
    }

    @Override
    public Object getItem(int i) {
        return bancosServicio.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        CheckBox checkBox;
        TextView tvMetodoPago;

        convertView = LayoutInflater.from(context).inflate(R.layout.itemcajero, null);
        final Bancos banco = (Bancos) getItem(position);


        checkBox = convertView.findViewById(R.id.checkboxBanco);
        tvMetodoPago = convertView.findViewById(R.id.nombreBanco);


        tvMetodoPago.setText(banco.getNombre());
        Log.d("estaooaoso", String.valueOf(bancosEstacion.size()));

        if (bancosEstacion.size() >0){

            for (int i = 0; i < bancosEstacion.size(); i++) {

                Long idBanco = bancosEstacion.get(i).getId();

                if (banco.getId().equals(idBanco)) {
                    checkBox.setChecked(true);
                    bancosEditados.add(banco);
                }
            }
        }

        checkBox.setOnClickListener(view -> {
            if (checkBox.isChecked()) {
                if (!bancosEditados.contains(banco.getNombre())) {
                    bancosEditados.add(banco);
                }
            } else {
                bancosEditados.remove(banco);
            }
        });

        return convertView;
    }

    public List<Bancos> obtenerListaBancos() {
        return bancosEditados;
    }
}
