package com.inndex.car.personas.fragments.estaciones.admin.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.inndex.car.personas.R;
import com.inndex.car.personas.model.Bancos;

import java.util.ArrayList;

public class bancosEdsotrosServiciosAdapter extends RecyclerView.Adapter<bancosEdsotrosServiciosAdapter.Viewholder> {

    ArrayList<Bancos> bancosServicio;
    ArrayList<Bancos> bancosEstacion;
    ArrayList<Bancos> editada = new ArrayList<>();


    public bancosEdsotrosServiciosAdapter(ArrayList<Bancos> bancosServicio, ArrayList<Bancos> bancosEstacion) {
        this.bancosServicio = bancosServicio;
        this.bancosEstacion = bancosEstacion;
    }

    @NonNull
    @Override
    public bancosEdsotrosServiciosAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemcajero, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull bancosEdsotrosServiciosAdapter.Viewholder holder, int position) {

        final Bancos bancos = bancosServicio.get(position);
        holder.nombreBanco.setText(bancos.getNombre());

        for (int i = 0; i < bancosEstacion.size(); i++) {

            Long idBanco = bancosEstacion.get(i).getId();

            if (bancos.getId().equals(idBanco)) {
                holder.checkBox.setChecked(true);
                editada.add(bancos);
            }
        }

        holder.checkBox.setOnClickListener(v -> {
            if (holder.checkBox.isChecked()) {
                if (!editada.contains(bancos)) {
                    editada.add(bancos);
                    Log.d("mejecuti", "poner");
                }
            } else {
                editada.remove(bancos);
                Log.d("mejecutir", bancos.getNombre());
                Log.d("mejecuti", String.valueOf(editada.size()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return bancosServicio.size();
    }

    public ArrayList<Bancos> obtenerListaBancos() {
        return editada;
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        CheckBox checkBox;
        TextView nombreBanco;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            checkBox = itemView.findViewById(R.id.checkboxBanco);
            nombreBanco = itemView.findViewById(R.id.nombreBanco);
        }
    }
}
