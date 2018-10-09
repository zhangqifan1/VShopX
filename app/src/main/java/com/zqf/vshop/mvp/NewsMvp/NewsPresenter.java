package com.zqf.vshop.mvp.NewsMvp;

import com.zqf.vshop.bean.NewsBean;
import com.zqf.vshop.bean.NewsBean3;

/**
 * -----------------------------
 * Created by zqf on 2018/2/5.
 * ---------------------------
 */

public class NewsPresenter extends Const.pNewsPresenter {

    @Override
    public void setMvp() {

        mModel.requestNewsData(new Const.NewsModel.callBack() {

            @Override
            public void setNewsData(NewsBean newsBean) {
                mView.showData(newsBean);
            }
        }, mView.getCt());

    }

    @Override
    public void setMvp2() {
        mModel.requestNewsData2(new Const.NewsModel.callBack2() {

            @Override
            public void setNewsData2(NewsBean3 newsBean3) {
                mView.showData2(newsBean3);
            }
        }, mView.getCt());
    }
}
