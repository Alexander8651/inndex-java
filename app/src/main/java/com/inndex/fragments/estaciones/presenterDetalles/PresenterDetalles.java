package com.inndex.fragments.estaciones.presenterDetalles;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.inndex.R;
import com.inndex.enums.ETipoReporte;
import com.inndex.model.EstacionCalificacion;
import com.inndex.model.EstacionReporte;
import com.inndex.model.Estaciones;
import com.inndex.model.TipoReporte;
import com.inndex.model.Usuario;
import com.inndex.retrofit.MedidorApiAdapter;
import com.inndex.shared.SharedViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PresenterDetalles implements IPresenterDetalles {

    IEstacionDetalleFragment iEstacionDetalleFragment;
    Context context;
    SharedViewModel sharedViewModel;
    Estaciones estaciones;

    public PresenterDetalles(IEstacionDetalleFragment iEstacionDetalleFragment, Context context, SharedViewModel model, Estaciones estaciones) {
        this.iEstacionDetalleFragment = iEstacionDetalleFragment;
        this.context = context;
        this.sharedViewModel = model;
        this.estaciones = estaciones;
    }

    @Override
    public void mostrarDialogReportes(Long idEstacion) {

        Estaciones estacionSeleccionada = new Estaciones(idEstacion);
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);

        dialog.setTitle("¿Que problema hay?");
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View v = layoutInflater.inflate(R.layout.itemreportarproblema, null);

        dialog.setView(v);
        dialog.setPositiveButton("Reportar", ((dialogInterface, i) -> {

            RadioButton rbDuplicado = v.findViewById(R.id.rbDuplicado);
            RadioButton rbCerradoMovido = v.findViewById(R.id.rbCerradoMovido);
            RadioButton rbDetallesIncorrectos = v.findViewById(R.id.rbDetallesIncorrectos);
            EstacionReporte estacionReporte = new EstacionReporte();
            TipoReporte tipoReporte = new TipoReporte();

            if (rbDuplicado.isChecked()) {
                tipoReporte.setId(ETipoReporte.DUPLICADO.getId());
            } else if (rbCerradoMovido.isChecked()) {
                tipoReporte.setId(ETipoReporte.CERRADO_O_MOVIDO.getId());
            } else if (rbDetallesIncorrectos.isChecked()) {
                tipoReporte.setId(ETipoReporte.DETALLES_INCORRECTOS.getId());
            }

            estacionReporte.setTipoReporte(tipoReporte);
            estacionReporte.setEstaciones(estacionSeleccionada);

            Call<EstacionReporte> estacionReporteCall = MedidorApiAdapter.getApiService().postEstacionReporteSave(estacionReporte);
            estacionReporteCall.enqueue(new Callback<EstacionReporte>() {
                @Override
                public void onResponse(@NonNull Call<EstacionReporte> call, @NonNull Response<EstacionReporte> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(context, "Se creo el reporte", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<EstacionReporte> call, @NonNull Throwable t) {
                }
            });
        }));

        dialog.setNegativeButton("Cancelar", ((dialogInterface, i) -> {
        }));
        dialog.show();
    }

    @Override
    public void editarEDS(Estaciones estacion) {
        sharedViewModel.setEditarEdsEvent(estacion);
    }

    @Override
    public void dialogCalificar() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.dialogocalificar, null);

        TextView nombreEds = v.findViewById(R.id.textviewtituloeds);
        RatingBar ratingBar = v.findViewById(R.id.rat_bar_estacion_servicios_calificacion_calificar);
        Button btnCalificar = v.findViewById(R.id.btnCalificar);
        Button btnCancelar = v.findViewById(R.id.btnCancelar);
        EditText comentariocalificar = v.findViewById(R.id.comentarioCalificacion);


        nombreEds.setText(estaciones.getNombre());
        builder.setView(v);
        AlertDialog dialog = builder.create();
        dialog.show();


        btnCancelar.setOnClickListener(view -> {

            dialog.dismiss();

        });

        btnCalificar.setOnClickListener(view ->{

            if(comentariocalificar.getText().toString().isEmpty()){
                Toast.makeText(context, "Ingrsa un comentario", Toast.LENGTH_SHORT).show();
            }else {
                float calificacion = ratingBar.getRating();

                Usuario usuario = new Usuario();

                EstacionCalificacion estacionCalificacion = new EstacionCalificacion((long) 1, (int) calificacion, comentariocalificar.getText().toString(),usuario, estaciones);

                Call<EstacionCalificacion> estacion = MedidorApiAdapter.getApiService().postEstacionCalificacionSave(estacionCalificacion);

                estacion.enqueue(new Callback<EstacionCalificacion>() {
                    @Override
                    public void onResponse(Call<EstacionCalificacion> call, Response<EstacionCalificacion> response) {
                        if (response.isSuccessful()){
                            Log.d("estoy", "estoy");

                            Toast.makeText(context, "Calificaste" + estaciones.getNombre(), Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }

                    }

                    @Override
                    public void onFailure(Call<EstacionCalificacion> call, Throwable t) {
                        Log.d("estoyerro", "estoyerro");


                    }
                });
            }

        });

    }
}
