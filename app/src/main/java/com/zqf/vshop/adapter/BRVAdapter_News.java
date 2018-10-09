package com.zqf.vshop.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zqf.vshop.R;
import com.zqf.vshop.bean.MultiItem;
import com.zqf.vshop.bean.NewsBean;

import java.util.List;

import util.FontUtils;
import util.Glideutils.GlideLoadImageUtil.GlideLoadImageUtil;

public class BRVAdapter_News extends BaseQuickAdapter<MultiItem, BaseViewHolder> {
    public BRVAdapter_News(int layoutResId, @Nullable List<MultiItem> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, MultiItem item) {
        FontUtils.setTypeFaceSansBold(mContext, (TextView) helper.getView(R.id.tvContent_item0));
        int layoutPosition = helper.getPosition();
        NewsBean.ApkBean apkBean = item.getList().get(layoutPosition % (item.getList().size()));

        helper.setText(R.id.tvTitle_item0, apkBean.getName());
        helper.setText(R.id.tvContent_item0, "游戏类别：" + apkBean.getCategoryName());
        helper.setText(R.id.tvVersion_item0, "版本号：" + apkBean.getVersionCode());
        ImageView icon = helper.getView(R.id.icon_item0);
        GlideLoadImageUtil.load(mContext, apkBean.getIconUrl(), icon);
    }

}