package com.inndex.car.personas.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.inndex.car.personas.R;
import com.inndex.car.personas.activities.mainactivity.MainActivity;
import com.inndex.car.personas.database.DataBaseHelper;
import com.inndex.car.personas.model.Estaciones;
import com.inndex.car.personas.model.MarcaCarros;
import com.inndex.car.personas.retrofit.MedidorApiAdapter;
import com.inndex.car.personas.utils.Constantes;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InicioActivity extends AppCompatActivity {

    private DataBaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_inicio);
        //this.getWindow().setStatusBarColor(Color.TRANSPARENT);


        helper = OpenHelperManager.getHelper(InicioActivity.this, DataBaseHelper.class);

        final SharedPreferences myPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        int DURACION_SPLASH = 1200;
        new Handler().postDelayed(() -> {
            // Cuando pasen los 3 segundos, pasamos al Login
            if (myPreferences.getBoolean(Constantes.SESION_ACTIVE, false)) {
                irMain();
            } else {
                try {
                    getAllStations();
                    getAllMarcas();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(InicioActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, DURACION_SPLASH);
    }

    private void irMain() {
        Intent intent = new Intent(InicioActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void getAllStations() throws SQLException {

        final Dao<Estaciones, Integer> dao = helper.getDaoEstaciones();
        if (!(dao.queryForAll().size() > 0)) {

            Call<List<Estaciones>> callGetStations = MedidorApiAdapter.getApiService().getEstaciones();
            callGetStations.enqueue(new Callback<List<Estaciones>>() {
                @Override
                public void onResponse(@NonNull Call<List<Estaciones>> call, @NonNull Response<List<Estaciones>> response) {
                    if (response.isSuccessful()) {
                        List<Estaciones> list = response.body();

                        if (list != null && list.size() > 0) {
                                try {
                                    dao.create(list);
                                } catch (SQLException e1) {
                                    Toast.makeText(InicioActivity.this, "Error en la base de datos.", Toast.LENGTH_SHORT).show();
                                }
                        }
                    } else {
                        Toast.makeText(InicioActivity.this, "NO SE PUDIERON DESCARGAR LAS ESTACIONES INTENTALO MAS TARDE.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<List<Estaciones>> call, @NonNull Throwable t) {
                    Toast.makeText(InicioActivity.this, "NO SE PUDIERON DESCARGAR LAS ESTACIONES INTENTALO MAS TARDE.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void getAllMarcas() throws SQLException {

        final Dao<MarcaCarros, Integer> dao = helper.getDaoMarcas();
        if (!(dao.queryForAll().size() > 0)) {
            Call<List<MarcaCarros>> callGetMarcas = MedidorApiAdapter.getApiService().getMarcasCarros();
            callGetMarcas.enqueue(new Callback<List<MarcaCarros>>() {
                @Override
                public void onResponse(@NonNull Call<List<MarcaCarros>> call, @NonNull Response<List<MarcaCarros>> response) {

                    if (response.isSuccessful()) {
                        List<MarcaCarros> list = response.body();
                        if (list != null && list.size() > 0) {
                            for (MarcaCarros e : list) {
                                try {
                                    dao.create(e);
                                } catch (SQLException e1) {
                                    Toast.makeText(InicioActivity.this, "Error en la base de datos.", Toast.LENGTH_SHORT).show();
                                    break;
                                }
                            }
                        }
                    } else {
                        Toast.makeText(InicioActivity.this, "NO SE PUDIERON DESCARGAR LAS MARCAS INTENTALO MAS TARDE.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<List<MarcaCarros>> call, @NonNull Throwable t) {
                    Toast.makeText(InicioActivity.this, "NO SE PUDIERON DESCARGAR LAS MARCAS INTENTALO MAS TARDE.", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }


}
