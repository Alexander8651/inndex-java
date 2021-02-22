package com.inndex.car.personas.fragments.estaciones.admin.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.inndex.car.personas.R;
import com.inndex.car.personas.model.Soat;

import java.util.ArrayList;

public class SegurosAdapter extends RecyclerView.Adapter<SegurosAdapter.ViewHolder> {

    ArrayList<Soat> soatService;
    Soat soatEstacion;
    ArrayList<Soat> soatEditado = new ArrayList<>();


    public SegurosAdapter(ArrayList<Soat> soatService, Soat soatEstacion) {
        this.soatService = soatService;
        this.soatEstacion = soatEstacion;
    }

    @NonNull
    @Override
    public SegurosAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemcajero, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SegurosAdapter.ViewHolder holder, int position) {

        final Soat soat = soatService.get(position);
        holder.soat.setText(soat.getNombre());



        if (soatEstacion == null)
            return;

        Long idTienda = soatEstacion.getId();

        if (soat.getId().equals(idTienda)) {
            holder.checkBox.setChecked(true);
            soatEditado.add(soat);
        }


        holder.checkBox.setOnClickListener(v -> {
            if (holder.checkBox.isChecked()) {
                soatEstacion.setNombre(soat.getNombre());
                soatEstacion.setId(soat.getId());
            }
        });

        holder.radioButton.setOnClickListener( v ->{
            if (holder.checkBox.isChecked()) {
                if (holder.checkBox.isChecked()) {
                    soatEstacion.setNombre(soat.getNombre());
                    soatEstacion.setId(soat.getId());
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return soatService.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CheckBox checkBox;
        TextView soat;
        RadioButton radioButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            checkBox = itemView.findViewById(R.id.checkboxBanco);
            soat = itemView.findViewById(R.id.nombreBanco);
            radioButton = itemView.findViewById(R.id.radiobuttonBanco);

            checkBox.setVisibility(View.GONE);
            radioButton.setVisibility(View.VISIBLE);

        }
    }

    public Soat obtenerSoat() {
        return soatEstacion;
    }
}
