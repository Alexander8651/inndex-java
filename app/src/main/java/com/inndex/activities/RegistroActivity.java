package com.inndex.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.inndex.R;
import com.inndex.model.Usuario;
import com.inndex.retrofit.MedidorApiAdapter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistroActivity extends AppCompatActivity {

    private EditText etEmail, etNombres, etApellidos, etPassword;
    private Button btnRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        etEmail = findViewById(R.id.etEmail);
        etNombres = findViewById(R.id.etNombres);
        etApellidos = findViewById(R.id.etApellidos);
        etPassword = findViewById(R.id.etPassword);
        btnRegistrar = findViewById(R.id.btnRegistrar);
        btnRegistrar.setOnClickListener(v -> {

            String email = etEmail.getText().toString().toLowerCase();
            String nombres = etNombres.getText().toString();
            String apellidos = etApellidos.getText().toString();
            String password = etPassword.getText().toString();

            if (!email.isEmpty() && !password.isEmpty() && !nombres.isEmpty() && !apellidos.isEmpty()) {

                Usuario nuevoUsuario = new Usuario();
                nuevoUsuario.setApellidos(apellidos);
                nuevoUsuario.setNombres(nombres);
                nuevoUsuario.setEmail(email);
                nuevoUsuario.setPassword(password);
                Call<Usuario> callPostRegisterUser = MedidorApiAdapter.getApiService().postRegisterUser(nuevoUsuario);
                callPostRegisterUser.enqueue(new Callback<Usuario>() {
                    @Override
                    public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(RegistroActivity.this, "REVISA TU CONEXIÓN.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(RegistroActivity.this, "ESTE USUARIO YA EXISTE.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Usuario> call, Throwable t) {
                        Toast.makeText(RegistroActivity.this, "REVISA TU CONEXIÓN.", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(getBaseContext(), "No ha llenado todos los datos", Toast.LENGTH_LONG).show();
            }
        });

    }

}
