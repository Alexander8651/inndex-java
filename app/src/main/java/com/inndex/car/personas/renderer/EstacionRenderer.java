package com.inndex.car.personas.renderer;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.inndex.car.personas.R;
import com.inndex.car.personas.to.InndexMarkerItem;

public class EstacionRenderer extends DefaultClusterRenderer<InndexMarkerItem> {

    public EstacionRenderer(Context context, GoogleMap map, ClusterManager<InndexMarkerItem> clusterManager) {
        super(context, map, clusterManager);
    }

    @Override
    protected void onBeforeClusterItemRendered(@NonNull InndexMarkerItem item, @NonNull MarkerOptions markerOptions) {

        if (item.isCertificada()) {
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.eds_certificada));
        } else {
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.eds_sin_certificado));
        }
        markerOptions.title("Ir a Street View");
        //markerOptions.snippet(item.getSnippet());
    }


}
