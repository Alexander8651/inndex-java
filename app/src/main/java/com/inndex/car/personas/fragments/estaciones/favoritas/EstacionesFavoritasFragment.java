package com.inndex.car.personas.fragments.estaciones.favoritas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.inndex.car.personas.R;

public class EstacionesFavoritasFragment extends Fragment {

    private EstacionesFavoritasViewModel mViewModel;

    public static EstacionesFavoritasFragment newInstance() {
        return new EstacionesFavoritasFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_estaciones_favoritas, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(EstacionesFavoritasViewModel.class);
        // TODO: Use the ViewModel
    }

}