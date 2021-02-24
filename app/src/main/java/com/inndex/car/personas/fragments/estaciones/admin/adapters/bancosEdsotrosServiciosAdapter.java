package com.inndex.car.personas.fragments.estaciones.admin.adapters;

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
import java.util.List;

public class bancosEdsotrosServiciosAdapter extends RecyclerView.Adapter<bancosEdsotrosServiciosAdapter.Viewholder> {

    List<Bancos> bancosServicio;
    ArrayList<Bancos> bancosEstacion;
    ArrayList<Bancos> editada = new ArrayList<>();


    public bancosEdsotrosServiciosAdapter(List<Bancos> bancosServicio, ArrayList<Bancos> bancosEstacion) {
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

        holder.checkBox.setOnCheckedChangeListener((v, b) -> {
            if (b) {
                if (!editada.contains(bancos)) {
                    editada.add(bancos);
                }
            } else {
                editada.remove(bancos);
            }
        });
    }

    @Override
    public void onViewRecycled(@NonNull Viewholder holder) {

        if (holder.checkBox != null) {
            holder.checkBox.setOnClickListener(null);
        }
        super.onViewRecycled(holder);
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
