package com.inndex.fragments.promociones.presentador;

import android.graphics.Bitmap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.inndex.adapter.AdapterSpinnerLinea;

public interface IPromocionFormFragment {
    Spinner crearSpinerTipoOferta();
    Spinner crearSpinerCategoriaOferta();
    EditText crearEditTextTituloOferta();
    EditText crearEditTextPresioOferta();
    EditText crearEditTextDescripcionOferta();
    Button crearBotonPublicarOferta();

    Bitmap getBitmap();

}
