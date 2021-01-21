package com.inndex.car.personas.fragments.configuracion_cuenta;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.hbb20.CountryCodePicker;
import com.inndex.car.personas.R;

import java.util.Calendar;

public class EditProfileFragment extends Fragment {

    private Button btnMasculino, btnFemenino;
    private TextView tvFecNacimiento;
    private EditText etText, etPassworDesa;
    private LinearLayout navPassword;
    private CountryCodePicker ccp;
    private View view;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        ccp = view.findViewById(R.id.ccp);// Las banderas y sus indicativos
        etText = view.findViewById(R.id.etCel);

        tvFecNacimiento = view.findViewById(R.id.tvFecNacimientoCapt);
        navPassword = view.findViewById(R.id.navPassword);

        etPassworDesa = view.findViewById(R.id.etPasswordDesa);
        etPassworDesa.setEnabled(false);

        btnMasculino = view.findViewById(R.id.btnMasculino);
        btnFemenino = view.findViewById(R.id.btnFemenino);



        //Cambiamos a masculino en negro
        btnMasculino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnMasculino.setBackgroundColor(Color.BLACK);
                btnMasculino.setTextColor(Color.WHITE);
                btnFemenino.setBackgroundColor(Color.WHITE);
                btnFemenino.setTextColor(Color.BLACK);

            }
        });

        //Cambiamos a femenino en negro
        btnFemenino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnFemenino.setBackgroundColor(Color.BLACK);
                btnFemenino.setTextColor(Color.WHITE);
                btnMasculino.setBackgroundColor(Color.WHITE);
                btnMasculino.setTextColor(Color.BLACK);

            }
        });


/*

        saveChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getNumber();

            }
        });
*/

        //Captura el click de fecha de nacimiento
        tvFecNacimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirCalendario();
            }
        });

        //navPassword.setOnClickListener(v ->
        //        Navigation.findNavController(v).navigate(R.id.action_editProfileFragment_to_editPasswordFragment));
        return view;
    }


    //Abrimos el calendario y obtenemos la fecha dada por el usuario
    public  void abrirCalendario(){
        Calendar calendar = Calendar.getInstance();
        int anio = calendar.get(Calendar.YEAR);
        int mes = calendar.get(Calendar.MONTH);
        int dia = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePicker = new DatePickerDialog(this.getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String fecha = dayOfMonth + "/" + (month+1) + "/" + year;
                tvFecNacimiento.setText(fecha);
            }
        }, anio, mes,dia);
        datePicker.show();
    }

    //para recuperar el telefono mas el indicativo
    private void getNumber(){
        String fullNumber = ccp.getFullNumber() + etText.getText().toString();
    }

}