package com.zqf.vshop.mvp.MapMvp;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.zqf.vshop.R;
import com.zqf.vshop.adapter.BRVAdapter_Map;
import com.zqf.vshop.bean.MultiItem;
import com.zqf.vshop.databinding.FragmentMapFmBinding;
import com.zqf.vshop.mvp.MapMvp.BaseMapActivity.BasicMapActivity;
import com.zqf.vshop.mvp.MapMvp.MapActivities.CircleMapActivity;
import com.zqf.vshop.mvp.MapMvp.MapActivities.DriverRouteMapActivity;
import com.zqf.vshop.mvp.MapMvp.MapActivities.GeoCoderActivity;
import com.zqf.vshop.mvp.MapMvp.MapActivities.LineMapActivity;
import com.zqf.vshop.mvp.MapMvp.MapActivities.LocationActivity;
import com.zqf.vshop.mvp.MapMvp.MapActivities.MarkerMapActivity;
import com.zqf.vshop.mvp.MapMvp.MapActivities.PoiSearchActivity;
import com.zqf.vshop.mvp.MyselfMvp.DefaultRationale;
import com.zqf.zhangqflibrary.base.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import util.ToastUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFm extends BaseFragment<FragmentMapFmBinding> {

    public static MapFm getInstance() {
        Bundle args = new Bundle();
        MapFm fragment = new MapFm();
        fragment.setArguments(args);
        return fragment;
    }

    public MapFm() {
        // Required empty public constructor
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_map_fm;
    }

    @Override
    public void initView(View rootView) {
        RecyclerView mapFmRecyclerView = mViewBinding.MapFmRecyclerView;
        List<MultiItem> list = new ArrayList<>();
        MultiItem multiItem = null;
        for (int i = 0; i < 10; i++) {
            multiItem = new MultiItem();
            if (i == 0) {
                multiItem.setText("定位文本显示");
            } else if (i == 1) {
                multiItem.setText("POI搜索");
            } else if (i == 2) {
                multiItem.setText("POI附件搜索");
            } else if (i == 3) {
                multiItem.setText("地理分析");
            } else if (i == 4) {
                multiItem.setText("反地理分析");
            } else if (i == 5) {
                multiItem.setText("基本地图");
            } else if (i == 6) {
                multiItem.setText("绘制线条");
            } else if (i == 7) {
                multiItem.setText("绘制圆形");
            } else if (i == 8) {
                multiItem.setText("绘制图标");
            } else if (i == 9) {
                multiItem.setText("路径规划");
            }
            list.add(multiItem);
        }
        BRVAdapter_Map brvAdapter_map = new BRVAdapter_Map(R.layout.cardview_item, list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mapFmRecyclerView.setLayoutManager(linearLayoutManager);
        mapFmRecyclerView.setAdapter(brvAdapter_map);

        brvAdapter_map.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent();
                boolean b = requestPermission();
                if(b==false){
                    return;
                }
                switch (position) {
                    case 0:
                        intent.setClass(getActivity(), LocationActivity.class);
                        break;
                    case 1:
                    case 2:
                        intent.setClass(getActivity(), PoiSearchActivity.class);
                        break;
                    case 3:
                    case 4:
                        intent.setClass(getActivity(), GeoCoderActivity.class);
                        break;
                    case 5:
                        intent.setClass(getActivity(), BasicMapActivity.class);
                        break;
                    case 6:
                        intent.setClass(getActivity(), LineMapActivity.class);
                        break;
                    case 7:
                        intent.setClass(getActivity(), CircleMapActivity.class);
                        break;
                    case 8:
                        intent.setClass(getActivity(), MarkerMapActivity.class);
                        break;
                    case 9:
                        intent.setClass(getActivity(), DriverRouteMapActivity.class);
                        break;
                    default:
                        break;
                }
                startActivity(intent);
            }
        });

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private boolean requestPermission(){
        // 地图权限
        final boolean[] flag = {false};

        AndPermission.with(this)
                .permission(Permission.ACCESS_FINE_LOCATION)
                .rationale(new DefaultRationale())
                .onGranted(new Action() {
                    @Override
                    public void onAction(List<String> permissions) {
                        ToastUtil.showShort("地图权限申请成功");
                        flag[0] =true;
                    }
                }).onDenied(new Action() {
            @Override
            public void onAction(List<String> permissions) {
                ToastUtil.showShort("地图权限申请失败");
                flag[0]=false;
            }
        })
                .start();
        return flag[0];
    }
}
