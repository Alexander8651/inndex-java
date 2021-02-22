package com.inndex.car.personas.fragments.promociones.presentador;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.inndex.car.personas.adapter.AdapterSpinnerLinea;

public interface IPromocionFormFragment {
    Spinner crearSpinerTipoOferta();
    Spinner crearSpinerCategoriaOferta();
    EditText crearEditTextTituloOferta();
    EditText crearEditTextPresioOferta();
    EditText crearEditTextDescripcionOferta();
    Button crearBotonPublicarOferta();

}
