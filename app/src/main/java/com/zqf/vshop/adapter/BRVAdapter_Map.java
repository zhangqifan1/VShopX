package com.zqf.vshop.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zqf.vshop.R;
import com.zqf.vshop.bean.MultiItem;

import java.util.List;

public class BRVAdapter_Map extends BaseQuickAdapter<MultiItem, BaseViewHolder> {

    public BRVAdapter_Map(int layoutResId, @Nullable List<MultiItem> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, MultiItem item) {
        helper.setText(R.id.tv_cardview,item.getText());
    }

}