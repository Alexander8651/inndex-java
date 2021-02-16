package com.inndex.car.personas.fragments.estaciones.admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.inndex.car.personas.R;

public class CombustibleYHorarioFragment extends Fragment {

    private Button btnGuardarCambiosCYH;
   private CheckBox cbGasoCorri,cbGasoExtra, cbDiesel, cbBiodiesel,cbGnv;
   private CheckBox cbLunes, cbMartes, cbMiercoles, cbJueves, cbViernes,cbSabado, cbDomingo;
   private LinearLayout llGasoCorri,llGasoExtra, llDiesel, llBiodiesel,llGnv;
   private LinearLayout llLunes, llMartes, llMiercoles, llJueves, llViernes,llSabado, llDomingo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_combustible_y_horario, container, false);
        TextView titulo = view.findViewById(R.id.tv_toolbar_titulo);
        titulo.setText("Combustibles y horarios");
        ImageButton btnBack = view.findViewById(R.id.btnBack);

        btnBack.setOnClickListener(v ->
                Navigation.findNavController(v).navigateUp()
        );

        btnGuardarCambiosCYH = view.findViewById(R.id.btn_guardar_cambios_cyh);

        //Checkbox tipo gasolina
        cbGasoCorri = view.findViewById(R.id.cb_gaso_corriente);
        cbGasoExtra = view.findViewById(R.id.cb_gaso_extra);
        cbDiesel = view.findViewById(R.id.cb_diesel);
        cbBiodiesel = view.findViewById(R.id.cb_biodiesel);
        cbGnv = view.findViewById(R.id.cb_gnv);

        //Checkbox horarios
        cbLunes = view.findViewById(R.id.cb_lunes);
        cbMartes = view.findViewById(R.id.cb_martes);
        cbMiercoles = view.findViewById(R.id.cb_miercoles);
        cbJueves = view.findViewById(R.id.cb_jueves);
        cbViernes = view.findViewById(R.id.cb_viernes);
        cbSabado = view.findViewById(R.id.cb_sabado);
        cbDomingo = view.findViewById(R.id.cb_domingo);

        //Linear Layout para hacer visible o invisible
        llGasoCorri = view.findViewById(R.id.ll_gaso_corriente);
        llGasoExtra = view.findViewById(R.id.ll_gaso_extra);
        llDiesel = view.findViewById(R.id.ll_diesel);
        llBiodiesel = view.findViewById(R.id.ll_biodiesel);
        llGnv = view.findViewById(R.id.ll_gnv);

        //Linear Layout para hacer visible o invisible
        llLunes = view.findViewById(R.id.ll_lunes);
        llMartes = view.findViewById(R.id.ll_martes);
        llMiercoles = view.findViewById(R.id.ll_miercoles);
        llJueves = view.findViewById(R.id.ll_jueves);
        llViernes = view.findViewById(R.id.ll_viernes);
        llSabado = view.findViewById(R.id.ll_sabado);
        llDomingo = view.findViewById(R.id.ll_domingo);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        btnGuardarCambiosCYH.setOnClickListener(v -> Toast.makeText(view.getContext(), "Se han guardado los cambios", Toast.LENGTH_SHORT).show());

        //Clicks visi o invi tipo gaso

        cbGasoCorri.setOnClickListener(v -> {
            if (cbGasoCorri.isChecked()){
                llGasoCorri.setVisibility(View.VISIBLE);
            } else {
                llGasoCorri.setVisibility(View.GONE);
            }

        });

        cbGasoExtra.setOnClickListener(v -> {
            if (cbGasoExtra.isChecked()){
                llGasoExtra.setVisibility(View.VISIBLE);
            } else {
                llGasoExtra.setVisibility(View.GONE);
            }

        });

        cbDiesel.setOnClickListener(v -> {
            if (cbDiesel.isChecked()){
                llDiesel.setVisibility(View.VISIBLE);
            } else {
                llDiesel.setVisibility(View.GONE);
            }

        });

        cbBiodiesel.setOnClickListener(v -> {
            if (cbBiodiesel.isChecked()){
                llBiodiesel.setVisibility(View.VISIBLE);
            } else {
                llBiodiesel.setVisibility(View.GONE);
            }

        });

        cbGnv.setOnClickListener(v -> {
            if (cbGnv.isChecked()){
                llGnv.setVisibility(View.VISIBLE);
            } else {
                llGnv.setVisibility(View.GONE);
            }

        });


        //Clicks visi o invi horarios

        cbLunes.setOnClickListener(v -> {
            if (cbLunes.isChecked()){
                Toast.makeText(view.getContext(), "Lunes 24 Horas", Toast.LENGTH_SHORT).show();
                llLunes.setVisibility(View.GONE);
            }else {
                llLunes.setVisibility(View.VISIBLE);
            }
        });

        cbMartes.setOnClickListener(v -> {
            if (cbMartes.isChecked()){
                Toast.makeText(view.getContext(), "Martes 24 Horas", Toast.LENGTH_SHORT).show();
                llMartes.setVisibility(View.GONE);
            }else {
                llMartes.setVisibility(View.VISIBLE);
            }
        });

        cbMiercoles.setOnClickListener(v -> {
            if (cbMiercoles.isChecked()){
                Toast.makeText(view.getContext(), "Miercoles 24 Horas", Toast.LENGTH_SHORT).show();
                llMiercoles.setVisibility(View.GONE);
            }else {
                llMiercoles.setVisibility(View.VISIBLE);
            }
        });

        cbJueves.setOnClickListener(v -> {
            if (cbJueves.isChecked()){
                Toast.makeText(view.getContext(), "Jueves 24 Horas", Toast.LENGTH_SHORT).show();
                llJueves.setVisibility(View.GONE);
            }else {
                llJueves.setVisibility(View.VISIBLE);
            }
        });

        cbViernes.setOnClickListener(v -> {
            if (cbViernes.isChecked()){
                Toast.makeText(view.getContext(), "Viernes 24 Horas", Toast.LENGTH_SHORT).show();
                llViernes.setVisibility(View.GONE);
            }else {
                llViernes.setVisibility(View.VISIBLE);
            }
        });

        cbSabado.setOnClickListener(v -> {
            if (cbSabado.isChecked()){
                Toast.makeText(view.getContext(), "Sabado 24 Horas", Toast.LENGTH_SHORT).show();
                llSabado.setVisibility(View.GONE);
            }else {
                llSabado.setVisibility(View.VISIBLE);
            }
        });

        cbDomingo.setOnClickListener(v -> {
            if (cbDomingo.isChecked()){
                Toast.makeText(view.getContext(), "Domingo 24 Horas", Toast.LENGTH_SHORT).show();
                llDomingo.setVisibility(View.GONE);
            }else {
                llDomingo.setVisibility(View.VISIBLE);
            }
        });


    }
}