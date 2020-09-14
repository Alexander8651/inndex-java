package com.inndex.car.personas.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.inndex.car.personas.R;
import com.inndex.car.personas.database.DataBaseHelper;
import com.inndex.car.personas.model.Estaciones;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class EstacionesActivity extends AppCompatActivity {

    private SharedPreferences myPreferences;
    private List<Estaciones> listEstaciones;
    private DataBaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estaciones);
        myPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        helper = OpenHelperManager.getHelper(EstacionesActivity.this, DataBaseHelper.class);


        listEstaciones = new ArrayList<>();
        try {
            getAllStations();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        RecyclerView rv = findViewById(R.id.rv_estaciones);
        final LinearLayoutManager llm = new LinearLayoutManager(EstacionesActivity.this);
        rv.setLayoutManager(llm);

        //RecyclerView.Adapter adapter = new EstacionesAdapter(listEstaciones, EstacionesActivity.this);
        //rv.setAdapter(adapter);
        init();
    }

    private void getAllStations() throws SQLException {

        final Dao<Estaciones, Integer> dao = helper.getDaoEstaciones();

        listEstaciones = dao.queryForAll();
    }

    private void init(){
        ProgressBar pbCombustible = findViewById(R.id.pbCombustible);
        pbCombustible.setMax(20);
        TextView tvCombustible = findViewById(R.id.tvCombustible);
        double nivelCombustible = Double.valueOf(myPreferences.getString("nivel", "0.0"));
        tvCombustible.setText(String.format(Locale.US,"%.1f", nivelCombustible)+" Gal.");
        pbCombustible.setProgress((int) nivelCombustible);
        pbCombustible.getProgressDrawable().setColorFilter(getResources().getColor(R.color.white),PorterDuff.Mode.SRC_IN);
        Typeface thin=Typeface.createFromAsset(getAssets(),"fonts/Roboto-Thin.ttf");

        TextView tvTitulo = findViewById(R.id.tvTitulo2);
        tvTitulo.setTypeface(thin);
        tvTitulo.setText("¿Donde tanquear?");
        ImageButton btnBack = findViewById(R.id.btnBack2);
        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            //finish();
        });
    }
}
