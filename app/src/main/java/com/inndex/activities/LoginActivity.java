package com.inndex.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.inndex.R;
import com.inndex.activities.mainactivity.MainActivity;
import com.inndex.constantes.ISecurityConstants;
import com.inndex.database.DataBaseHelper;
import com.inndex.model.Estados;
import com.inndex.model.LineasVehiculos;
import com.inndex.model.Usuario;
import com.inndex.model.Vehiculo;
import com.inndex.retrofit.MedidorApiAdapter;
import com.inndex.utils.Constantes;
import com.j256.ormlite.dao.Dao;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText etPassword, etEmail;
    private String email;
    public static final int LOCATION_REQUEST_CODE = 1;

    public static final int GOOGLE_SIGN_IN = 2;

    private Dao<Vehiculo, Integer> daoUsuarioModeloCarros;
    private Dao<LineasVehiculos, Integer> daoModeloCarros;

    private DataBaseHelper helper;

    private SharedPreferences myPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        checkGPSState();

        Typeface light = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf");
        Typeface bold = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Bold.ttf");

        etEmail = findViewById(R.id.etEmail);
        etEmail.setTypeface(light);
        etPassword = findViewById(R.id.etPassword);
        etPassword.setTypeface(light);
        LinearLayout llGoogleLogin = findViewById(R.id.llGoogleLogin);
        Button btnIngresar = findViewById(R.id.btnIngresar);
        btnIngresar.setTypeface(bold);

        btnIngresar.setOnClickListener(view -> {
            email = etEmail.getText().toString().toLowerCase();
            String password = etPassword.getText().toString();

            if (!email.isEmpty() && !password.isEmpty() && isEmailValid(email)) {
                Usuario user = new Usuario();
                user.setEmail(email);
                user.setPassword(password);
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    //login(user);
                    //mCustomProgressDialog.show("");
                    login(user);
                    //Toast.makeText(LoginActivity.this, "OK", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getBaseContext(), "No hay conexion a internet", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getBaseContext(), "Por favor ingrese Correo y Contrase??a v??lidos.", Toast.LENGTH_LONG).show();
            }
        });
        TextView btnRegistrar = findViewById(R.id.btnRegistrar);
        btnRegistrar.setTypeface(bold);
        btnRegistrar.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, RegistroActivity.class);
            startActivity(intent);
        });

        llGoogleLogin.setOnClickListener(v -> checkLogin());

        TextView tvRecordar = findViewById(R.id.tvRecordar);
        tvRecordar.setTypeface(bold);
        tvRecordar.setOnClickListener(view -> {
        });
        myPreferences = getSharedPreferences(Constantes.SHARED_PREFERENCES_FILE_KEY, MODE_PRIVATE);
    }

    private void checkLogin() {
        GoogleSignInAccount signIn = GoogleSignIn.getLastSignedInAccount(LoginActivity.this);
        if (signIn!= null) {

        } else {
            logInWithGoogle();
        }
    }

    private void logInWithGoogle() {
        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(ISecurityConstants.GOOGLE_SIGN_IN_OAUTH2_KEY).requestEmail().build();
        GoogleSignInClient client = GoogleSignIn.getClient(this, options);

        startActivityForResult(client.getSignInIntent(), GOOGLE_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GOOGLE_SIGN_IN) {

            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            GoogleSignInAccount account = null;
            try {
                account = task.getResult(ApiException.class);
                if (account != null) {
                    Log.e("1", account.getDisplayName());
                    Log.e("2", account.getEmail());
                    Log.e("3", account.getFamilyName());
                    Log.e("4", account.getPhotoUrl().toString());
                    Log.e("5", account.getGivenName());
                    Log.e("6", account.getId());
                    Log.e("7", account.getIdToken());
                }
            } catch (ApiException e) {
                Toast.makeText(this, "NO FUE POSIBLE INICIAR SESI??N CON GOOGLE", Toast.LENGTH_SHORT).show();
                Log.e("ex", e.getMessage());
            }


        }
    }

    private void login(Usuario user) {
        Call<Usuario> login = MedidorApiAdapter.getApiService().postLogin(Constantes.CONTENT_TYPE_JSON, user);
        login.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null)
                        irMain(response.body());
                } else {
                    Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void irMain(Usuario user) {
        SharedPreferences.Editor infoUsuario = myPreferences.edit();
        infoUsuario.putBoolean(Constantes.SESION_ACTIVE, true);
        infoUsuario.putString("email", user.getEmail());
        infoUsuario.putString("nombres", user.getNombres());
        infoUsuario.putLong(Constantes.DEFAULT_USER_ID, user.getId());
        infoUsuario.putString("celular", user.getCelular());
        infoUsuario.putString("apellidos", user.getApellidos());

        infoUsuario.apply();
        infoUsuario.commit();
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public boolean isEmailValid(String email) {
        String regExpn =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        CharSequence inputStr = email;
        Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        return matcher.matches();
    }

    private void setDefaultState(Estados estados) {
        if (estados != null) {
            myPreferences.edit().putString(Constantes.DEFAULT_STATE, estados.getNombre()).apply();
            myPreferences.edit().putInt(Constantes.DEFAULT_STATE_ID, estados.getId()).apply();
        }
    }

    private void checkGPSState() {
        if (!(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    LOCATION_REQUEST_CODE);
        }
    }
}
