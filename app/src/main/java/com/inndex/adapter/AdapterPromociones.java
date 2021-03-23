package com.inndex.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.inndex.R;
import com.inndex.model.Promocion;
import com.inndex.retrofit.MedidorApiAdapter;

import java.text.DecimalFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterPromociones extends RecyclerView.Adapter<AdapterPromociones.ViewHolder> {

    ArrayList<Promocion> promocions;
    private final Context context;

    public AdapterPromociones(ArrayList<Promocion> promocions, Context context) {
        this.promocions = promocions;
        this.context = context;
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
        DecimalFormat formatter = new DecimalFormat("###,###");

        if (promocion.getPrecio() != null) {
            String sPrecio = formatter.format(Double.valueOf(promocion.getPrecio()));
            holder.precio.setText(context.getString(R.string.precio_combustible_placeholder, sPrecio.replace(",", ".")));
        }
        Glide.with(holder.itemView).load(promocion.getFoto()).into(holder.fotoPromocion);

        holder.menuPromocionAdapter.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
            popupMenu.inflate(R.menu.menuadapterpromociones);
            popupMenu.show();

            popupMenu.setOnMenuItemClickListener(item -> {
                if (item.getItemId() == R.id.optEliminarPromocion) {
                    deletePromocion(promocion);
                }
                return true;
            });
        });
    }

    private void deletePromocion(Promocion promocion) {
        Call<Promocion> callDeletePromocion = MedidorApiAdapter.getApiService().deletePromocion(promocion.getId());
        callDeletePromocion.enqueue(new Callback<Promocion>() {
            @Override
            public void onResponse(Call<Promocion> call, Response<Promocion> response) {

                if (response.isSuccessful()) {
                    promocions.remove(promocion);
                    notifyDataSetChanged();
                    Toast.makeText(context, "PROMOCION ELIMINADA EXITOSAMENTE" + response.code(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "ERROR ELIMINANDO LA PROMOCION " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Promocion> call, Throwable t) {
                Toast.makeText(context, "ERROR ELIMINANDO LA PROMOCION ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return promocions.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

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
