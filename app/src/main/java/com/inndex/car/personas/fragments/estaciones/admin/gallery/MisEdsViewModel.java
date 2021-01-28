package com.inndex.car.personas.fragments.estaciones.admin.gallery;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MisEdsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MisEdsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}