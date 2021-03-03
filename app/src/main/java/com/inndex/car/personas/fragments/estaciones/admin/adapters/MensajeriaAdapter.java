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
import com.inndex.car.personas.model.Mensajeria;

import java.util.ArrayList;
import java.util.List;

public class MensajeriaAdapter extends RecyclerView.Adapter<MensajeriaAdapter.Viewholder> {

    List<Mensajeria> mensajeriaServicio;
    List<Mensajeria> mensajeriaEstacion;
    ArrayList<Mensajeria> editada = new ArrayList<>();


    public MensajeriaAdapter(List<Mensajeria> bancosServicio, List<Mensajeria> bancosEstacion) {
        this.mensajeriaServicio = bancosServicio;
        this.mensajeriaEstacion = bancosEstacion;
    }

    @NonNull
    @Override
    public MensajeriaAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemcajero, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MensajeriaAdapter.Viewholder holder, int position) {

        holder.setIsRecyclable(false);
        final Mensajeria mensajeria = mensajeriaServicio.get(position);
        holder.nombreMensajeria.setText(mensajeria.getNombre());

        for (Mensajeria mensajeriaEstacion : mensajeriaEstacion) {
            if (mensajeria.getId().equals(mensajeriaEstacion.getId())) {
                holder.checkBox.setChecked(true);
                editada.add(mensajeriaEstacion);
                break;
            }
        }
        holder.checkBox.setOnCheckedChangeListener((v, b) -> {

            Mensajeria mensajeriaSelected = mensajeriaServicio.get(position);
            boolean exists = false;
            int positionInBancosEstacion = 0;
            for (int i = 0; i < mensajeriaEstacion.size(); i++) {
                if(mensajeriaEstacion.get(i).getId().equals(mensajeriaSelected.getId())) {
                    exists = true;
                    positionInBancosEstacion = i;
                    break;
                }
            }

            if (!exists && b){
                mensajeriaEstacion.add(mensajeriaSelected);
            } else 
            {
                mensajeriaEstacion.remove(positionInBancosEstacion);
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
        return mensajeriaServicio.size();
    }

    public List<Mensajeria> obtenerListaMensajeria() {
        return mensajeriaEstacion;
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        CheckBox checkBox;
        TextView nombreMensajeria;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            checkBox = itemView.findViewById(R.id.checkboxBanco);
            nombreMensajeria = itemView.findViewById(R.id.nombreBanco);
        }
    }
}
