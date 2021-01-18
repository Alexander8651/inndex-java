package com.inndex.car.personas.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.inndex.car.personas.R;
import com.inndex.car.personas.activities.MainActivity;
import com.inndex.car.personas.database.DataBaseHelper;
import com.inndex.car.personas.model.Estados;
import com.inndex.car.personas.model.ModeloCarros;
import com.inndex.car.personas.model.Vehiculo;
import com.inndex.car.personas.retrofit.MedidorApiAdapter;
import com.inndex.car.personas.utils.Constantes;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VehiculosAdapter extends RecyclerView.Adapter<VehiculosAdapter.EstacionesViewHolder> {

    private List<Vehiculo> items;
    private Context context;
    private String defaultPlaca;
    private MainActivity mainActivity;
    private DataBaseHelper helper;

    private Vehiculo vehiculoSelected;

    public class EstacionesViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        public TextView tvPlaca;
        public TextView tvModelo;
        public TextView tvMarca;
        public TextView tvLinea;
        public TextView tvHasTwoTanks;
        private ImageView imgMiVehicle;
        private RelativeLayout relativeLayout;
        public ImageButton btnChangeBlueTooth;

        public EstacionesViewHolder(View itemView) {
            super(itemView);
            tvPlaca = itemView.findViewById(R.id.tv_placa_mi_vehiculo);
            tvModelo = itemView.findViewById(R.id.tv_anio_mi_vehiculo);
            tvMarca = itemView.findViewById(R.id.tv_marca_mi_vehiculo);
            tvLinea =  itemView.findViewById(R.id.tv_linea_mi_vehiculo);
            tvHasTwoTanks = itemView.findViewById(R.id.tv_has_tow_tanks_mi_vehiculo);
            relativeLayout = itemView.findViewById(R.id.rel_mi_vehiculo);
            imgMiVehicle =itemView.findViewById(R.id.img_mi_vehiculo);
            Typeface light=Typeface.createFromAsset(context.getAssets(),"fonts/Roboto-Light.ttf");

            tvPlaca.setTypeface(light);
            tvMarca.setTypeface(light);
            tvModelo.setTypeface(light);
            tvLinea.setTypeface(light);
            tvHasTwoTanks.setTypeface(light);
            imgMiVehicle.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View v) {

            Vibrator vibrator = (Vibrator) mainActivity.getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(800);
            Vehiculo vehiculo = items.get(getAdapterPosition());
            if(vehiculo != null ){
                ((MainActivity)context).upateDefaultVehicle(vehiculo);
                defaultPlaca = vehiculo.getPlaca();
                notifyDataSetChanged();
                Toast.makeText(mainActivity, "VEHÍCULO SELECCIONADO DE MANERA EXITOSA.", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
    }

    public VehiculosAdapter(List<Vehiculo> items, Context context, DataBaseHelper helper) {
        this.items = items;
        this.context = context;
        this.mainActivity = (MainActivity)context;
        this.helper = helper;

        SharedPreferences sharedPreferences = mainActivity.getMyPreferences();
        vehiculoSelected = null;
        defaultPlaca = sharedPreferences.getString(Constantes.DEFAULT_PLACA, "");
    }

    @Override
    public EstacionesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_vehiculo,parent,false);
        return new EstacionesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EstacionesViewHolder holder, int position) {
        holder.tvLinea.setText(items.get(position).getLinea());
        holder.tvMarca.setText(items.get(position).getMarca());
        holder.tvModelo.setText(items.get(position).getAnio());
        holder.tvPlaca.setText(items.get(position).getPlaca());

        if(items.get(position).getPlaca().equals(defaultPlaca)){
            holder.relativeLayout.setBackgroundColor(context.getResources().getColor(R.color.yellow_soft));
        }
    }

    private void updateVehiculo() {

        try {
            Integer id = (int)(mainActivity.getMyPreferences().getLong(Constantes.DEFAULT_VEHICLE_ID, 0));
            Dao<Vehiculo, Integer> dao = helper.getDaoUsuarioHasModeloCarros();
            Vehiculo uhmc = dao.queryForId(id);
            if(uhmc == null){

                Toast.makeText(context, "ERROR AL GUARDAR", Toast.LENGTH_SHORT).show();
                return ;
            }
            ModeloCarros modeloCarros = new ModeloCarros();
            modeloCarros.setId((int)mainActivity.getMyPreferences().getLong("defaultModeloCarroId",0));

            vehiculoSelected.setId(uhmc.getId());
            vehiculoSelected.setModeloCarros(modeloCarros);
            vehiculoSelected.setUsuariosId(uhmc.getUsuariosId());
            Estados estados = new Estados();
            estados.setId(mainActivity.getMyPreferences().getInt(Constantes.DEFAULT_STATE_ID, 1));
            vehiculoSelected.setEstado(estados);

            Call<Vehiculo> callUpdateUsuariosHasModeloCarro = MedidorApiAdapter.getApiService()
                    .putUpdateUsuarioHasModeloCarro(Constantes.CONTENT_TYPE_JSON ,
                            vehiculoSelected);

            mainActivity.getmCustomProgressDialog().show("");
            callUpdateUsuariosHasModeloCarro.enqueue(new Callback<Vehiculo>() {
                @Override
                public void onResponse(Call<Vehiculo> call, Response<Vehiculo> response) {

                    mainActivity.getmCustomProgressDialog().dismiss("");
                    if(response.isSuccessful()) {
                        ((MainActivity)context).upateDefaultVehicle(vehiculoSelected);
                        notifyDataSetChanged();
                        Toast.makeText(mainActivity, "VEHÍCULO ACTUALIZADO DE MANERA EXITOSA.", Toast.LENGTH_SHORT).show();
                        //btnUpdateUhmc.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(Call<Vehiculo> call, Throwable t) {
                    mainActivity.getmCustomProgressDialog().dismiss("");
                    Toast.makeText(mainActivity, "NO SE PUDO ACTUALIZAR EL VEHÍCULO.", Toast.LENGTH_SHORT).show();
                }
            });

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
