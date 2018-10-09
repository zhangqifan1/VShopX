package com.zqf.vshop.mvp.MapMvp.MapActivities;

import android.os.Bundle;

import com.gezbox.map.overlay.PolyLineLayer;
import com.zqf.vshop.mvp.MapMvp.BaseMapActivity.BasicMapActivity;

/**
 * Created by chenzhaohua on 16/7/28.
 */
public class LineMapActivity extends BasicMapActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        testLine();
    }

    private void testLine() {
        PolyLineLayer lineLayer = new PolyLineLayer(mapView);
        lineLayer.addLine(30.208569, 120.21164, 30.211804, 120.211791);
    }
}
