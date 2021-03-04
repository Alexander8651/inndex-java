package com.inndex.car.personas.fragments.promociones.presentador;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.inndex.car.personas.R;
import com.inndex.car.personas.model.Estaciones;
import com.inndex.car.personas.model.Promocion;
import com.inndex.car.personas.retrofit.MedidorApiAdapter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PresenterPromocionForm implements IPresenterPromocionForm {

    IPromocionFormFragment iPromocionFormFragment;
    Spinner tipoOferta, categoriaOferta;
    Context context;
    Button publicarOferta;
    EditText tituoOferta, PrecioOferta, DescripcionOferta;
    Promocion promocion = new Promocion();
    Estaciones estacion;

    public PresenterPromocionForm(IPromocionFormFragment iPromocionFormFragment, Context context,
                                  Estaciones est) {
        this.iPromocionFormFragment = iPromocionFormFragment;
        this.context = context;
        this.estacion = new Estaciones(est.getId());
        mostrarSpinnerTipoOferta();
        mostrarSpinnerCategoriaOferta();
        publicarOferta();
    }

    @Override
    public void mostrarSpinnerTipoOferta() {
        tipoOferta = iPromocionFormFragment.crearSpinerTipoOferta();
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                R.array.tipo_oferta, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tipoOferta.setAdapter(adapter);

        tipoOferta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String tipoOferta = (String) adapterView.getItemAtPosition(i);
                promocion.setTipo(tipoOferta);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                TextView tipoOferta = adapterView.findViewById(R.id.itemSpinnerMarca);
                tipoOferta.setText("Seleccione el tipo de oferta");
            }
        });
    }

    @Override
    public void mostrarSpinnerCategoriaOferta() {

        categoriaOferta = iPromocionFormFragment.crearSpinerCategoriaOferta();
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                R.array.categoria_oferta, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoriaOferta.setAdapter(adapter);

        categoriaOferta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String tipoOferta = (String) adapterView.getItemAtPosition(i);
                promocion.setCategoria(tipoOferta);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                TextView tipoOferta = adapterView.findViewById(R.id.itemSpinnerMarca);
                tipoOferta.setText("Seleccione la categoria");
            }
        });
    }

    @Override
    public void publicarOferta() {

        publicarOferta = iPromocionFormFragment.crearBotonPublicarOferta();
        tituoOferta = iPromocionFormFragment.crearEditTextTituloOferta();
        PrecioOferta = iPromocionFormFragment.crearEditTextPresioOferta();
        DescripcionOferta = iPromocionFormFragment.crearEditTextDescripcionOferta();

        publicarOferta.setOnClickListener(v -> {

            if (!tituoOferta.getText().toString().isEmpty() &&
                    !PrecioOferta.getText().toString().isEmpty() &&
                    !DescripcionOferta.getText().toString().isEmpty()) {

                promocion.setTitulo(tituoOferta.getText().toString());
                promocion.setPrecio(Double.valueOf(PrecioOferta.getText().toString()));
                promocion.setDescripcion(DescripcionOferta.getText().toString());
                promocion.setActive(true);
                promocion.setEstaciones(estacion);

                Call<Promocion> promocionCall = MedidorApiAdapter.getApiService().postSavePromocion(promocion);

                promocionCall.enqueue(new Callback<Promocion>() {
                    @Override
                    public void onResponse(Call<Promocion> call, Response<Promocion> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(context, "Se creo promocion", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Error " + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Promocion> call, Throwable t) {
                        Toast.makeText(context, "ERROR " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            } else {
                Toast.makeText(context, "Ingrese Todos los valores", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
