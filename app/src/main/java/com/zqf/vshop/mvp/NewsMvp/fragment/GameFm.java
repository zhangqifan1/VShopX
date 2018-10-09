package com.zqf.vshop.mvp.NewsMvp.fragment;


import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.animation.BaseAnimation;
import com.zqf.vshop.R;
import com.zqf.vshop.adapter.BRVAdapter_News;
import com.zqf.vshop.adapter.MyDecoration;
import com.zqf.vshop.bean.MultiItem;
import com.zqf.vshop.bean.NewsBean;
import com.zqf.vshop.databinding.FragmentGameFmBinding;
import com.zqf.zhangqflibrary.base.fragment.BaseFragment;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import util.ToastUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class GameFm extends BaseFragment<FragmentGameFmBinding> {


    private BRVAdapter_News brvAdapter;
    private List<MultiItem> list = new ArrayList<>();;

    public GameFm() {
        // Required empty public constructor
    }

    public static GameFm getInstance(NewsBean newsBean) {

        Bundle args = new Bundle();
        args.putSerializable("newsBean", newsBean);
        GameFm fragment = new GameFm();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_game_fm;
    }

    @Override
    public void initView(View rootView) {
        NewsBean newsBean = (NewsBean) getArguments().getSerializable("newsBean");
        if (newsBean != null) {
            EventBus.getDefault().postSticky(newsBean);
            for (int i = 0; i < newsBean.getApk().size(); i++) {
                MultiItem multiItem = new MultiItem();
                multiItem.setList(newsBean.getApk());
                list.add(multiItem);
            }

            RecyclerView gameFmRecyclerView = mViewBinding.GameFmRecyclerView;
            StaggeredGridLayoutManager staggeredGridLayoutManager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
//            GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);

            gameFmRecyclerView.setLayoutManager(staggeredGridLayoutManager);
            brvAdapter = new BRVAdapter_News(R.layout.newsitem0,list);
            gameFmRecyclerView.setAdapter(brvAdapter);
            //旋转
            BaseAnimation baseAnimation = new BaseAnimation() {
                @Override
                public Animator[] getAnimators(View view) {
                    return new Animator[]{
                            ObjectAnimator.ofFloat(view, "rotationX", -360, 0),
                            ObjectAnimator.ofFloat(view, "rotationY", -360, 0)
                    };
                }
            };
            brvAdapter.openLoadAnimation(baseAnimation);

            gameFmRecyclerView.addItemDecoration(new MyDecoration(getContext(), LinearLayoutManager.VERTICAL));
        }

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
//        mViewBinding.GameswipRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                if(list.size()>0)
//                    list.add(0,list.get(new Random().nextInt(5)));
//                //数据重新加载完成后，提示数据发生改变，并且设置现在不在刷
//                brvAdapter.notifyDataSetChanged();
//                mViewBinding.GameswipRefreshLayout.setRefreshing(false);
//            }
//        });

        //设置支持上拉加载下拉刷新
        brvAdapter.setEnableLoadMore(true);
        brvAdapter.setUpFetchEnable(true);
        brvAdapter.setUpFetchListener(new BaseQuickAdapter.UpFetchListener() {
            @Override
            public void onUpFetch() {
                mViewBinding.GameFmRecyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        brvAdapter.addData(0,list.get(new Random().nextInt(5)));
                    }
                },1500);
            }
        });

        brvAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {

            @Override
            public void onLoadMoreRequested() {
                mViewBinding.GameFmRecyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < 3; i++) {
                            brvAdapter.addData(list.get(i));
                        }
                        brvAdapter.loadMoreComplete();
                    }
                },1500);

            }
        });
        brvAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ToastUtil.showShort("当前点击条目："+position);
            }
        });
    }

}
