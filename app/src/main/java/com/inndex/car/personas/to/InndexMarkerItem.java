package com.inndex.car.personas.to;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

public class InndexMarkerItem implements ClusterItem {

    private boolean certificada;
    private LatLng mPosition;
    private int positionInList;
    private Long idEstacion;

    public InndexMarkerItem(boolean certificada, LatLng mPosition,  Long idEstacion, int position) {
        this.certificada = certificada;
        this.mPosition = mPosition;
        this.idEstacion = idEstacion;
        this.positionInList = position;
    }

    @NonNull
    @Override
    public LatLng getPosition() {
        return mPosition;
    }

    @Nullable
    @Override
    public String getTitle() {
        return "Ir a Street View";
    }

    @Nullable
    @Override
    public String getSnippet() {
        return null;
    }

    public boolean isCertificada() {
        return certificada;
    }

    public void setCertificada(boolean certificada) {
        this.certificada = certificada;
    }

    public int getPositionInList() {
        return positionInList;
    }

    public void setPositionInList(int positionInList) {
        this.positionInList = positionInList;
    }

    public Long getIdEstacion() {
        return idEstacion;
    }

    public void setIdEstacion(Long idEstacion) {
        this.idEstacion = idEstacion;
    }
}
