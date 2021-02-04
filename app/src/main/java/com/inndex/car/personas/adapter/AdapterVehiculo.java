package com.inndex.car.personas.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.inndex.car.personas.R;
import com.inndex.car.personas.model.Vehiculo;

import java.util.List;

public class AdapterVehiculo extends RecyclerView.Adapter<com.inndex.car.personas.adapter.AdapterVehiculo.ViewHolder> {

    private List<Vehiculo> lVehiculo;
    private LayoutInflater inflater;
    private Context context;
    private int selectedStarPosition = -1;

    public AdapterVehiculo(List<Vehiculo> itemList, Context context){
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.lVehiculo = itemList;
    }

    @Override
    public int getItemCount() { return  lVehiculo.size();}

    @NonNull
    @Override
    public AdapterVehiculo.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_mis_vehiculos,parent,false);

        return new AdapterVehiculo.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterVehiculo.ViewHolder holder, int position) {

        final Vehiculo vehiculo = lVehiculo.get(position);
        holder.marca.setText(vehiculo.getLinea().getMarca().getNombre());
        holder.anio.setText(vehiculo.getAnio());
        holder.placa.setText(vehiculo.getPlaca());
        holder.linea.setText(vehiculo.getLinea().getNombre());

        holder.mRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.mRadioButton.isChecked()){
                    Toast.makeText(v.getContext(), "Seleccionaste el vehiculo # "+   (position+1) +" registrado con placa " + vehiculo.getPlaca() , Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public class ViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {

        TextView marca, anio , linea, placa;
        ImageView ivEdi_elim;
        RadioButton mRadioButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            marca = itemView.findViewById(R.id.tvMarca);
            anio = itemView.findViewById(R.id.tvAnio);
            linea = itemView.findViewById(R.id.tvLinea);
            placa = itemView.findViewById(R.id.tvPlaca);
            ivEdi_elim = itemView.findViewById(R.id.ivEdit_elim);

            ivEdi_elim.setOnClickListener(this);

            mRadioButton = itemView.findViewById(R.id.rb_rv_seleccion);
        }

        @Override
        public void onClick(View v) {
            showPopupMenu(v);
        }

        private void showPopupMenu(View view){
            PopupMenu popupMenu = new PopupMenu(view.getContext(),view);
            popupMenu.inflate(R.menu.mis_vehiculos_elim_edit);
            popupMenu.setOnMenuItemClickListener(this);
            popupMenu.show();

        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()){
                case R.id.mElim:
                    Toast.makeText(itemView.getContext(), "Eliminado", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.mEdit:
                    Toast.makeText(itemView.getContext(), "Editado", Toast.LENGTH_SHORT).show();
                    return true;
                default:
                    return  false;
            }

        }
    }

}
