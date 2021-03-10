package com.inndex.car.personas.fragments.promociones.presentador;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.inndex.car.personas.R;
import com.inndex.car.personas.model.Estaciones;
import com.inndex.car.personas.model.Promocion;
import com.inndex.car.personas.retrofit.MedidorApiAdapter;
import com.inndex.car.personas.utils.Constantes;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

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

    View view;

    public PresenterPromocionForm(IPromocionFormFragment iPromocionFormFragment, Context context,
                                  Estaciones est) {
        this.iPromocionFormFragment = iPromocionFormFragment;
        this.context = context;
        this.estacion = new Estaciones(est.getId());
        mostrarSpinnerTipoOferta();
        mostrarSpinnerCategoriaOferta();
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
    public void publicarOferta(View view) {

        this.view = view;
        publicarOferta = iPromocionFormFragment.crearBotonPublicarOferta();
        tituoOferta = iPromocionFormFragment.crearEditTextTituloOferta();
        PrecioOferta = iPromocionFormFragment.crearEditTextPresioOferta();
        DescripcionOferta = iPromocionFormFragment.crearEditTextDescripcionOferta();

        if (!tituoOferta.getText().toString().isEmpty() &&
                !PrecioOferta.getText().toString().isEmpty() &&
                !DescripcionOferta.getText().toString().isEmpty()) {

            promocion.setTitulo(tituoOferta.getText().toString());
            promocion.setPrecio(Integer.valueOf(PrecioOferta.getText().toString()));
            promocion.setDescripcion(DescripcionOferta.getText().toString());
            promocion.setActive(true);
            promocion.setEstaciones(estacion);
            Bitmap bitmap = iPromocionFormFragment.getBitmap();

            if (bitmap == null) {
                Toast.makeText(context, "DEBE SUBIR UNA IMAGEN PARA LA PROMOCION", Toast.LENGTH_SHORT).show();
                return;
            }

            iPromocionFormFragment.crearBotonPublicarOferta().setClickable(false);
            uploadImageToFirebase();

        } else {
            Toast.makeText(context, "Ingrese Todos los valores", Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadImageToFirebase() {
        StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("promociones");

        String random = UUID.randomUUID().toString();
        random = random.replace("-", "");
        StorageReference imageRef = storageRef.child(random + ".jpg");

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        iPromocionFormFragment.getBitmap().compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);
        final byte[] byteBitmap = byteArrayOutputStream.toByteArray();

        UploadTask uploadTask = imageRef.putBytes(byteBitmap);

        Task<Uri> taskUri = uploadTask.continueWithTask(task -> {

            if (!task.isSuccessful()) {
                Toast.makeText(context, "NO FUE POSIBLE SUBIR LA IMAGEN A NUESTROS SERVIDORES.", Toast.LENGTH_SHORT).show();
            }

            return imageRef.getDownloadUrl();
        }).addOnSuccessListener(completedTask -> {

            imageRef.getDownloadUrl().addOnSuccessListener(uri -> {

                Log.e("URL", uri.toString());

                promocion.setFoto(uri.toString());

                Call<Promocion> promocionCall = MedidorApiAdapter.getApiService().postSavePromocion(promocion);

                promocionCall.enqueue(new Callback<Promocion>() {
                    @Override
                    public void onResponse(Call<Promocion> call, Response<Promocion> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(context, "Información guardada de manera exitosa.", Toast.LENGTH_SHORT).show();
                            Bundle bundle = new Bundle();
                            bundle.putParcelable(Constantes.ESTACION_BUNDLE,estacion);
                            Navigation.findNavController(view).navigate(R.id.promocionListFragment, bundle);
                        } else {
                            Toast.makeText(context, "Error " + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Promocion> call, Throwable t) {
                        Toast.makeText(context, "ERROR " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            });
        });
    }
}
