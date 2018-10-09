package com.zqf.vshop.mvp.MapMvp.MapActivities;

import android.os.Bundle;

import com.gezbox.map.overlay.MarkerLayer;
import com.zqf.vshop.mvp.MapMvp.BaseMapActivity.BasicMapActivity;

/**
 * Created by chenzhaohua on 16/7/28.
 */
public class MarkerMapActivity extends BasicMapActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        testMarker();
    }

    private void testMarker() {
        MarkerLayer markerLayer = new MarkerLayer(mapView);
        markerLayer.addMarker(30.212305, 120.217198, "蟹宝宝");
        markerLayer.addMarker(30.211415, 120.205332, "蟹宝宝2");
        markerLayer.addMarker(30.197414, 120.213057, "蟹宝宝3");
    }

}
