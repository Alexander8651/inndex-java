package com.inndex.car.personas.fragments.compra;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.inndex.car.personas.R;
import com.inndex.car.personas.activities.MainActivity;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CompraFragment extends Fragment implements
        AdapterView.OnItemSelectedListener {

    @BindView(R.id.edt_compra_valor)
    public EditText edtValor;
    @BindView(R.id.edt_compra_galones)
    public EditText edtGalones;
    @BindView(R.id.tv_compra_tipos_combustibles)
    public TextView tvTiposCombustibles;
    @BindView(R.id.tv_compra_ingresa_cantidad_valor)
    public TextView tvIngresarCantidadValor;
    @BindView(R.id.tv_compra_agregar_otros_productos)
    public TextView tvAgregarProductos;
    @BindView(R.id.tv_compra_signo_peso)
    public TextView tvCompraSignoPeso;
    @BindView(R.id.tv_compra_gal)
    public TextView tvCompraGal;
    @BindView(R.id.sp_compra_tipos_combustibles)
    public Spinner spTiposCombustibles;

    private final int CORRIENTE = 0;
    private final int EXTRA = 1;
    private final int DIESEL = 2;

    private final int SET_CANTIDAD = 1;
    private final int SET_VALOR = 2;

    private final int PRECIO_CORRIENTE = 8740;
    private final int PRECIO_EXTRA = 9940;
    private final int PRECIO_DIESEL = 10677;

    private boolean corrienteSelected;
    private boolean extraSelected;
    private boolean dieselSelected;

    private boolean edtGalonesFocused;
    private boolean edtValorFocused;
    private static DecimalFormat df2 = new DecimalFormat("#.00");
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
        tvCompraGal.setTypeface(light);
        tvCompraSignoPeso.setTypeface(light);

        edtValor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (edtValorFocused) {
                    setCantValor(SET_CANTIDAD);
                }
            }
        });

        edtGalones.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (edtGalonesFocused) {
                    setCantValor(SET_VALOR);
                }
            }
        });
        edtGalones.setOnFocusChangeListener((view1, b) -> {
            edtGalonesFocused = b;
        });

        edtValor.setOnFocusChangeListener((view12, b) -> {
            edtValorFocused = b;
        });

        initSpinnerTiposCombustibles();
        return view;
    }

    private void initSpinnerTiposCombustibles() {
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(mainActivity, android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.tipos_combustibles_venta));
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spTiposCombustibles.setAdapter(dataAdapter);
        spTiposCombustibles.setOnItemSelectedListener(this);
        spTiposCombustibles.setSelection(CORRIENTE);
        corrienteSelected = true;
    }

    public void showNumeroIslasDialog() {

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (i) {
            case CORRIENTE:
                corrienteSelected = true;
                extraSelected = false;
                dieselSelected = false;
                break;
            case EXTRA:
                corrienteSelected = false;
                extraSelected = true;
                dieselSelected = false;
                break;
            case DIESEL:
                corrienteSelected = false;
                dieselSelected = true;
                extraSelected = false;
                break;
        }
        callSetValCant();
    }

    private void callSetValCant() {
        try {
            edtGalones.setText("1");
            setCantValor(SET_VALOR);
        } catch (Exception ex) {
            Toast.makeText(mainActivity, "OCURRIO UNA EXCEPCION", Toast.LENGTH_SHORT).show();
        }
    }

    private void setCantValor(int setValorOGalones) {

        double result = 0;
        double galones = 0;
        double valor = 0;

        try {
            if (setValorOGalones == SET_CANTIDAD) {
                if (!edtValor.getEditableText().toString().equals(""))
                    valor = Double.parseDouble(edtValor.getEditableText().toString());
            }
            if (setValorOGalones == SET_VALOR) {
                if (!edtGalones.getEditableText().toString().equals(""))
                    galones = Double.parseDouble(edtGalones.getEditableText().toString());
            }

            if (corrienteSelected) {
                switch (setValorOGalones) {
                    case SET_CANTIDAD:
                        result = valor / PRECIO_CORRIENTE;
                        result = Double.parseDouble(df2.format(result));
                        edtGalones.setText(df2.format(result));
                        break;
                    case SET_VALOR:
                        result = galones * PRECIO_CORRIENTE;
                        edtValor.setText(String.valueOf((int) result));
                        break;
                }
            } else if (extraSelected) {
                switch (setValorOGalones) {
                    case SET_CANTIDAD:
                        result = valor / PRECIO_EXTRA;
                        result = Double.parseDouble(df2.format(result));
                        edtGalones.setText(df2.format(result));
                        break;
                    case SET_VALOR:
                        result = galones * PRECIO_EXTRA;
                        edtValor.setText(String.valueOf((int) result));
                        break;
                }
            } else if (dieselSelected) {
                switch (setValorOGalones) {
                    case SET_CANTIDAD:
                        result = valor / PRECIO_DIESEL;
                        result = Double.parseDouble(df2.format(result));
                        edtGalones.setText(df2.format(result));
                        break;
                    case SET_VALOR:
                        result = galones * PRECIO_DIESEL;
                        edtValor.setText(String.valueOf((int) result));
                        break;
                }
            }
        } catch (Exception ex) {
            Toast.makeText(mainActivity, "EX " + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}