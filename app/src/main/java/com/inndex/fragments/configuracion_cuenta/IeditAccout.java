package com.inndex.fragments.configuracion_cuenta;

import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.inndex.model.Usuario;

public interface IeditAccout {

    EditText createTextViewName();
    EditText createTextViewLastName();
    EditText createTextViewId();
    EditText createTextViewEmail();
    EditText createTextViewCellphone();
    TextView createTextViewBornAt();
    Usuario updateUser();
    RelativeLayout imagenCarga();
}
