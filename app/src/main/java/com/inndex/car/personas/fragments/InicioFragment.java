package com.inndex.car.personas.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.inndex.car.personas.R;
import com.inndex.car.personas.activities.MainActivity;
import com.inndex.car.personas.model.Recorrido;
import com.inndex.car.personas.utils.CustomProgressDialog;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Timer;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link InicioFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link InicioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InicioFragment extends Fragment  {

    private OnFragmentInteractionListener mListener;
    private MainActivity mainActivity;
    private Typeface bold;
    private FloatingActionButton fabRuta;
    //private Button btnChangeDefaultState;
    //private TextView tvGalones, tvDistancia;
    boolean estado = false;
    private Timer mTimer1;
    private Handler mHandler;
    private double combustibleInicial, galonesPerdidos;
    private Recorrido recorrido;
    private CustomProgressDialog mCustomProgressDialog;
    private FloatingActionButton imgBtnAbrirDialogoReportar;
    private FloatingActionButton imgBtnEds;
    private FloatingActionButton imgBtnTienda;
    private ImageButton imgOcultarMenuSecundario;
    private EditText edtLookPlaces;
    private LinearLayout layMenuSecundario;
    FloatingActionButton fabUbicacion;
    private ImageButton btnMenu;
    private Button btnReportarAccidente;
    private Button btnReportarEdsNoRegistrada;
    private Button btnReportarPeajeNoRegistrado;
    private Button btnReportarEstadoVia;
    private Button btnReportarOtro;

    private TextView tvReportar;
    private TextView tvEds;
    private TextView tvTienda;

    private AlertDialog dialogReportar;
    private AlertDialog.Builder dialogBuilder;

    private boolean menuItemSelectedFlag = false;

    public static final int ITEM_REPORTAR_SELECCIONADO = 1;
    public static final int ITEM_EDS_SELECCIONADO = 2;
    public static final int ITEM_TIENDA_SELECCIONADO = 3;

    public static int AUTO_COMPLETE_PLACES_REQUEST_CODE = 87;

    public InicioFragment() {
        // Required empty public constructor
    }

    public InicioFragment(Typeface bold, MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        this.bold = bold;
        mCustomProgressDialog = new CustomProgressDialog(this.mainActivity);
        mCustomProgressDialog.setCanceledOnTouchOutside(false);
        mCustomProgressDialog.setCancelable(false);
    }

    // TODO: Rename and change types and number of parameters
    public static InicioFragment newInstance(String param1, String param2) {
        InicioFragment fragment = new InicioFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void drawRouteToStation() {
        mainActivity.getMapService().drawSationRoute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_inicio, container, false);
        //Button btnTanquear = v.findViewById(R.id.btnTanquear);
        Typeface light = Typeface.createFromAsset(Objects.requireNonNull(getActivity()).getAssets(), "fonts/Roboto-Light.ttf");
        Typeface bold = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Bold.ttf");
        imgBtnAbrirDialogoReportar = v.findViewById(R.id.img_btn_abrir_dialogo_reportar_accidente);
        imgBtnEds = v.findViewById(R.id.img_btn_eds);
        imgBtnTienda = v.findViewById(R.id.img_btn_tienda);
        edtLookPlaces = v.findViewById(R.id.edt_look_for_place);
        btnReportarAccidente = v.findViewById(R.id.btn_reportar_accidente);
        btnReportarEdsNoRegistrada = v.findViewById(R.id.btn_reportar_eds_no_registrada);
        btnReportarEstadoVia = v.findViewById(R.id.btn_reportar_estado_via);
        btnReportarPeajeNoRegistrado = v.findViewById(R.id.btn_reportar_peaje_no_registrado);
        btnReportarOtro = v.findViewById(R.id.btn_reportar_otro);
        layMenuSecundario = v.findViewById(R.id.lay_menu_secundario);
        imgOcultarMenuSecundario = v.findViewById(R.id.img_btn_ocultar_menu_secundario);
        fabUbicacion = v.findViewById(R.id.fabUbicacion);
        fabRuta = v.findViewById(R.id.fabRuta);
        btnMenu = v.findViewById(R.id.btnMenu);
        tvEds = v.findViewById(R.id.tv_eds);
        tvReportar = v.findViewById(R.id.tv_reportar);
        tvTienda = v.findViewById(R.id.tv_tienda);
        init();

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AUTO_COMPLETE_PLACES_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                edtLookPlaces.setText(place.getAddress());
            } else {
                Log.e("ERROR", "EN INICIO FRAGMENT");
            }
        } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
            Status status = Autocomplete.getStatusFromIntent(data);
            Log.e("STATUS", status.getStatusMessage());
            Toast.makeText(mainActivity, "ERROR EN PLACES ", Toast.LENGTH_SHORT).show();
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    private void abrirDialogoReportarAccidente() {
        dialogBuilder = new AlertDialog.Builder(mainActivity);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_reportar_accidente, null);

        dialogBuilder.setView(dialogView);
        dialogReportar = dialogBuilder.create();
        dialogReportar.show();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void setBold(Typeface bold) {
        this.bold = bold;
    }

    public FloatingActionButton getFabRuta() {
        return fabRuta;
    }

    private void init() {
        Typeface thin = Typeface.createFromAsset(mainActivity.getAssets(), "fonts/Roboto-Thin.ttf");
        Typeface robotoRegular = Typeface.createFromAsset(mainActivity.getAssets(), "fonts/Roboto-Regular.ttf");

        //edtLookPlaces.setTypeface(thin);
        //edtLookPlaces.setTextColor(getResources().getColor(R.color.colorPrimary, null));

        tvEds.setTypeface(robotoRegular);
        tvReportar.setTypeface(robotoRegular);
        tvTienda.setTypeface(robotoRegular);
        btnReportarAccidente.setTypeface(thin);
        btnReportarPeajeNoRegistrado.setTypeface(thin);
        btnReportarEdsNoRegistrada.setTypeface(thin);
        btnReportarOtro.setTypeface(thin);
        btnReportarEstadoVia.setTypeface(thin);
        btnReportarOtro.setTextColor(getResources().getColor(R.color.colorPrimary, null));
        btnReportarEdsNoRegistrada.setTextColor(getResources().getColor(R.color.colorPrimary, null));
        btnReportarPeajeNoRegistrado.setTextColor(getResources().getColor(R.color.colorPrimary, null));
        btnReportarAccidente.setTextColor(getResources().getColor(R.color.colorPrimary, null));
        btnReportarEstadoVia.setTextColor(getResources().getColor(R.color.colorPrimary, null));

        btnReportarAccidente.setOnClickListener(vReportarAccidente -> {
            layMenuSecundario.setVisibility(View.GONE);
            abrirDialogoReportarAccidente();
        });
        imgOcultarMenuSecundario.setOnClickListener(vOcultar -> {
            layMenuSecundario.setVisibility(View.GONE);
            imgBtnAbrirDialogoReportar.setImageResource(R.drawable.reportar_negro);
            tvReportar.setTextColor(getResources().getColor(R.color.colorPrimary,null));
        });
        edtLookPlaces.setFocusable(false);
        Places.initialize(mainActivity, "AIzaSyCRfWREv6YQGU8OBG0lOmXMOT16wjS2sC4");
        edtLookPlaces.setOnClickListener(viewEdt -> {
            List<Place.Field> lPlacesFields = Arrays.asList(Place.Field.ADDRESS, Place.Field.LAT_LNG, Place.Field.NAME);
            Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, lPlacesFields).build(mainActivity);
            startActivityForResult(intent, AUTO_COMPLETE_PLACES_REQUEST_CODE);
        });
        imgBtnAbrirDialogoReportar.setOnClickListener(v13 -> {
            updateMenuIcons(ITEM_REPORTAR_SELECCIONADO);
        });
        recorrido = new Recorrido();
        fabUbicacion.setOnClickListener(v2 -> {
            this.mainActivity.getMapService().mostrarUbicacion();
            fabUbicacion.hide();
        });
        fabRuta.setOnClickListener(v1 -> {
            this.drawRouteToStation();
            fabRuta.hide();
        });
        btnMenu.setOnClickListener(vMenu -> mainActivity.openSideMenu());
        imgBtnEds.setOnClickListener(click -> {
            mainActivity.goToEstacionesFiltros();
        });
    }

    private void updateMenuIcons(int selectedItem) {

        switch (selectedItem) {
            case ITEM_REPORTAR_SELECCIONADO:
                if (!menuItemSelectedFlag) {
                    layMenuSecundario.setVisibility(View.VISIBLE);
                    imgBtnAbrirDialogoReportar.setImageResource(R.drawable.reportar_rojo);
                    tvReportar.setTextColor(getResources().getColor(R.color.colorAccent,null));
                    btnReportarOtro.setTextColor(getResources().getColor(R.color.colorPrimary, null));
                    btnReportarEdsNoRegistrada.setTextColor(getResources().getColor(R.color.colorPrimary, null));
                    btnReportarPeajeNoRegistrado.setTextColor(getResources().getColor(R.color.colorPrimary, null));
                    btnReportarAccidente.setTextColor(getResources().getColor(R.color.colorPrimary, null));
                    btnReportarEstadoVia.setTextColor(getResources().getColor(R.color.colorPrimary, null));

                } else {
                    layMenuSecundario.setVisibility(View.GONE);
                    imgBtnAbrirDialogoReportar.setImageResource(R.drawable.reportar_negro);
                    tvReportar.setTextColor(getResources().getColor(R.color.colorPrimary,null));
                }
                break;
            case ITEM_EDS_SELECCIONADO:
                layMenuSecundario.setVisibility(View.GONE);

                /*if (!menuItemSelectedFlag)
                    imgBtnEds.setImageResource(R.drawable.eds_rojo);
                else
                    imgBtnEds.setImageResource(R.drawable.eds_negro);
                break;*/
            case ITEM_TIENDA_SELECCIONADO:
                layMenuSecundario.setVisibility(View.GONE);
                if (!menuItemSelectedFlag)
                    imgBtnTienda.setImageResource(R.drawable.tienda_rojo);
                else
                    imgBtnTienda.setImageResource(R.drawable.tienda_negro);
                break;
        }
        menuItemSelectedFlag = !menuItemSelectedFlag;
    }


    public void onMapPositionChange() {
        fabUbicacion.show();
    }

    public void onMapMarkerSelected() {
        fabRuta.show();
    }

    public void onChangeRouteButtonIcon() {
        fabRuta.setBackgroundDrawable(getResources().getDrawable(R.drawable.navegacion, null));
    }
}
