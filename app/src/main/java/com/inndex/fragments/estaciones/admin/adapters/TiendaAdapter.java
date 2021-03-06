package com.inndex.fragments.estaciones.admin.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.inndex.R;
import com.inndex.model.Bancos;
import com.inndex.model.PuntoPago;
import com.inndex.model.Tiendas;

import java.util.ArrayList;

public class TiendaAdapter extends RecyclerView.Adapter<TiendaAdapter.ViewHolder> {
    ArrayList<Tiendas> tiendasService;
    ArrayList<Tiendas> tiendasEstacion;
    ArrayList<Tiendas> tiendasEditadas = new ArrayList<>();

    public TiendaAdapter(ArrayList<Tiendas> tiendasService, ArrayList<Tiendas> tiendasEstacion) {
        this.tiendasService = tiendasService;
        this.tiendasEstacion = tiendasEstacion;
    }

    @NonNull
    @Override
    public TiendaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemcajero, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TiendaAdapter.ViewHolder holder, int position) {

        holder.setIsRecyclable(false);
        final Tiendas tienda = tiendasService.get(position);
        holder.tienda.setText(tienda.getNombre());

        if (tiendasEstacion == null)
            tiendasEstacion = new ArrayList<>();

        for (int i = 0; i < tiendasEstacion.size(); i++) {
            Long idTienda = tiendasEstacion.get(i).getId();
            if (tienda.getId().equals(idTienda)) {
                holder.checkBox.setChecked(true);
                tiendasEditadas.add(tienda);
            }
        }
        holder.checkBox.setOnCheckedChangeListener((v, b) -> {

            Tiendas tiendaSelected = tiendasService.get(position);
            boolean exists = false;
            int positionInBancosEstacion = 0;
            for (int i = 0; i < tiendasEstacion.size(); i++) {
                if (tiendasEstacion.get(i).getId().equals(tiendaSelected.getId())) {
                    exists = true;
                    positionInBancosEstacion = i;
                    break;
                }
            }

            if (!exists && b) {
                tiendasEstacion.add(tiendaSelected);
            } else {
                tiendasEstacion.remove(positionInBancosEstacion);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tiendasService.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CheckBox checkBox;
        TextView tienda;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkboxBanco);
            tienda = itemView.findViewById(R.id.nombreBanco);
        }
    }

    public ArrayList<Tiendas> obtenerListaTiendas() {
        return tiendasEstacion;
    }
}
