package com.example.assignmentarchitecture.ui.maphelper;

import android.content.Context;
import android.util.Log;
import com.example.assignmentarchitecture.domain.model.vehicles.VehicleModel;
import com.google.android.gms.maps.GoogleMap;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

public class VehicleMarkerRender extends DefaultClusterRenderer<VehicleModel> implements GoogleMap.OnCameraIdleListener {
    private GoogleMap googleMap;
    private Context mContext;

    public VehicleMarkerRender(Context context, GoogleMap map, ClusterManager<VehicleModel> clusterManager) {
        super(context, map, clusterManager);
        this.mContext = context;
        googleMap = map;
        googleMap.setOnCameraIdleListener(clusterManager);
        googleMap.setOnMarkerClickListener(clusterManager);

    }

    @Override
    protected boolean shouldRenderAsCluster(Cluster<VehicleModel> cluster) {
        return cluster.getSize() > 1;
    }

    @Override
    public void onCameraIdle() {
        Log.d("CLUSTZOOM", "zoom = " + googleMap.getCameraPosition().zoom);
    }

}