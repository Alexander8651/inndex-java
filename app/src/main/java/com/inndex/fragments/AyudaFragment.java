package com.inndex.fragments;

import android.content.ComponentName;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.util.Util;
import com.inndex.R;
import com.inndex.utils.Constantes;

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

        enviarCorreo = root.findViewById(R.id.enviarCorreo);
        titulo = root.findViewById(R.id.tv_toolbar_titulo);

        RelativeLayout relWhatsapp = root.findViewById(R.id.lay_whatsapp_ayuda);

        btnBack.setOnClickListener(v -> Navigation.findNavController(v).navigateUp());
        titulo.setText("Ayuda");

        enviarCorreo.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setType("text/plain");
            //intent.setData(Uri.parse("mailto:")); // only email apps should handle this
            intent.putExtra(Intent.EXTRA_EMAIL, new String[] { getString(R.string.inndex_email)});
            //requireActivity().startActivity(intent);
            startActivity(Intent.createChooser(intent,
                    "Send Email Using: "));
        });

        relWhatsapp.setOnClickListener(v -> goToWhatsapp());

        return root;
    }

    private void goToWhatsapp() {
        try {
            Uri uri = Uri.parse("https://api.whatsapp.com/send?phone=" + Constantes.ADMIN_PHONENUMBER);
            Intent i = new Intent(Intent.ACTION_VIEW, uri);
            //i.setPackage("com.whatsapp");
            startActivity(i);
        } catch (Exception e) {
            Toast.makeText(requireContext(), "Error/n" + e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}