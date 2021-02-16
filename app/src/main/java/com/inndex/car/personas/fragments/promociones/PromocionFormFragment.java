package com.inndex.car.personas.fragments.promociones;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.inndex.car.personas.R;

public class PromocionFormFragment extends Fragment {

    private PromocionFormViewModel mViewModel;

    public static PromocionFormFragment newInstance() {
        return new PromocionFormFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_promocion_form, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(PromocionFormViewModel.class);
        // TODO: Use the ViewModel
    }

}