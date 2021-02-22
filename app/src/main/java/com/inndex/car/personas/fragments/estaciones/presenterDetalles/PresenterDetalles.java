package com.inndex.car.personas.fragments.estaciones.presenterDetalles;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.inndex.car.personas.R;
import com.inndex.car.personas.model.EstacionProblema;
import com.inndex.car.personas.retrofit.MedidorApiAdapter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PresenterDetalles implements IPresenterDetalles {

    IEstacionDetalleFragment iEstacionDetalleFragment;
    Context context;

    public PresenterDetalles(IEstacionDetalleFragment iEstacionDetalleFragment, Context context) {
        this.iEstacionDetalleFragment = iEstacionDetalleFragment;
        this.context = context;
    }

    @Override
    public void MostrarDialogReportes() {

        AlertDialog.Builder dialog = new AlertDialog.Builder(context);

        dialog.setTitle("¿Que problema hay?");
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View v = layoutInflater.inflate(R.layout.itemreportarproblema, null);

        dialog.setView(v);
        dialog.setPositiveButton("Reportar", ((dialogInterface, i) -> {

            RadioButton rbDuplicado = v.findViewById(R.id.rbDuplicado);
            RadioButton rbCerradoMovido = v.findViewById(R.id.rbCerradoMovido);
            RadioButton rbDetallesIncorrectos = v.findViewById(R.id.rbDetallesIncorrectos);
            EstacionProblema estacionProblema = new EstacionProblema();

            if (rbDuplicado.isChecked()){
                estacionProblema.setNombre("Duplicado");
            }else if (rbCerradoMovido.isChecked()){
                estacionProblema.setNombre("Cerrado o movido");
            }else if (rbDetallesIncorrectos.isChecked()) {
                estacionProblema.setNombre("Detalles incorrectos");
            }

            Call<EstacionProblema> estacionProblemaCall = MedidorApiAdapter.getApiService().postSaveEstacionProblema(estacionProblema);
            estacionProblemaCall.enqueue(new Callback<EstacionProblema>() {
                @Override
                public void onResponse(Call<EstacionProblema> call, Response<EstacionProblema> response) {

                    if (response.isSuccessful()){
                        Toast.makeText(context, "Se creo el reporte", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<EstacionProblema> call, Throwable t) {
                }
            });
        }));

        dialog.setNegativeButton("Cancelar", ((dialogInterface, i) -> {
        }));

        dialog.show();


    }
}
