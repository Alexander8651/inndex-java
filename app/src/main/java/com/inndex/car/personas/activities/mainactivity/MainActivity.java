package com.inndex.car.personas.activities.mainactivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.inndex.car.personas.R;
import com.inndex.car.personas.databinding.ActivityMainBinding;
import com.inndex.car.personas.enums.EEvents;
import com.inndex.car.personas.shared.SharedViewModel;
import com.inndex.car.personas.utils.Constantes;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawer;
    private NavigationView navigationView;

    private boolean isEstacionesMapFragmentVisible;
    private SharedViewModel model;
    private ActivityMainBinding activityMainBinding;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        //View view = activityMainBinding.getRoot();
        setContentView(R.layout.activity_main);
        this.getWindow().setStatusBarColor(Color.TRANSPARENT);

        SharedPreferences preferences = getSharedPreferences(Constantes.SHARED_PREFERENCES_FILE_KEY, MODE_PRIVATE);

        String nombresApellidos = preferences.getString("nombres", "") + " " + preferences.getString("apellidos", "");
        String email = preferences.getString("email", "");

        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navigationView, navController);
        //navigationView.setNavigationItemSelectedListener(this);

        model = new ViewModelProvider(this).get(SharedViewModel.class);
        model.getEvents().observe(this, i -> {

            if (i.equals(EEvents.ESTACIONES_MAP_FRAGMENT_VISIBLE.getId())) {
                isEstacionesMapFragmentVisible = true;
            } else if (i.equals(EEvents.ESTACIONES_MAP_FRAGMENT_GONE.getId())) {
                isEstacionesMapFragmentVisible = false;
            }
        });
        model.getEditarEstacionEvent().observe(this, estacion -> {
            Bundle bundle = new Bundle();
            bundle.putParcelable("estacionIs", estacion);
            navController.navigate(R.id.opt_agregar_eds  ,bundle);
        });

        TextView tvNombres = navigationView.getHeaderView(0).findViewById(R.id.tvUsuario);
        TextView tvEmail = navigationView.getHeaderView(0).findViewById(R.id.tvEmailUsuario);
        if (tvNombres != null)
            tvNombres.setText(nombresApellidos);
        if (tvEmail != null)
            tvEmail.setText(email);
    }

    @Override
    public void onBackPressed() {
        if (isEstacionesMapFragmentVisible) {
            model.setHomeEvents(EEvents.BACK_BUTTON_PRESSED.getId());
        } else {
            super.onBackPressed();
        }
    }

}
