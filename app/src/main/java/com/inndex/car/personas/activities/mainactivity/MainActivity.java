package com.inndex.car.personas.activities.mainactivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.inndex.car.personas.R;
import com.inndex.car.personas.databinding.ActivityMainBinding;
import com.inndex.car.personas.enums.EEvents;
import com.inndex.car.personas.shared.SharedViewModel;
import com.inndex.car.personas.utils.Constantes;
import com.inndex.car.personas.utils.NavTypeFace;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawer;
    private NavigationView navigationView;
    private AppBarConfiguration mAppBarConfiguration;

    private boolean isEstacionesMapFragmentVisible;

    /* private TextView tvDefaultPlaca;
    private AlertDialog alert = null;
    private CustomProgressDialog mCustomProgressDialog;
    private SharedPreferences myPreferences;
    private List<Estaciones> estaciones;
    private DataBaseHelper helper;
    private View viewMap;
    private Estaciones estacionMasCercana;
    private EstacionesPlaces estacionesPlaces;
    private Fragment miFragment;
    //@BindView(R.id.linearCard)
    @BindView(R.id.card_places_search)
    public CardView cardSearchPlaces;

    private Timer timerInndexDeviceListener;
    private Integer tipoUsuario;
    private RecorridoService recorridoService;
    private long idUsuario;
    private Long idVehiculo;

    private MapService mapService;
    private InndexLocationService inndexLocationService;
    private String values;

    private String placa;
    private Toolbar toolbar;
    private Typeface light;

    private static final int HOME_CLICKED = 1;
    private static final int EDS_CLICKED = 2;
    private static final int STORE_CLICKED = 3;

    @BindView(R.id.img_btn_home)
    public ImageView imgBtnHome;
    @BindView(R.id.img_btn_eds)
    public ImageView imgBtnEds;
    @BindView(R.id.img_btn_favoritos)
    public ImageView imgBtnFavoritos;

    @BindView(R.id.btnBack2)
    public ImageView btnBack;
    //@BindView(R.id.tv_toolbar)
    //public TextView tvTitulo;

    boolean verServiciosButtonClicked;

    @BindView(R.id.tv_home)
    public TextView tvHome;
    @BindView(R.id.tv_eds)
    public TextView tvEds;
    @BindView(R.id.tv_favoritos)
    public TextView tvFavoritos;
    @BindView(R.id.tv_toolbar_nombre_estacion)
    public TextView tvToolbarNombreEstacion;
    @BindView(R.id.menu_main_first_division)
    public View viewFirstDivision;
    @BindView(R.id.menu_main_second_division)
    public View viewSecondDivision;

    @BindView(R.id.fab_ubicacion)
    public FloatingActionButton fabUbicacion;
    //    @BindView(R.id.fab_navegacion)
//    public FloatingActionButton fabNavegacion;
    @BindView(R.id.btn_menu)
    public FloatingActionButton btnMenu;

    @BindView(R.id.lay_menu_inferior)
    public LinearLayout layMenuInferior;
    @BindView(R.id.lay_buttons_station_selected)
    public LinearLayout layButtonsStationSelected;
    @BindView(R.id.lay_lista)
    public LinearLayout layLista;
    @BindView(R.id.lay_btn_navegar)
    public RelativeLayout layBtnNavegar;
    @BindView(R.id.lay_btn_ver_servicios)
    public RelativeLayout layBtnVerServicios;

    @BindView(R.id.lay_btn_indicaciones)
    public RelativeLayout layBtnIndicaciones;
    @BindView(R.id.lay_buttons_confirmar_compra)
    public LinearLayout layButtonsConfirmarCompra;
    @BindView(R.id.lay_btn_reclamar_ahora)
    public LinearLayout layBtnReclamarAhora;

    @BindView(R.id.lay_btn_confirmar_compra)
    public RelativeLayout layBtnConfirmarCompra;
    private BottomSheetBehavior mBottomSheetBehavior;

    private Presentador iPresentador;
    SearchView buscarlugar;
    private RecyclerView recycler;*/

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

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.homeFragment, R.id.opt_configuracion, R.id.editProfileFragment)
                .setOpenableLayout(drawer)
                .build();
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
        Menu m = navigationView.getMenu();

        for (int i = 0; i < m.size(); i++) {
            MenuItem mi = m.getItem(i);
            //for aapplying a font to subMenu ...
            SubMenu subMenu = mi.getSubMenu();
            if (subMenu != null && subMenu.size() > 0) {
                for (int j = 0; j < subMenu.size(); j++) {
                    MenuItem subMenuItem = subMenu.getItem(j);
                    applyFontToMenuItem(subMenuItem);
                }
            }
            //the method we have create in activity
            applyFontToMenuItem(mi);
        }

        TextView tvNombres = navigationView.getHeaderView(0).findViewById(R.id.tvUsuario);
        TextView tvEmail = navigationView.getHeaderView(0).findViewById(R.id.tvEmailUsuario);
        if (tvNombres != null)
            tvNombres.setText(nombresApellidos);
        else
            Log.e("ERR","NULL NOMBRES");

        if (tvEmail != null)
            tvEmail.setText(email);
        else
            Log.e("ERR","NULL EMAIL");
    }

    @Override
    public void onBackPressed() {
        if (isEstacionesMapFragmentVisible)
            model.setHomeEvents(EEvents.BACK_BUTTON_PRESSED.getId());
        else super.onBackPressed();
    }

    private void applyFontToMenuItem(MenuItem mi) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            Typeface light = getResources().getFont(R.font.roboto_light);
            SpannableString mNewTitle = new SpannableString(mi.getTitle());
            mNewTitle.setSpan(new NavTypeFace("", light), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            //mNewTitle.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER), 0, mNewTitle.length(), 0); Use this if you want to center the items
            mi.setTitle(mNewTitle);
        }
    }
}
