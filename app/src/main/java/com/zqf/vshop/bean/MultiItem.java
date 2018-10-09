package com.zqf.vshop.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

//信息实体类
public class MultiItem implements MultiItemEntity {

    public static final int Type0 = 837;
    public static final int Type1 = 957;
    public static final int Type2 = 180;

    public int TYPE;
    @Override
    public int getItemType() {
        return TYPE;
    }
    public MultiItem(int TYPE) {
        this.TYPE = TYPE;
    }
    public MultiItem() {
    }

    /*************NewsBean************/
    private List<NewsBean.ApkBean> listApkBeans;
    public List<NewsBean.ApkBean> getList() {
        return listApkBeans;
    }
    public void setList(List<NewsBean.ApkBean> listApkBeans) {
        this.listApkBeans = listApkBeans;
    }

    /*************NewsBean3************/
    private List<NewsBean3.DataBean> listDataBeans;

    public List<NewsBean3.DataBean> getListDataBeans() {
        return listDataBeans;
    }

    public void setListDataBeans(List<NewsBean3.DataBean> listDataBeans) {
        this.listDataBeans = listDataBeans;
    }

    private NewsBean.ApkBean apkBean;

    public NewsBean.ApkBean getApkBean() {
        return apkBean;
    }

    public void setApkBean(NewsBean.ApkBean apkBean) {
        this.apkBean = apkBean;
    }

    /*************CardViewItem*************/
    private String text;
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
}