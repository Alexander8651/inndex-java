package com.inndex.car.personas.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.inndex.car.personas.R;
import com.inndex.car.personas.model.Estaciones;

import java.util.ArrayList;

public class EstacionFavoritaAdapter extends RecyclerView.Adapter<EstacionFavoritaAdapter.EstacionesFavoritasViewHolder> {

    ArrayList<Estaciones> estaciones;
    Activity activity;


    public EstacionFavoritaAdapter(ArrayList<Estaciones> estaciones, Activity activity) {
        this.estaciones = estaciones;
        this.activity = activity;
    }


    @NonNull
    @Override
    public EstacionesFavoritasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemestacionfavorita, parent, false);
        return new EstacionesFavoritasViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EstacionesFavoritasViewHolder holder, int position) {

        Estaciones estacion = estaciones.get(position);
        String ubicacion = estacion.getMunicipio() + estacion.getDepartamento();

        holder.marcaestacion.setText(estacion.getMarca());
        holder.nombreestacion.setText(estacion.getNombre());
        holder.direccionestacion.setText(estacion.getDireccion());
        holder.ciudadestacion.setText(ubicacion);

        holder.menu.setOnClickListener(v ->{
            PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
            popupMenu.inflate(R.menu.menufavoritas);
            popupMenu.show();
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()){
                        case R.id.elinimar_favoritas:
                            Log.d("meejecuto", String.valueOf(position));
                            estaciones.remove(position);
                            notifyDataSetChanged();
                            return true;
                        case R.id.compartir_favoritas:
                            return true;
                        default:
                            return  false;
                    }
                }
            });
        });

    }

    @Override
    public int getItemCount() {
        return estaciones.size();
    }





    public static class EstacionesFavoritasViewHolder extends RecyclerView.ViewHolder  {

        TextView marcaestacion;
        TextView nombreestacion;
        TextView direccionestacion;
        TextView ciudadestacion;
        ImageView menu;

        public EstacionesFavoritasViewHolder(View itemView) {
            super(itemView);

            marcaestacion = itemView.findViewById(R.id.marca_estacion_item);
            nombreestacion = itemView.findViewById(R.id.nombre_estacion_item);
            direccionestacion = itemView.findViewById(R.id.direccion_estacion_item);
            ciudadestacion = itemView.findViewById(R.id.ciudad_estacion_item);
            menu = itemView.findViewById(R.id.menu3botonoes);
        }
    }
}

