package com.zqf.vshop.guideAc;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;

import com.zqf.vshop.MainActivity;
import com.zqf.vshop.R;
import com.zqf.vshop.databinding.ActivityGuideBinding;
import com.zqf.zhangqflibrary.base.activity.BaseActivity;

import cn.bingoogolapple.bgabanner.BGABanner;
import cn.bingoogolapple.bgabanner.BGALocalImageSize;
import cn.pedant.SweetAlert.SweetAlertDialog;
import util.NetWorkUtils;
import util.SPUtils;

/**
 * 引导页
 * 第一次登陆  进行标识
 * 待添加  ： 对用户是否登陆进行保存
 */
public class GuideActivity extends BaseActivity<ActivityGuideBinding> {

    private CountDownTimer countDownTimer;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_guide;
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    public void initView() {

        boolean hasDone = (boolean) SPUtils.get("HasDone", false);

        if (hasDone == false) {
            SPUtils.put("HasDone", true);

            BGABanner bannerGuideContent = mViewBinding.bannerGuideContent;
            // Bitmap 的宽高在 maxWidth maxHeight 和 minWidth minHeight 之间
            BGALocalImageSize localImageSize = new BGALocalImageSize(1080, 1920, 320, 640);
            // 设置数据源
            bannerGuideContent.setData(localImageSize, ImageView.ScaleType.CENTER_CROP,
                    R.drawable.vshopbg1,
                    R.drawable.vshopbg2
            );
            bannerGuideContent.setEnterSkipViewIdAndDelegate(R.id.butEnter, R.id.butSkip, new BGABanner.GuideDelegate() {
                @Override
                public void onClickEnterOrSkip() {
                    Intent intent = new Intent(GuideActivity.this, MainActivity.class);
                    intent.putExtra("isFirst", true);
                    startActivity(intent);
                    finish();
                }
            });
        } else {
            mViewBinding.rllGuideLayout.setBackgroundResource(R.drawable.vshopbg_2_tiny);
            mViewBinding.butSkip.setVisibility(View.GONE);
            countDownTimer = new CountDownTimer(1000, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {

                    //有网
                    boolean available = NetWorkUtils.isNetWorkAvailable(GuideActivity.this);
                    if (!available) {
                        ToSettings();
                    } else {
                        Intent intent = new Intent(GuideActivity.this, MainActivity.class);
                        intent.putExtra("isFirst", false);
                        startActivity(intent);
                    }

                }
            }.start();


        }


    }

    private void ToSettings() {
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("感觉不到网络~")
                .setContentText("是否设置网络？")
                .setCancelText("不，就不给")
                .setConfirmText("给，全给你")
                .showCancelButton(true)
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        // reuse previous dialog instance, keep widget user state, reset them if you need
                        sDialog.setTitleText("Cancelled!")
                                .setContentText("真的不给么...")
                                .setCancelText("不给")
                                .setConfirmText("给")
                                .showCancelButton(true)
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        startActivity(new Intent("android.settings.WIRELESS_SETTINGS"));
                                    }
                                })
                                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        finish();
                                    }
                                })
                                .changeAlertType(SweetAlertDialog.ERROR_TYPE);

                    }
                })
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        startActivity(new Intent("android.settings.WIRELESS_SETTINGS"));
                    }
                })
                .show();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }

    }
}
