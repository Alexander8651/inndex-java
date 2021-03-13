package com.inndex.fragments.configuracion_cuenta;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.Html;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.inndex.R;

public class PrivacidadFragment extends Fragment {

    ImageButton btnBack;
    TextView titulo;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_privacidad, null);

        final TextView body = root.findViewById(R.id.text_body);

        ImageButton btnBack = root.findViewById(R.id.btnBack);
        TextView titulo = root.findViewById(R.id.tv_toolbar_titulo);

        SpannableStringBuilder builder = new SpannableStringBuilder();

        titulo.setText("Privacidad");

        btnBack.setOnClickListener(v -> Navigation.findNavController(v).navigateUp());



        SpannableString str2= new SpannableString("Inndex, utiliza los servicios de ubicación para pfrecer un mejor servicio. ");
        str2.setSpan(new ForegroundColorSpan(Color.BLACK), 0, str2.length(), 0);
        builder.append(str2);

        SpannableString str1= new SpannableString("Ver mas...");
        str1.setSpan(new ForegroundColorSpan(Color.BLUE), 0, str1.length(), 0);
        builder.append(str1);

        body.setText( builder, TextView.BufferType.SPANNABLE);

        body.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_privacidadFragment_to_ubicacionTextoFragment));

        return root;
    }
}