package com.inndex.adapter;


import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;

import com.inndex.R;
import com.inndex.model.MarcaVehiculos;

import java.util.Calendar;
import java.util.List;

public class AdapterSpinner extends ArrayAdapter<MarcaVehiculos> {
    private Context context;

    List<MarcaVehiculos> datos = null;

    public AdapterSpinner(Context context, List<MarcaVehiculos> datos) {
        //se debe indicar el layout para el item que seleccionado (el que se muestra sobre el botón del botón)
        super(context, R.layout.itemspinnermarca, datos);
        this.context = context;
        this.datos = datos;
    }

    //este método establece el elemento seleccionado sobre el botón del spinner
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        if (convertView == null) {
            convertView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.itemspinnermarca, null);
        }

        TextView item = ((TextView) convertView.findViewById(R.id.itemSpinnerMarca));

        // cuando se seleciona un item en el spinner se cambia el valor selecionado

            item.setTextColor(Color.BLACK);
            item.setText("Selecciona una marca");

        // cuando se seleciona un item en el spinner se cambia el valor selecionado
        if (position >= 0) {
            MarcaVehiculos conclusion = datos.get(position);
            item.setText(conclusion.getNombre());
            item.setTextColor(Color.BLACK);
        }

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        if (row == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.itemspinnermarca, parent, false);
            MarcaVehiculos marcaVehiculos = datos.get(position);

            ((TextView) row.findViewById(R.id.itemSpinnerMarca)).setText(marcaVehiculos.getNombre());


            if (row.getTag() == null) {
                //AdapterSpinner UnidadesHolder = new AdapterSpinner();
                // UnidadesHolder.setTextView((TextView) row.findViewById(R.id.itemSpinners));
                // row.setTag(UnidadesHolder);
            }

            //rellenamos el layout con los datos de la fila que se está procesando
            MarcaVehiculos categorias = datos.get(position);

        }
        return row;

    }
}