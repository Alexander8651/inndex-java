package com.inndex.car.personas.fragments.configuracion_cuenta.presenter;

import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.inndex.car.personas.fragments.configuracion_cuenta.IeditAccout;
import com.inndex.car.personas.model.Usuario;
import com.inndex.car.personas.retrofit.InndexApiServices;
import com.inndex.car.personas.retrofit.MedidorApiAdapter;
import com.inndex.car.personas.utils.Constantes;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PesenterEditAccount implements IpresenterEditAccount {

    private IeditAccout ieditAccout;
    private SharedPreferences myPreferences;
    private int userID;
    EditText name;
    EditText apellidos;
    EditText numeroIdentidad;
    EditText correo;
    EditText numeroCelular;
    TextView fechaNacimiento;
    RelativeLayout imagenCarga;

    public PesenterEditAccount(IeditAccout ieditAccout, int userID) {
        this.ieditAccout = ieditAccout;
        this.userID = userID;
        setInfoUser();
        getUserInfoAccount();
    }

    @Override
    public void setInfoUser() {
        name = ieditAccout.createTextViewName();
        apellidos = ieditAccout.createTextViewLastName();
        numeroIdentidad = ieditAccout.createTextViewId();
        correo = ieditAccout.createTextViewEmail();
        numeroCelular = ieditAccout.createTextViewCellphone();
        fechaNacimiento = ieditAccout.createTextViewBornAt();
        imagenCarga = ieditAccout.imagenCarga();
    }

    @Override
    public void getUserInfoAccount() {
        imagenCarga.setVisibility(View.VISIBLE);
        Long id = (long) userID;

        Call<Usuario> getUserInfo = MedidorApiAdapter.getApiService().getUserInfoById(id);

        getUserInfo.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {

                if (response.isSuccessful()) {
                    imagenCarga.setVisibility(View.GONE);

                    Usuario usuario = response.body();

                    if (usuario.getNombres() != null) {
                        name.setText(usuario.getNombres());
                    }

                    if (usuario.getApellidos() != null){
                        apellidos.setText(usuario.getApellidos());
                    }

                    if (usuario.getIdentificacion() != null){
                        numeroIdentidad.setText(String.valueOf(usuario.getIdentificacion()));
                    }

                    if (usuario.getEmail() != null){
                        correo.setText(usuario.getEmail());
                    }

                    if (usuario.getCelular() != null){
                        numeroCelular.setText(usuario.getCelular());
                    }

                    if (usuario.getFechaNacimiento() != null){
                        fechaNacimiento.setText(usuario.getFechaNacimiento());
                    }
                }
            }
            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Log.d("llegoesto", t.getMessage());
            }
        });
    }

    @Override
    public void updateUserInfoAccount() {
        Usuario usuario = ieditAccout.updateUser();
        Call<Usuario> updateUserInfo = MedidorApiAdapter.getApiService().updateUser(Constantes.CONTENT_TYPE_JSON ,usuario);

        updateUserInfo.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                //Log.d("actualice", response.body().getNombres());
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {

            }
        });
    }
}


