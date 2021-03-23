package com.inndex.fragments.configuracion_cuenta.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.Navigation;

import com.inndex.enums.EGenero;
import com.inndex.fragments.configuracion_cuenta.IeditAccout;
import com.inndex.model.Usuario;
import com.inndex.retrofit.MedidorApiAdapter;
import com.inndex.utils.Constantes;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PesenterEditAccount implements IpresenterEditAccount {

    private IeditAccout ieditAccout;
    private SharedPreferences myPreferences;
    private long userID;
    EditText name;
    EditText apellidos;
    EditText numeroIdentidad;
    EditText correo;
    EditText numeroCelular;
    TextView fechaNacimiento;
    RelativeLayout imagenCarga;
    private Context context;
    View root;

    public PesenterEditAccount(IeditAccout ieditAccout, long userID, Context context, View root) {
        this.ieditAccout = ieditAccout;
        this.userID = userID;
        this.context = context;
        this.root = root;
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
                    if (usuario != null) {
                        if (usuario.getNombres() != null) {
                            name.setText(usuario.getNombres());
                        }
                        if (usuario.getApellidos() != null) {
                            apellidos.setText(usuario.getApellidos());
                        }
                        if (usuario.getIdentificacion() != null) {
                            numeroIdentidad.setText(String.valueOf(usuario.getIdentificacion()));
                        }
                        if (usuario.getEmail() != null) {
                            correo.setText(usuario.getEmail());
                        }
                        if (usuario.getCelular() != null) {
                            numeroCelular.setText(usuario.getCelular());
                        }
                        if (usuario.getFechaNacimiento() != null) {
                            fechaNacimiento.setText(usuario.getFechaNacimiento());
                        }
                        Integer genero = usuario.getGenero();
                        if (genero != null) {
                            if (EGenero.MASCULINO.getId().equals(genero))
                                ieditAccout.getBtnMasculino().callOnClick();
                            if (EGenero.FEMENINO.getId().equals(genero))
                                ieditAccout.getBtnFemenino().callOnClick();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                imagenCarga.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void updateUserInfoAccount() {
        Usuario usuario = ieditAccout.updateUser();
        if (usuario != null) {
            Call<Usuario> updateUserInfo = MedidorApiAdapter.getApiService().updateUser(usuario);

            updateUserInfo.enqueue(new Callback<Usuario>() {
                @Override
                public void onResponse(Call<Usuario> call, Response<Usuario> response) {

                    if (response.isSuccessful()) {
                        Toast.makeText(context, "INFORMACIÓN ACTUALIZADA DE MANERA EXITOSA.", Toast.LENGTH_SHORT).show();
                        Navigation.findNavController(root).navigateUp();
                    } else
                        Toast.makeText(context, "ERROR ACTUALIZANDO INFORMACIÓN " + response.code(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<Usuario> call, Throwable t) {
                    Toast.makeText(context, "ERROR ACTUALIZANDO IONFORMACIÓN " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(context, "Por favor, Llenar todos los campos requeridos. ", Toast.LENGTH_SHORT).show();
        }
    }
}


