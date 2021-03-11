package com.inndex.car.personas.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.inndex.car.personas.R;
import com.inndex.car.personas.model.Promocion;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class PromocionesDetalleAdapter extends RecyclerView.Adapter<PromocionesDetalleAdapter.ViewHolder> {

    List<Promocion> promocions;
    private final Context context;

    public PromocionesDetalleAdapter(List<Promocion> promocions, Context context) {
        this.promocions = promocions;
        this.context = context;
    }

    @NonNull
    @Override
    public PromocionesDetalleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_promocion_detalle, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PromocionesDetalleAdapter.ViewHolder holder, int position) {

        final Promocion promocion = promocions.get(position);

        holder.titulo.setText(promocion.getTitulo());
        DecimalFormat formatter = new DecimalFormat("###,###");

        if (promocion.getPrecio() != null) {
            String sPrecio = formatter.format(Double.valueOf(promocion.getPrecio()));
            holder.precio.setText(context.getString(R.string.precio_combustible_placeholder, sPrecio.replace(",", ".")));
        }
        Glide.with(holder.itemView).load(promocion.getFoto()).into(holder.fotoPromocion);
    }

    @Override
    public int getItemCount() {
        return promocions.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView titulo;
        TextView precio;
        TextView tvDescripcion;
        ImageView fotoPromocion;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            titulo = itemView.findViewById(R.id.tituloPromocion);
            precio = itemView.findViewById(R.id.precioPromocion);
            fotoPromocion = itemView.findViewById(R.id.fotoPromocion);
            tvDescripcion = itemView.findViewById(R.id.descripcionPromocion);
        }
    }
}
