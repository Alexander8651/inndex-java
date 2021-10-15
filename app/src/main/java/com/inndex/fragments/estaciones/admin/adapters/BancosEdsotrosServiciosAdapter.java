package com.inndex.fragments.estaciones.admin.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.inndex.R;
import com.inndex.model.Bancos;

import java.util.ArrayList;
import java.util.List;

public class BancosEdsotrosServiciosAdapter extends RecyclerView.Adapter<BancosEdsotrosServiciosAdapter.Viewholder> {

    List<Bancos> bancosServicio;
    ArrayList<Bancos> bancosEstacion;
    ArrayList<Bancos> editada = new ArrayList<>();


    public BancosEdsotrosServiciosAdapter(List<Bancos> bancosServicio, ArrayList<Bancos> bancosEstacion) {
        this.bancosServicio = bancosServicio;
        this.bancosEstacion = bancosEstacion;
    }

    @NonNull
    @Override
    public BancosEdsotrosServiciosAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemcajero, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BancosEdsotrosServiciosAdapter.Viewholder holder, int position) {

        holder.setIsRecyclable(false);
        final Bancos bancos = bancosServicio.get(position);
        holder.nombreBanco.setText(bancos.getNombre());

        if(bancosEstacion == null){
            bancosEstacion = new ArrayList<>();
        }

        for (Bancos bancoEstacion : bancosEstacion) {
            if (bancos.getId().equals(bancoEstacion.getId())) {
                holder.checkBox.setChecked(true);
                editada.add(bancoEstacion);
                break;
            }
        }
        holder.checkBox.setOnCheckedChangeListener((v, b) -> {

            Bancos bancoSelected = bancosServicio.get(position);
            boolean exists = false;
            int positionInBancosEstacion = 0;
            for (int i = 0; i < bancosEstacion.size(); i++) {
                if(bancosEstacion.get(i).getId().equals(bancoSelected.getId())) {
                    exists = true;
                    positionInBancosEstacion = i;
                    break;
                }
            }

            if (!exists && b){
                bancosEstacion.add(bancoSelected);
            } else{
                bancosEstacion.remove(positionInBancosEstacion);
            }

        });
    }

    @Override
    public void onViewRecycled(@NonNull Viewholder holder) {

        /*if (holder.checkBox != null) {
            holder.checkBox.setOnClickListener(null);
        }*/
        super.onViewRecycled(holder);
    }

    @Override
    public int getItemCount() {
        return bancosServicio.size();
    }

    public ArrayList<Bancos> obtenerListaBancos() {
        return bancosEstacion;
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
