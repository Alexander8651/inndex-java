package com.inndex.car.personas.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.inndex.car.personas.R;
import com.inndex.car.personas.model.Promocion;

import java.util.ArrayList;

public class AdapterPromociones extends RecyclerView.Adapter<AdapterPromociones.ViewHolder> {

    ArrayList<Promocion> promocions;

    public AdapterPromociones(ArrayList<Promocion> promocions) {
        this.promocions = promocions;
    }

    @NonNull
    @Override
    public AdapterPromociones.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itempromocion, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPromociones.ViewHolder holder, int position) {

        final Promocion promocion = promocions.get(position);

        holder.titulo.setText(promocion.getTitulo());

        if (holder.precio != null){
            holder.precio.setText(promocion.getPrecio().toString());
        }
        Glide.with(holder.itemView).load(promocion.getFoto()).into(holder.fotoPromocion);
    }

    @Override
    public int getItemCount() {
        return promocions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView titulo;
        TextView precio;
        ImageView fotoPromocion, menuPromocionAdapter;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            titulo = itemView.findViewById(R.id.tituloPromocion);
            precio = itemView.findViewById(R.id.precioPromocion);
            fotoPromocion = itemView.findViewById(R.id.fotoPromocion);
            menuPromocionAdapter = itemView.findViewById(R.id.menuPromocionAdapter);
        }
    }
}
