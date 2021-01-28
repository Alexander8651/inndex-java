package com.inndex.car.personas.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.inndex.car.personas.R;
import com.inndex.car.personas.model.Estaciones;

import java.util.List;

public class AdapterMisEds extends RecyclerView.Adapter<AdapterMisEds.ViewHOlder> {

    private List<Estaciones> lEstaciones;
    private LayoutInflater inflater;
    private Context context;

    public AdapterMisEds(List<Estaciones> itemList, Context context){

        this.inflater = LayoutInflater.from(context);
        this.lEstaciones = itemList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHOlder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_mis_eds,parent,false);
        return new AdapterMisEds.ViewHOlder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHOlder holder, int position) {

        final  Estaciones estaciones = lEstaciones.get(position);
        holder.tpEstacion.setText(estaciones.getMarca());
        holder.nombreEstacionGaso.setText(estaciones.getNombre());
        holder.direccionEstacionGaso.setText(estaciones.getDireccion());
        holder.municipio.setText(estaciones.getMunicipio());
        holder.departamento.setText(estaciones.getDepartamento());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Navigation.findNavController(v).navigate(R.id.action_nav_gallery_to_editarEdsFragment);

            }
        });

    }

    @Override
    public int getItemCount() {return lEstaciones.size();}

    public class ViewHOlder extends RecyclerView.ViewHolder {

        TextView tpEstacion, nombreEstacionGaso, direccionEstacionGaso, municipio, departamento;

        public ViewHOlder(@NonNull View itemView) {
            super(itemView);
            tpEstacion = itemView.findViewById(R.id.tp_estacion);
            nombreEstacionGaso = itemView.findViewById(R.id.nombre_estacion_gaso);
            direccionEstacionGaso= itemView.findViewById(R.id.direccion_estacion_gaso);
            departamento = itemView.findViewById(R.id.tv_departamento);
            municipio = itemView.findViewById(R.id.tv_municipio);
        }
    }

}


