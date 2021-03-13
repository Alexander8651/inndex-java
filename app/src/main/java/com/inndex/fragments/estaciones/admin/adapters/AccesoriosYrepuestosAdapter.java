package com.inndex.fragments.estaciones.admin.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.inndex.R;
import com.inndex.model.AccesoriosYrepuestos;

import java.util.ArrayList;
import java.util.List;

public class AccesoriosYrepuestosAdapter extends RecyclerView.Adapter<AccesoriosYrepuestosAdapter.ViewHolder> {
    List<AccesoriosYrepuestos> accesoriosService;
    List<AccesoriosYrepuestos> accesoriosEstacion;
    List<AccesoriosYrepuestos> accesoriosEditadas = new ArrayList<>();

    public AccesoriosYrepuestosAdapter(List<AccesoriosYrepuestos> accesoriosService, List<AccesoriosYrepuestos> accesoriosEstacion) {
        this.accesoriosService = accesoriosService;
        this.accesoriosEstacion = accesoriosEstacion;
    }

    @NonNull
    @Override
    public AccesoriosYrepuestosAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemcajero, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AccesoriosYrepuestosAdapter.ViewHolder holder, int position) {

        holder.setIsRecyclable(false);
        final AccesoriosYrepuestos accesoriosYrepuestos = accesoriosService.get(position);
        holder.textView.setText(accesoriosYrepuestos.getNombre());

        if (accesoriosEstacion == null)
            accesoriosEstacion = new ArrayList<>();

        for (int i = 0; i < accesoriosEstacion.size(); i++) {
            Long idTienda = accesoriosEstacion.get(i).getId();
            if (accesoriosYrepuestos.getId().equals(idTienda)) {
                holder.checkBox.setChecked(true);
                accesoriosEditadas.add(accesoriosYrepuestos);
            }
        }
        holder.checkBox.setOnCheckedChangeListener((v, b) -> {

            AccesoriosYrepuestos accesorioselected = accesoriosService.get(position);
            boolean exists = false;
            int positionInBancosEstacion = 0;
            for (int i = 0; i < accesoriosEstacion.size(); i++) {
                if (accesoriosEstacion.get(i).getId().equals(accesorioselected.getId())) {
                    exists = true;
                    positionInBancosEstacion = i;
                    break;
                }
            }

            if (!exists && b) {
                accesoriosEstacion.add(accesorioselected);
            } else {
                accesoriosEstacion.remove(positionInBancosEstacion);
            }
        });
    }

    @Override
    public int getItemCount() {
        return accesoriosService.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        CheckBox checkBox;
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkboxBanco);
            textView = itemView.findViewById(R.id.nombreBanco);
        }
    }

    public List<AccesoriosYrepuestos> obtenerListaaccesorios() {
        return accesoriosEstacion;
    }
}
