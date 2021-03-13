package com.inndex.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.inndex.R;

public class CustomInfoViewAdapter implements GoogleMap.InfoWindowAdapter {

    private final LayoutInflater mInflater;

    public CustomInfoViewAdapter(LayoutInflater inflater) {
        this.mInflater = inflater;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        final View popup = mInflater.inflate(R.layout.custom_marker_snippet, null);

        TextView tvSnippet = popup.findViewById(R.id.title);
        tvSnippet.setText(marker.getSnippet());


        return popup;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }
}
