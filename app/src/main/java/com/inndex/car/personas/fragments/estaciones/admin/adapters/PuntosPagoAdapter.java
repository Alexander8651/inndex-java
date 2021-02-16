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

import java.util.ArrayList;

public class PuntosPagoAdapter extends RecyclerView.Adapter<PuntosPagoAdapter.ViewHolder> {

    ArrayList<PuntoPago> puntoPagosService;
    ArrayList<PuntoPago> puntoPagosEstacion;
    ArrayList<PuntoPago> puntoPagosEditado = new ArrayList<>();

    public PuntosPagoAdapter(ArrayList<PuntoPago> puntoPagosService, ArrayList<PuntoPago> puntoPagosEstacion) {
        this.puntoPagosService = puntoPagosService;
        this.puntoPagosEstacion = puntoPagosEstacion;
    }

    @NonNull
    @Override
    public PuntosPagoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemcajero, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PuntosPagoAdapter.ViewHolder holder, int position) {

        final PuntoPago puntoPago = puntoPagosService.get(position);
        holder.puntoPago.setText(puntoPago.getNombre());

        for (int i = 0; i < puntoPagosEstacion.size(); i++) {

            Long idPuntoPago = puntoPagosEstacion.get(i).getId();

            if (puntoPago.getId().equals(idPuntoPago)) {
                holder.checkBox.setChecked(true);
                puntoPagosEditado.add(puntoPago);
            }
        }

        holder.checkBox.setOnClickListener(v -> {
            if (holder.checkBox.isChecked()) {
                if (!puntoPagosEditado.contains(puntoPago)) {
                    puntoPagosEditado.add(puntoPago);
                    Log.d("mejecuti", "poner");
                }
            } else {
                puntoPagosEditado.remove(puntoPago);
                Log.d("mejecutir", puntoPago.getNombre());
                Log.d("mejecuti", String.valueOf(puntoPagosEditado.size()));
            }
        });

    }

    @Override
    public int getItemCount() {
        return puntoPagosService.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        CheckBox checkBox;
        TextView puntoPago;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            checkBox = itemView.findViewById(R.id.checkboxBanco);
            puntoPago = itemView.findViewById(R.id.nombreBanco);
        }
    }

    public ArrayList<PuntoPago> obtenerListapuntosPago(){
        return puntoPagosEditado;
    }
}
