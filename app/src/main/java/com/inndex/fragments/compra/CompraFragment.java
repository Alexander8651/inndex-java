package com.inndex.fragments.compra;

import android.app.AlertDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.inndex.R;
import com.inndex.activities.mainactivity.MainActivity;

import java.text.DecimalFormat;

public class CompraFragment extends Fragment implements
        AdapterView.OnItemSelectedListener {


    public EditText edtValor;

    public EditText edtGalones;

    public TextView tvTiposCombustibles;

    public TextView tvIngresarCantidadValor;

    public TextView tvAgregarProductos;

    public TextView tvCompraSignoPeso;

    public TextView tvCompraGal;

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
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mainActivity);
// ...Irrelevant code for customizing the buttons and title
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_compra_islas, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setPositiveButton("OK", (dialogInterface, i) -> {
            dialogInterface.dismiss();
        });
        dialogBuilder.setNegativeButton("CANCELAR", (dialogInterface, i) -> {
            dialogInterface.dismiss();
        });

        RelativeLayout layIslaUno = dialogView.findViewById(R.id.lay_isla_1);
        RelativeLayout layIslaDos = dialogView.findViewById(R.id.lay_isla_2);
        RelativeLayout layIslaTres = dialogView.findViewById(R.id.lay_isla_3);
        RelativeLayout layIslaCuatro = dialogView.findViewById(R.id.lay_isla_4);

        layIslaUno.setOnClickListener(v -> {
            v.setBackgroundColor(getResources().getColor(R.color.colorPrimary, null));
            layIslaDos.setBackgroundColor(getResources().getColor(R.color.gris_islas, null));
            layIslaTres.setBackgroundColor(getResources().getColor(R.color.gris_islas, null));
            layIslaCuatro.setBackgroundColor(getResources().getColor(R.color.gris_islas, null));
        });
        layIslaDos.setOnClickListener(v -> {
            v.setBackgroundColor(getResources().getColor(R.color.colorPrimary, null));
            layIslaUno.setBackgroundColor(getResources().getColor(R.color.gris_islas, null));
            layIslaTres.setBackgroundColor(getResources().getColor(R.color.gris_islas, null));
            layIslaCuatro.setBackgroundColor(getResources().getColor(R.color.gris_islas, null));       });
        layIslaTres.setOnClickListener(v -> {
            v.setBackgroundColor(getResources().getColor(R.color.colorPrimary, null));
            layIslaDos.setBackgroundColor(getResources().getColor(R.color.gris_islas, null));
            layIslaUno.setBackgroundColor(getResources().getColor(R.color.gris_islas, null));
            layIslaCuatro.setBackgroundColor(getResources().getColor(R.color.gris_islas, null));       });
        layIslaCuatro.setOnClickListener(v -> {
            v.setBackgroundColor(getResources().getColor(R.color.colorPrimary, null));
            layIslaDos.setBackgroundColor(getResources().getColor(R.color.gris_islas, null));
            layIslaTres.setBackgroundColor(getResources().getColor(R.color.gris_islas, null));
            layIslaUno.setBackgroundColor(getResources().getColor(R.color.gris_islas, null));       });

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.setOnShowListener(arg0 -> {
            alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.colorPrimary, null));
            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorPrimary, null));
        });
        alertDialog.show();
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
            edtGalones.setText("1.0");
            setCantValor(SET_VALOR);
        } catch (Exception ex) {
            Toast.makeText(mainActivity, "OCURRIO UNA EXCEPCION", Toast.LENGTH_SHORT).show();
        }
    }

    private void setCantValor(int setValorOGalones) {

        double result;
        double galones = 0;
        double valor = 0;

        try {
            if (setValorOGalones == SET_CANTIDAD) {
                if (!edtValor.getEditableText().toString().equals(""))
                    valor = Double.parseDouble(edtValor.getEditableText().toString());
            }
            if (setValorOGalones == SET_VALOR) {
                if (!edtGalones.getEditableText().toString().equals(""))
                    galones = Double.parseDouble(edtGalones.getEditableText().toString().replace(",", "."));
            }
            Log.e("RES", edtGalones.getEditableText().toString());

            //Toast.makeText(mainActivity, "GALONES " + edtGalones.getEditableText().toString(), Toast.LENGTH_SHORT).show();

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