package com.inndex.car.personas.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.inndex.car.personas.R;
import com.inndex.car.personas.database.DataBaseHelper;
import com.inndex.car.personas.model.Estados;
import com.inndex.car.personas.model.ModeloCarros;
import com.inndex.car.personas.model.Usuario;
import com.inndex.car.personas.model.Vehiculo;
import com.inndex.car.personas.retrofit.MedidorApiAdapter;
import com.inndex.car.personas.utils.Constantes;
import com.inndex.car.personas.utils.CustomProgressDialog;
import com.j256.ormlite.dao.Dao;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText etPassword, etEmail;
    private String email ;
    public static final int LOCATION_REQUEST_CODE = 1;

    private CustomProgressDialog mCustomProgressDialog;

    private Dao<Vehiculo, Integer> daoUsuarioModeloCarros;
    private Dao<ModeloCarros, Integer> daoModeloCarros;

    private DataBaseHelper helper;

    private SharedPreferences myPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        checkGPSState();

        Typeface light=Typeface.createFromAsset(getAssets(),"fonts/Roboto-Light.ttf");
        Typeface bold=Typeface.createFromAsset(getAssets(),"fonts/Roboto-Bold.ttf");
        mCustomProgressDialog = new CustomProgressDialog(this);
        mCustomProgressDialog.setCanceledOnTouchOutside(false);
        mCustomProgressDialog.setCancelable(false);

        etEmail =  findViewById(R.id.etEmail);
        etEmail.setTypeface(light);
        etPassword =  findViewById(R.id.etPassword);
        etPassword.setTypeface(light);
        Button btnIngresar = findViewById(R.id.btnIngresar);
        btnIngresar.setTypeface(bold);

        btnIngresar.setOnClickListener(view -> {
            email = etEmail.getText().toString().toLowerCase();
            String password = etPassword.getText().toString();

            if (!email.isEmpty() && !password.isEmpty() && isEmailValid(email)){
                Usuario user = new Usuario();
                user.setEmail(email);
                user.setPassword(password);
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()){
                    //login(user);
                    mCustomProgressDialog.show("");
                    login(user);
                    //Toast.makeText(LoginActivity.this, "OK", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getBaseContext(),"No hay conexion a internet", Toast.LENGTH_LONG).show();
                }
            }else{
                Toast.makeText(getBaseContext(),"Por favor ingrese Correo y Contraseña válidos.", Toast.LENGTH_LONG).show();
            }
        });
        Button btnRegistrar = findViewById(R.id.btnRegistrar);
        btnRegistrar.setTypeface(bold);
        btnRegistrar.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, RegistroActivity.class);
            startActivity(intent);
        });

        TextView tvRecordar = findViewById(R.id.tvRecordar);
        tvRecordar.setTypeface(bold);
        tvRecordar.setOnClickListener(view -> {
        });
        myPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    }

    private void login(Usuario user){


        Call<Usuario> login = MedidorApiAdapter.getApiService().postLogin(Constantes.CONTENT_TYPE_JSON, user);
        login.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful()){
                    irMain(user);
                }else{
                    mCustomProgressDialog.dismiss("");
                    Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                mCustomProgressDialog.dismiss("");
                Toast.makeText(LoginActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void irMain(Usuario user){
        SharedPreferences.Editor infoUsuario = myPreferences.edit();
        infoUsuario.putBoolean(Constantes.SESION_ACTIVE, true);
        infoUsuario.putString("email", user.getEmail());
        infoUsuario.putString("nombres", user.getNombres());
        infoUsuario.putInt(Constantes.DEFAULT_USER_ID, user.getId());
        infoUsuario.putString("celular", user.getCelular());
        infoUsuario.putString("apellidos", user.getApellidos());

        infoUsuario.apply();
        mCustomProgressDialog.dismiss("");
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public boolean isEmailValid(String email){
        String regExpn =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        +"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        CharSequence inputStr = email;
        Pattern pattern = Pattern.compile(regExpn,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        return matcher.matches();
    }

    private void setDefaultState(Estados estados){
        if(estados !=  null){
            myPreferences.edit().putString(Constantes.DEFAULT_STATE, estados.getNombre()).apply();
            myPreferences.edit().putInt(Constantes.DEFAULT_STATE_ID, estados.getId()).apply();
        }
    }

    private void checkGPSState() {
        if ( !(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) ) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    LOCATION_REQUEST_CODE);
        }
    }
}
