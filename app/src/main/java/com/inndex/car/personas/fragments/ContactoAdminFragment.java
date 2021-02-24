package com.inndex.car.personas.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.inndex.car.personas.R;
import com.inndex.car.personas.databinding.FragmentContactoAdminBinding;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ContactoAdminFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContactoAdminFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    FragmentContactoAdminBinding binding;

    public ContactoAdminFragment() {
        // Required empty public constructor
    }

    public static ContactoAdminFragment newInstance(String param1, String param2) {
        ContactoAdminFragment fragment = new ContactoAdminFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contacto_admin, container, false);
        binding = FragmentContactoAdminBinding.bind(view);
        TextView tvTitulo = binding.getRoot().findViewById(R.id.tv_toolbar_titulo);
        tvTitulo.setText(requireContext().getString(R.string.administrar_mi_eds));
        ImageView imgBack = binding.getRoot().findViewById(R.id.btnBack);
        NavController navController = Navigation.findNavController(requireActivity(), R.id.fragContentApp);
        imgBack.setOnClickListener(v ->
                navController.navigateUp()
        );
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}