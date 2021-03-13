package com.inndex.fragments.estaciones.presenterDetalles;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.inndex.R;
import com.inndex.enums.ETipoReporte;
import com.inndex.model.EstacionReporte;
import com.inndex.model.Estaciones;
import com.inndex.model.TipoReporte;
import com.inndex.retrofit.MedidorApiAdapter;
import com.inndex.shared.SharedViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PresenterDetalles implements IPresenterDetalles {

    IEstacionDetalleFragment iEstacionDetalleFragment;
    Context context;
    SharedViewModel sharedViewModel;

    public PresenterDetalles(IEstacionDetalleFragment iEstacionDetalleFragment, Context context, SharedViewModel model) {
        this.iEstacionDetalleFragment = iEstacionDetalleFragment;
        this.context = context;
        this.sharedViewModel = model;
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
}
