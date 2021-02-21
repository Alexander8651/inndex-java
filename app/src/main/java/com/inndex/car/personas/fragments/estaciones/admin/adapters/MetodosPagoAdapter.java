package com.inndex.car.personas.fragments.estaciones.admin.adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.inndex.car.personas.R;
import com.inndex.car.personas.model.MetodoPago;

import java.util.ArrayList;
import java.util.List;

public class MetodosPagoAdapter extends RecyclerView.Adapter<MetodosPagoAdapter.ViewHolder> {

    List<MetodoPago> metodoPagoService;
    List<MetodoPago> metodoPagoEstacion;
    List<MetodoPago> metodoPagoEditado = new ArrayList<>();

    public MetodosPagoAdapter(List<MetodoPago> metodoPagoService, List<MetodoPago> metodoPagoEstacion) {
        this.metodoPagoService = metodoPagoService;
        this.metodoPagoEstacion = metodoPagoEstacion;
    }

    @NonNull
    @Override
    public MetodosPagoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemcajero, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MetodosPagoAdapter.ViewHolder holder, int position) {

        final MetodoPago metodoPago = metodoPagoService.get(position);
        holder.tvMetodoPago.setText(metodoPago.getNombre());

        for (int i = 0; i < metodoPagoEstacion.size(); i++) {

            Long id = metodoPagoEstacion.get(i).getId();

            if (metodoPago.getId().equals(id)) {
                holder.checkBox.setChecked(true);
                metodoPagoEditado.add(metodoPago);
            }
        }

        holder.checkBox.setOnClickListener(v -> {
            if (holder.checkBox.isChecked()) {
                if (!metodoPagoEditado.contains(metodoPago)) {
                    metodoPagoEditado.add(metodoPago);
                }
            } else {
                metodoPagoEditado.remove(metodoPago);
            }
        });
    }

    @Override
    public int getItemCount() {
        return metodoPagoService.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        CheckBox checkBox;
        TextView tvMetodoPago;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            checkBox = itemView.findViewById(R.id.checkboxBanco);
            tvMetodoPago = itemView.findViewById(R.id.nombreBanco);
        }
    }

    public List<MetodoPago> obtenerListapuntosPago(){
        return metodoPagoEditado;
    }
}
