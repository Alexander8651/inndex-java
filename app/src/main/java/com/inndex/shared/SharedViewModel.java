package com.inndex.shared;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.inndex.model.Estaciones;

public class SharedViewModel extends ViewModel {

    private final MutableLiveData<Integer> events = new MutableLiveData<>();

    private final MutableLiveData<Integer> homeEvent = new MutableLiveData<>();

    private final MutableLiveData<Estaciones> editarEstacionEvent = new MutableLiveData<>();

    public LiveData<Integer> getEvents() {
        return events;
    }

    public void setEvents(Integer event) {
        events.setValue(event);
    }

    public LiveData<Integer> getHomeEvents() {
        return homeEvent;
    }

    public void setHomeEvents(Integer event) {
        homeEvent.setValue(event);
    }

    public LiveData<Estaciones> getEditarEstacionEvent() {
        return editarEstacionEvent;
    }
    public void setEditarEdsEvent(Estaciones estacion) {
        editarEstacionEvent.setValue(estacion);
    }


}
