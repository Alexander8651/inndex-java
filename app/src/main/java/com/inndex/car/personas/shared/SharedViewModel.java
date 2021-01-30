package com.inndex.car.personas.shared;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {

    private MutableLiveData<Integer> events = new MutableLiveData<>();

    private MutableLiveData<Integer> homeEvent = new MutableLiveData<>();

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
}
