package com.inndex.fragments.estaciones.admin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.inndex.R;
import com.inndex.adapter.AdapterMisEds;
import com.inndex.fragments.estaciones.admin.presentermisedsfragment.IMisEdsFragment;
import com.inndex.fragments.estaciones.admin.presentermisedsfragment.IPresenterMisEdsFragment;
import com.inndex.fragments.estaciones.admin.presentermisedsfragment.PresenterMisEdsFragment;
import com.inndex.model.Estaciones;
import com.inndex.utils.Constantes;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


public class MisEdsFragment extends Fragment implements IMisEdsFragment {

    private AdapterMisEds adapterMisEds;
    private List<Estaciones> estaciones;
    private RecyclerView recyclerView;
    private View root;
    private SharedPreferences myPreferences;
    private IPresenterMisEdsFragment iPresenterMisEdsFragment;
    private ImageButton btnBack;
    private TextView titulo;

    private TextView tvMensajeAdmin;
    private TextView tvInndexEmail;
    private Button btnSendEmail;
    private RelativeLayout relWhatsapp;
    LinearLayout llSeparator;

    private MisEdsViewModel galleryViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      /*  galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);*/
        root = inflater.inflate(R.layout.fragment_mis_eds, container, false);
        /*final TextView textView = root.findViewById(R.id.text_gallery);
        galleryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/

        btnBack = root.findViewById(R.id.btnBack);
        titulo = root.findViewById(R.id.tv_toolbar_titulo);

        titulo.setText("Mis EDS");

        tvMensajeAdmin = root.findViewById(R.id.tvMensajeAdmin);
        tvInndexEmail = root.findViewById(R.id.tvInndexEmail);
        btnSendEmail = root.findViewById(R.id.btnSendEmail);
        llSeparator = root.findViewById(R.id.separator);
        btnSendEmail.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:" + getString(R.string.inndex_email))); // only email apps should handle this
            intent.putExtra(Intent.EXTRA_EMAIL, getString(R.string.inndex_email));
            startActivity(intent);
        });

        relWhatsapp = root.findViewById(R.id.lay_whatsapp_ayuda);

        relWhatsapp.setOnClickListener(v -> goToWhatsapp());

        btnBack.setOnClickListener(v -> {
            Navigation.findNavController(v).navigateUp();
        });

        recyclerView = root.findViewById(R.id.rv_mis_eds);
        estaciones = new ArrayList<>();

        cargarEstaciones();
        mostarEstaciones();

        myPreferences = requireActivity().getSharedPreferences(Constantes.SHARED_PREFERENCES_FILE_KEY, MODE_PRIVATE);
        int userID = myPreferences.getInt(Constantes.DEFAULT_USER_ID, 0);

        iPresenterMisEdsFragment = new PresenterMisEdsFragment(this, userID);

        return root;
    }

    public void cargarEstaciones() {
        // estaciones.add(new Estaciones("EDS la esperanza","cl.22 # 68d-20","TERPEL", "Bogotá DC","Bogotá DC"));
    }

    public void mostarEstaciones() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterMisEds = new AdapterMisEds(estaciones, getContext());
        recyclerView.setAdapter(adapterMisEds);
    }

    @Override
    public AdapterMisEds crearAdater(ArrayList<Estaciones> estaciones) {
        return new AdapterMisEds(estaciones, requireContext());
    }

    @Override
    public void InicializarAdapter(AdapterMisEds adapterMisEds) {

        if (estaciones == null || estaciones.size() == 0) {
            recyclerView.setVisibility(View.GONE);
            tvMensajeAdmin.setVisibility(View.VISIBLE);
            tvInndexEmail.setVisibility(View.VISIBLE);
            btnSendEmail.setVisibility(View.VISIBLE);
            relWhatsapp.setVisibility(View.VISIBLE);
            llSeparator.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            tvMensajeAdmin.setVisibility(View.GONE);
            tvInndexEmail.setVisibility(View.GONE);
            btnSendEmail.setVisibility(View.GONE);
            relWhatsapp.setVisibility(View.GONE);
            llSeparator.setVisibility(View.GONE);
        }
        recyclerView.setAdapter(adapterMisEds);
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