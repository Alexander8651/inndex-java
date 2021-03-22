package com.inndex.activities.mainactivity;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.inndex.R;
import com.inndex.databinding.ActivityMainBinding;
import com.inndex.enums.EEvents;
import com.inndex.shared.SharedViewModel;
import com.inndex.utils.Constantes;

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
//        this.getWindow().setStatusBarColor(Color.TRANSPARENT);

        SharedPreferences preferences = getSharedPreferences(Constantes.SHARED_PREFERENCES_FILE_KEY, MODE_PRIVATE);

        String nombresApellidos = preferences.getString("nombres", "") + " " + preferences.getString("apellidos", "");
        String email = preferences.getString("email", "");

        NavigationView navigationView = findViewById(R.id.nav_view);


        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navigationView, navController);

        Menu menu = navigationView.getMenu();

        for (int i = 0; i < menu.size(); i++) {
            if(menu.getItem(i).getItemId() == R.id.opt_compartir_app){
                menu.getItem(i).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        shareApp();
                        return false;
                    }
                });
            }
        }


        //navigationView.setNavigationItemSelectedListener(this);
        /*navigationView.setNavigationItemSelectedListener(menuItem -> {
            Log.e("TAG", menuItem.getTitle().toString());
            if (menuItem.getItemId() == R.id.opt_compartir_app)

            return true;
        });*/
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
            navController.navigate(R.id.opt_agregar_eds, bundle);
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

    private void shareApp() {
        final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
        String shareMessage= "\nLet me recommend you this application\n\n";
        shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + appPackageName +"\n\n";
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
        startActivity(Intent.createChooser(shareIntent, "choose one"));
    }

}
