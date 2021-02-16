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
import com.inndex.car.personas.model.PuntoPago;
import com.inndex.car.personas.model.Tiendas;

import java.util.ArrayList;

public class TiendaAdapter  extends RecyclerView.Adapter<TiendaAdapter.ViewHolder> {
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

        final Tiendas tienda =tiendasService.get(position);
        holder.tienda.setText(tienda.getNombre());

        for (int i = 0; i < tiendasEstacion.size(); i++) {

            Long idTienda = tiendasEstacion.get(i).getId();

            if (tienda.getId().equals(idTienda)) {
                holder.checkBox.setChecked(true);
                tiendasEditadas.add(tienda);
            }
        }

        holder.checkBox.setOnClickListener(v -> {
            if (holder.checkBox.isChecked()) {
                if (!tiendasEditadas.contains(tienda)) {
                    tiendasEditadas.add(tienda);
                    Log.d("mejecuti", "poner");
                }
            } else {
                tiendasEditadas.remove(tienda);
                Log.d("mejecutir", tienda.getNombre());
                Log.d("mejecuti", String.valueOf(tiendasEditadas.size()));
            }
        });

    }

    @Override
    public int getItemCount() {
        return tiendasService.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        CheckBox checkBox;
        TextView tienda;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkboxBanco);
            tienda = itemView.findViewById(R.id.nombreBanco);
        }
    }

    public ArrayList<Tiendas> obtenerListaTiendas(){
        return tiendasEditadas;
    }
}
