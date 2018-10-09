package com.zqf.vshop.mvp.NewsMvp.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zqf.vshop.R;
import com.zqf.vshop.adapter.BRVAdapter_News2;
import com.zqf.vshop.adapter.MyDecoration;
import com.zqf.vshop.bean.MultiItem;
import com.zqf.vshop.bean.NewsBean3;
import com.zqf.vshop.databinding.FragmentInfoFmBinding;
import com.zqf.zhangqflibrary.base.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import util.ToastUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class InfoFm extends BaseFragment<FragmentInfoFmBinding> {

    private List<MultiItem>  list = new ArrayList<>();;
    private BRVAdapter_News2 brvAdapter;

    public InfoFm() {
        // Required empty public constructor
    }

    public static InfoFm getInstance(NewsBean3 newsBean3) {
        Bundle args = new Bundle();
        args.putSerializable("newsBean2", newsBean3);
        InfoFm fragment = new InfoFm();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_info_fm;
    }

    @Override
    public void initView(View rootView) {
        NewsBean3 newsBean2 = (NewsBean3) getArguments().getSerializable("newsBean2");
        if (newsBean2 != null) {

            MultiItem multiItem = null;
            for (int i = 0; i < newsBean2.getData().size(); i++) {
                if (i % 3 == 0) {
                    multiItem = new MultiItem(MultiItem.Type0);
                } else if (i % 3 == 1) {
                    multiItem = new MultiItem(MultiItem.Type1);
                } else if (i % 3 == 2) {
                    multiItem = new MultiItem(MultiItem.Type2);
                }
                multiItem.setListDataBeans(newsBean2.getData());
                list.add(multiItem);
            }

            RecyclerView InfoFmRecyclerView = mViewBinding.InfoFmRecyclerView;
            LinearLayoutManager gridLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            InfoFmRecyclerView.setLayoutManager(gridLayoutManager);
            brvAdapter = new BRVAdapter_News2(list);
            InfoFmRecyclerView.setAdapter(brvAdapter);
            //右往左
            brvAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_RIGHT);
            //添加间隔线
            InfoFmRecyclerView.addItemDecoration(new MyDecoration(getContext(),LinearLayoutManager.VERTICAL));

        }
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        mViewBinding.InfoswipRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(list.size()>0)
                list.add(0,list.get(new Random().nextInt(5)));
//                ToastUtil.show("已刷新",100);
                //数据重新加载完成后，提示数据发生改变，并且设置现在不在刷
                brvAdapter.notifyDataSetChanged();
                mViewBinding.InfoswipRefreshLayout.setRefreshing(false);
            }
        });

        //设置支持上拉加载下拉刷新
        brvAdapter.setEnableLoadMore(true);
        brvAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {

            @Override
            public void onLoadMoreRequested() {
                mViewBinding.InfoFmRecyclerView.postDelayed(new Runnable() {
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
