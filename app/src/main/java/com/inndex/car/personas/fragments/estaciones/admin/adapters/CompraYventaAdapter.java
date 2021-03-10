package com.inndex.car.personas.fragments.estaciones.admin.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.inndex.car.personas.R;
import com.inndex.car.personas.model.CompraYventa;

import java.util.ArrayList;
import java.util.List;


public class CompraYventaAdapter extends RecyclerView.Adapter<CompraYventaAdapter.ViewHolder> {
    List<CompraYventa> compraYventaService;
    List<CompraYventa> compraYventaEstacion;
    List<CompraYventa> compraYventaEditadas = new ArrayList<>();

    public CompraYventaAdapter(List<CompraYventa> compraYventaService, List<CompraYventa> compraYventaEstacion) {
        this.compraYventaService = compraYventaService;
        this.compraYventaEstacion = compraYventaEstacion;
    }

    @NonNull
    @Override
    public CompraYventaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemcajero, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CompraYventaAdapter.ViewHolder holder, int position) {

        holder.setIsRecyclable(false);
        final CompraYventa tienda = compraYventaService.get(position);
        holder.tienda.setText(tienda.getNombre());

        if (compraYventaEstacion == null)
            compraYventaEstacion = new ArrayList<>();

        for (int i = 0; i < compraYventaEstacion.size(); i++) {
            Long idTienda = compraYventaEstacion.get(i).getId();
            if (tienda.getId().equals(idTienda)) {
                holder.checkBox.setChecked(true);
                compraYventaEditadas.add(tienda);
            }
        }
        holder.checkBox.setOnCheckedChangeListener((v, b) -> {

            CompraYventa tiendaSelected = compraYventaService.get(position);
            boolean exists = false;
            int positionInBancosEstacion = 0;
            for (int i = 0; i < compraYventaEstacion.size(); i++) {
                if (compraYventaEstacion.get(i).getId().equals(tiendaSelected.getId())) {
                    exists = true;
                    positionInBancosEstacion = i;
                    break;
                }
            }
            if (!exists && b) {
                compraYventaEstacion.add(tiendaSelected);
            } else {
                compraYventaEstacion.remove(positionInBancosEstacion);
            }
        });
    }

    @Override
    public int getItemCount() {
        return compraYventaService.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        CheckBox checkBox;
        TextView tienda;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkboxBanco);
            tienda = itemView.findViewById(R.id.nombreBanco);
        }
    }

    public List<CompraYventa> obtenerListaCompraYventas() {
        return compraYventaEstacion;
    }
}
