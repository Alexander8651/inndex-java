package com.inndex.car.personas.to;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

public class InndexMarkerItem implements ClusterItem {

    private boolean certificada;
    private LatLng mPosition;
    private String mTitle;
    private String mSnippet;
    private int positionInList;
    private int idEstacion;

    public InndexMarkerItem(boolean certificada, LatLng mPosition, String mTitle, String mSnippet, int idEstacion, int position) {
        this.certificada = certificada;
        this.mPosition = mPosition;
        this.mTitle = mTitle;
        this.mSnippet = mSnippet;
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
        return mTitle;
    }

    @Nullable
    @Override
    public String getSnippet() {
        return mSnippet;
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

    public int getIdEstacion() {
        return idEstacion;
    }

    public void setIdEstacion(int idEstacion) {
        this.idEstacion = idEstacion;
    }
}
