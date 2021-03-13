package com.inndex.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.inndex.R;
import com.inndex.activities.mainactivity.MainActivity;
import com.inndex.database.DataBaseHelper;
import com.inndex.model.Bancos;
import com.inndex.model.Combustibles;
import com.inndex.model.MarcaEstacion;
import com.inndex.model.MetodoPago;
import com.inndex.model.Soat;
import com.inndex.model.Tiendas;
import com.inndex.retrofit.MedidorApiAdapter;
import com.inndex.utils.Constantes;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InicioActivity extends AppCompatActivity {

    private DataBaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        helper = OpenHelperManager.getHelper(InicioActivity.this, DataBaseHelper.class);

        final SharedPreferences myPreferences = getSharedPreferences(Constantes.SHARED_PREFERENCES_FILE_KEY, MODE_PRIVATE);

        int DURACION_SPLASH = 1200;
        new Handler().postDelayed(() -> {
            // Cuando pasen los 3 segundos, pasamos al Login
            if (myPreferences.getBoolean(Constantes.SESION_ACTIVE, false)) {
                irMain();
            } else {
                try {
                    getAllSoat();
                    getAllBancos();
                    getAllTiendas();
                    getAllCombustibles();
                    getAllMarcaEstacion();
                    getAllMetodosPago();
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


    private void getAllSoat() throws SQLException {
        final Dao<Soat, Long> dao = helper.getDaoSoat();
        if (!(dao.queryForAll().size() > 0)) {

            Call<List<Soat>> callGetSoat = MedidorApiAdapter.getApiService().getSoat();
            callGetSoat.enqueue(new Callback<List<Soat>>() {
                @Override
                public void onResponse(@NonNull Call<List<Soat>> call, @NonNull Response<List<Soat>> response) {
                    if (response.isSuccessful()) {
                        List<Soat> list = response.body();
                        if (list != null && list.size() > 0) {
                            try {
                                dao.create(list);
                            } catch (SQLException e1) {
                                Toast.makeText(InicioActivity.this, "Error en la base de datos.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else {
                        Toast.makeText(InicioActivity.this, "NO SE PUDIERON DESCARGAR LAS Soat INTENTALO MAS TARDE.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<List<Soat>> call, @NonNull Throwable t) {
                    Toast.makeText(InicioActivity.this, "NO SE PUDIERON DESCARGAR LAS Soat INTENTALO MAS TARDE.", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    private void getAllBancos() throws SQLException {
        final Dao<Bancos, Long> dao = helper.getDaoBancos();
        if (!(dao.queryForAll().size() > 0)) {
            Call<List<Bancos>> callGetBancos = MedidorApiAdapter.getApiService().getBancos();
            callGetBancos.enqueue(new Callback<List<Bancos>>() {
                @Override
                public void onResponse(@NonNull Call<List<Bancos>> call, @NonNull Response<List<Bancos>> response) {
                    if (response.isSuccessful()) {
                        List<Bancos> list = response.body();
                        if (list != null && list.size() > 0) {
                            try {
                                dao.create(list);
                            } catch (SQLException e1) {
                                Toast.makeText(InicioActivity.this, "Error en la base de datos.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else {
                        Toast.makeText(InicioActivity.this, "NO SE PUDIERON DESCARGAR LAS Bancos INTENTALO MAS TARDE.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<List<Bancos>> call, @NonNull Throwable t) {
                    Toast.makeText(InicioActivity.this, "NO SE PUDIERON DESCARGAR LAS Bancos INTENTALO MAS TARDE.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void getAllTiendas() throws SQLException {
        final Dao<Tiendas, Long> dao = helper.getDaoTiendas();
        if (!(dao.queryForAll().size() > 0)) {

            Call<List<Tiendas>> callGetTiendas = MedidorApiAdapter.getApiService().getTiendas();
            callGetTiendas.enqueue(new Callback<List<Tiendas>>() {
                @Override
                public void onResponse(@NonNull Call<List<Tiendas>> call, @NonNull Response<List<Tiendas>> response) {
                    if (response.isSuccessful()) {
                        List<Tiendas> list = response.body();
                        if (list != null && list.size() > 0) {
                            try {
                                dao.create(list);
                            } catch (SQLException e1) {
                                Toast.makeText(InicioActivity.this, "Error en la base de datos.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else {
                        Toast.makeText(InicioActivity.this, "NO SE PUDIERON DESCARGAR LAS Tiendas INTENTALO MAS TARDE.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<List<Tiendas>> call, @NonNull Throwable t) {
                    Toast.makeText(InicioActivity.this, "NO SE PUDIERON DESCARGAR LAS Tiendas INTENTALO MAS TARDE.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void getAllCombustibles() throws SQLException {
        final Dao<Combustibles, Long> dao = helper.getDaoCombustibles();
        if (!(dao.queryForAll().size() > 0)) {
            Call<List<Combustibles>> callGetCombustibles = MedidorApiAdapter.getApiService().getCombustiblesAll();
            callGetCombustibles.enqueue(new Callback<List<Combustibles>>() {
                @Override
                public void onResponse(@NonNull Call<List<Combustibles>> call, @NonNull Response<List<Combustibles>> response) {
                    if (response.isSuccessful()) {
                        List<Combustibles> list = response.body();
                        if (list != null && list.size() > 0) {
                            try {
                                dao.create(list);
                            } catch (SQLException e1) {
                                Toast.makeText(InicioActivity.this, "Error en la base de datos.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else {
                        Toast.makeText(InicioActivity.this, "NO SE PUDIERON DESCARGAR LAS Combustibles INTENTALO MAS TARDE.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<List<Combustibles>> call, @NonNull Throwable t) {
                    Toast.makeText(InicioActivity.this, "NO SE PUDIERON DESCARGAR LAS Combustibles INTENTALO MAS TARDE.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void getAllMarcaEstacion() throws SQLException {
        final Dao<MarcaEstacion, Long> dao = helper.getDaoMarcaEstacion();
        if (!(dao.queryForAll().size() > 0)) {
            Call<List<MarcaEstacion>> callGetMarcaEstacion = MedidorApiAdapter.getApiService().getMarcasEstaciones();
            callGetMarcaEstacion.enqueue(new Callback<List<MarcaEstacion>>() {
                @Override
                public void onResponse(@NonNull Call<List<MarcaEstacion>> call, @NonNull Response<List<MarcaEstacion>> response) {
                    if (response.isSuccessful()) {
                        List<MarcaEstacion> list = response.body();
                        if (list != null && list.size() > 0) {
                            try {
                                dao.create(list);
                            } catch (SQLException e1) {
                                Toast.makeText(InicioActivity.this, "Error en la base de datos.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else {
                        Toast.makeText(InicioActivity.this, "NO SE PUDIERON DESCARGAR LAS MarcasVehiculos INTENTALO MAS TARDE.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<List<MarcaEstacion>> call, @NonNull Throwable t) {
                    Toast.makeText(InicioActivity.this, "NO SE PUDIERON DESCARGAR LAS MarcasVehiculos INTENTALO MAS TARDE.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void getAllMetodosPago() throws SQLException {
        final Dao<MetodoPago, Long> dao = helper.getDaoMetodoPago();
        if (!(dao.queryForAll().size() > 0)) {
            Call<List<MetodoPago>> callGetMarcaEstacion = MedidorApiAdapter.getApiService().getMetodosPago();
            callGetMarcaEstacion.enqueue(new Callback<List<MetodoPago>>() {
                @Override
                public void onResponse(@NonNull Call<List<MetodoPago>> call, @NonNull Response<List<MetodoPago>> response) {
                    if (response.isSuccessful()) {
                        List<MetodoPago> list = response.body();
                        if (list != null && list.size() > 0) {
                            try {
                                dao.create(list);
                            } catch (SQLException e1) {
                                Toast.makeText(InicioActivity.this, "Error en la base de datos.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else {
                        Toast.makeText(InicioActivity.this, "NO SE PUDIERON DESCARGAR LOS METODOS DE PAGO INTENTALO MAS TARDE.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<List<MetodoPago>> call, @NonNull Throwable t) {
                    Toast.makeText(InicioActivity.this, "NO SE PUDIERON DESCARGAR LAS MarcasVehiculos INTENTALO MAS TARDE.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}
