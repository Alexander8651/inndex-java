package com.inndex.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.inndex.R;
public class AyudaFragment extends Fragment {

    ImageButton btnBack;
    TextView titulo;

    Button enviarCorreo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_ayuda, container, false);

        btnBack = root.findViewById(R.id.btnBack);

        enviarCorreo =root.findViewById(R.id.enviarCorreo);
        titulo =root.findViewById(R.id.tv_toolbar_titulo);

        btnBack.setOnClickListener(v-> Navigation.findNavController(v).navigateUp());
        titulo.setText("Ayuda");

        enviarCorreo.setOnClickListener(v ->{
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:")); // only email apps should handle this
            intent.putExtra(Intent.EXTRA_EMAIL, "inndexco@gmail.com");
            requireContext().startActivity(intent);
        });


        return root;
    }
}