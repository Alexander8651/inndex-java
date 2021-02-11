package com.inndex.car.personas.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.inndex.car.personas.R;
import com.inndex.car.personas.enums.ECombustibles;
import com.inndex.car.personas.model.EstacionCombustibles;
import com.inndex.car.personas.model.Estaciones;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Locale;

public class EstacionesAdapter extends RecyclerView.Adapter<EstacionesAdapter.EstacionesViewHolder> {

    private List<Estaciones> items;
    private Context context;

    public class EstacionesViewHolder extends RecyclerView.ViewHolder {
        public TextView tvNombre;
        public TextView tvDistancia;
        public TextView tvMarca;
        public TextView tvDireccion;
        public TextView tvCalificacion;
        public TextView tvCantCalificacion;
        public RatingBar rbClasificacion;
        public View layCombustibles;


        public EstacionesViewHolder(View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvDistancia = itemView.findViewById(R.id.tvDistancia);
            tvMarca = itemView.findViewById(R.id.tvMarca);
            tvDireccion = itemView.findViewById(R.id.tvDireccion);
            tvCalificacion = itemView.findViewById(R.id.tvClasificacion);
            tvCantCalificacion = itemView.findViewById(R.id.tvCantClasificacion);
            rbClasificacion = itemView.findViewById(R.id.rbClasificacion);
            layCombustibles = itemView.findViewById(R.id.lay_combustibles_list);
        }
    }

    public EstacionesAdapter(List<Estaciones> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public EstacionesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.carview_estaciones, parent, false);
        return new EstacionesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EstacionesViewHolder holder, int position) {
        holder.tvNombre.setText(items.get(position).getNombre());
        holder.tvMarca.setText(items.get(position).getMarca());

        float distancia = items.get(position).getDistancia();
        float distanciaTolerance = distancia * 1.5f;
        distancia /= 1000;
        distanciaTolerance /= 1000;
        holder.tvDistancia.setText(String.format(Locale.ENGLISH, "de %.2f a %.2f km", distancia, distanciaTolerance));

        holder.tvDireccion.setText(items.get(position).getDireccion());
        holder.tvCalificacion.setText(String.valueOf(items.get(position).getCalificacion()));
        holder.rbClasificacion.setRating((float) items.get(position).getCalificacion());
        holder.tvCalificacion.setText(String.format(Locale.ENGLISH, "%.1f", items.get(position).getCalificacion()));

        holder.tvCantCalificacion.setText("(" + items.get(position).getCantCalificacion() + ")");

        initCombustibles(items.get(position), holder);

    }

    private void initCombustibles(Estaciones estaciones, EstacionesViewHolder holder) {

        View root = holder.layCombustibles;

        if (estaciones.getListEstacionCombustibles() != null && estaciones.getListEstacionCombustibles().size() > 0) {
            //holder.layCombustibles.setVisibility(View.VISIBLE);
            //root.findViewById(R.id.lay_combustibles_list).setVisibility(View.VISIBLE);
            TextView tvPreciosActualizados = root.findViewById(R.id.tv_estacion_servicios_precios_actualizados);
            tvPreciosActualizados.setVisibility(View.GONE);
            TextView nombreCombustible = null;
            TextView precioCombustible = null;
            for (EstacionCombustibles bomba : estaciones.getListEstacionCombustibles()) {

                if (bomba.getCombustible().getId().equals(ECombustibles.CORRIENTE.getId())) {
                    final LinearLayout corriente = root.findViewById(R.id.lay_corriente);
                    corriente.setVisibility(View.VISIBLE);
                    nombreCombustible = root.findViewById(R.id.nombreCorriente);
                    precioCombustible = root.findViewById(R.id.precioCorriente);
                }

                if (bomba.getCombustible().getId().equals(ECombustibles.EXTRA.getId())) {
                    final LinearLayout extra = root.findViewById(R.id.lay_extra);
                    extra.setVisibility(View.VISIBLE);
                    nombreCombustible = root.findViewById(R.id.nombreExtra);
                    precioCombustible = root.findViewById(R.id.precioExtra);
                }

                if (bomba.getCombustible().getId().equals(ECombustibles.DIESEL.getId())) {
                    final LinearLayout diesel = root.findViewById(R.id.DIESEL);
                    diesel.setVisibility(View.VISIBLE);
                    nombreCombustible = root.findViewById(R.id.nombreDiesel);
                    precioCombustible = root.findViewById(R.id.precioDiesel);
                }

                if (bomba.getCombustible().getId().equals(ECombustibles.DIESEL.getId())) {
                    final LinearLayout diesel = root.findViewById(R.id.DIESEL);
                    diesel.setVisibility(View.VISIBLE);
                    nombreCombustible = root.findViewById(R.id.nombreDiesel);
                    precioCombustible = root.findViewById(R.id.precioDiesel);
                }

                if (bomba.getCombustible().getId().equals(ECombustibles.BIODIESEL.getId())) {
                    final LinearLayout bioDiesel = root.findViewById(R.id.lay_bio_diesel);
                    bioDiesel.setVisibility(View.VISIBLE);
                    nombreCombustible = root.findViewById(R.id.nombre_bio_diesel);
                    precioCombustible = root.findViewById(R.id.precio_bio_diesel);
                }

                if (nombreCombustible != null && precioCombustible != null) {
                    nombreCombustible.setText(bomba.getCombustible().getNombre());
                    DecimalFormat formatter = new DecimalFormat("###,###");
                    String sPrecio = formatter.format(Double.valueOf(bomba.getPrecio().intValue()));
                    precioCombustible.setText(context.getString(R.string.precio_combustible_placeholder, sPrecio.replace(",", ".")));
                }
            }
        } else {
            holder.layCombustibles.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}
