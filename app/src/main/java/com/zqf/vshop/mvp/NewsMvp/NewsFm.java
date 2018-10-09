package com.zqf.vshop.mvp.NewsMvp;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.zqf.vshop.R;
import com.zqf.vshop.bean.NewsBean;
import com.zqf.vshop.bean.NewsBean3;
import com.zqf.vshop.databinding.FragmentNewsFmBinding;
import com.zqf.vshop.httputil.CallServer;
import com.zqf.vshop.httputil.HttpConst;
import com.zqf.vshop.mvp.NewsMvp.fragment.GameFm;
import com.zqf.vshop.mvp.NewsMvp.fragment.InfoFm;
import com.zqf.zhangqflibrary.base.fragment.BaseMvpFragment;

import org.greenrobot.eventbus.EventBus;

import lib.kingja.switchbutton.SwitchMultiButton;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFm extends BaseMvpFragment<NewsPresenter, NewsModel, FragmentNewsFmBinding> implements Const.NewsView {


    private String[] mTitles = {"推荐", "新闻"};
    private GameFm gameFm;
    private InfoFm infoFm;


    public NewsFm() {
        // Required empty public constructor
    }

    public static NewsFm getInstance() {
        Bundle args = new Bundle();
        NewsFm fragment = new NewsFm();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mPresenter.setMvp();
        mPresenter.setMvp2();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_news_fm;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void initView(View rootView) {
        mViewBinding.SwitchMultiButton.setText(mTitles[0], mTitles[1]);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        mViewBinding.SwitchMultiButton.setOnSwitchListener(new SwitchMultiButton.OnSwitchListener() {
            @Override
            public void onSwitch(int position, String tabText) {
                if (position == 0 && gameFm!=null) {
                    showHideFragment(gameFm,infoFm);
//                    getFragmentManager().beginTransaction().show(gameFm).hide(infoFm).addToBackStack(null).commit();
                } else if (position == 1 && infoFm!=null) {
                    showHideFragment(infoFm,gameFm);
//                    getFragmentManager().beginTransaction().show(infoFm).hide(gameFm).addToBackStack(null).commit();
                }

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        CallServer.getInstance().cancelBySign(HttpConst.NewsCancelSign);
        CallServer.getInstance().cancelBySign(HttpConst.News2CancelSign);
    }

    @Override
    public Context getCt() {
        return getActivity();
    }

    @Override
    public void showData(NewsBean newsBean) {
        if (newsBean == null) {
            mPresenter.setMvp();
        }else{
            EventBus.getDefault().postSticky(newsBean);
            gameFm = GameFm.getInstance(newsBean);
//            loadMultipleRootFragment();
            loadRootFragment(R.id.newsFrame, gameFm);
//            showHideFragment(gameFm);
//            getFragmentManager().beginTransaction().add(R.id.newsFrame, gameFm).show(gameFm).commit();
        }

    }


    @Override
    public void showData2(NewsBean3 newsBean3) {
        if (newsBean3 == null) {
            mPresenter.setMvp2();
        }else{
            infoFm = InfoFm.getInstance(newsBean3);
            loadMultipleRootFragment(R.id.newsFrame,0,infoFm);
            showHideFragment(gameFm,infoFm);
//            getFragmentManager().beginTransaction().add(R.id.newsFrame, infoFm).hide(infoFm).commit();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onBackPressedSupport() {
        return super.onBackPressedSupport();
    }


}
