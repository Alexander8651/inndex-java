package com.inndex.car.personas.fragments.compra;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.inndex.car.personas.R;
import com.inndex.car.personas.activities.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.abhinay.input.CurrencyEditText;

public class CompraFragment extends Fragment {

    @BindView(R.id.edt_compra_valor)
    public CurrencyEditText edtValor;
    @BindView(R.id.edt_compra_galones)
    public CurrencyEditText edtGalones;
    @BindView(R.id.tv_compra_tipos_combustibles)
    public TextView tvTiposCombustibles;
    @BindView(R.id.tv_compra_ingresa_cantidad_valor)
    public TextView tvIngresarCantidadValor;
    @BindView(R.id.tv_compra_agregar_otros_productos)
    public TextView tvAgregarProductos;
    @BindView(R.id.sp_compra_tipos_combustibles)
    public Spinner spTiposCombustibles;

    private Typeface light;

    public CompraFragment() {
        // Required empty public constructor
    }

    public CompraFragment(MainActivity mainActivity, Typeface light) {
        this.mainActivity = mainActivity;
        this.light = light;
    }

    private MainActivity mainActivity;


    public static CompraFragment newInstance(String param1, String param2) {
        CompraFragment fragment = new CompraFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_compra, container, false);
        ButterKnife.bind(this, view);
        edtValor.setTypeface(light);
        edtGalones.setTypeface(light);
        tvTiposCombustibles.setTypeface(light);
        tvIngresarCantidadValor.setTypeface(light);
        tvAgregarProductos.setTypeface(light);
        return view;
    }

    public void showNumeroIslasDialog(){

    }
}