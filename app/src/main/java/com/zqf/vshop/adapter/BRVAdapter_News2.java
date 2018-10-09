package com.zqf.vshop.adapter;

import android.annotation.SuppressLint;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lqm.roundview.RoundImageView;
import com.zqf.vshop.R;
import com.zqf.vshop.bean.MultiItem;
import com.zqf.vshop.bean.NewsBean3;

import java.util.List;

import util.Glideutils.GlideLoadImageUtil.GlideLoadImageUtil;

public class BRVAdapter_News2 extends BaseMultiItemQuickAdapter<MultiItem, BaseViewHolder> {

    public BRVAdapter_News2(List<MultiItem> data) {
        super(data);
        addItemType(MultiItem.Type0, R.layout.news2item0);
        addItemType(MultiItem.Type1, R.layout.news2item1);
        addItemType(MultiItem.Type2, R.layout.news2item2);
    }

    @SuppressLint("ResourceType")
    @Override
    protected void convert(BaseViewHolder helper, MultiItem item) {

        int layoutPosition = helper.getPosition();
        NewsBean3.DataBean dataBean = item.getListDataBeans().get(layoutPosition%(item.getListDataBeans().size()));

        switch (helper.getItemViewType()) {
            case MultiItem.Type0:

                helper.setText(R.id.tvTitle_item0_news2, dataBean.getNews_title());
                helper.setText(R.id.tvContent_item0_news2, dataBean.getNews_summary());
                RoundImageView T0_iv = helper.getView(R.id.image_item0_news2);
                GlideLoadImageUtil.load(mContext,dataBean.getPic_url(),T0_iv);

                break;
            case MultiItem.Type1:

                helper.setText(R.id.tvTitle_item1_news2, dataBean.getNews_title());
                helper.setText(R.id.tvContent_item1_news2, dataBean.getNews_summary());
                RoundImageView T1_iv = helper.getView(R.id.image0_item1_news2);
                GlideLoadImageUtil.load(mContext,dataBean.getPic_url(),T1_iv);

                break;
            case MultiItem.Type2:

                helper.setText(R.id.tvTitle_item2_news2, dataBean.getNews_title());
                helper.setText(R.id.tvContent_item2_news2, dataBean.getNews_summary());
                RoundImageView T2_iv = helper.getView(R.id.image0_item2_news2);
                GlideLoadImageUtil.load(mContext,dataBean.getPic_url(),T2_iv);

                break;
            default:
                break;
        }
    }
}