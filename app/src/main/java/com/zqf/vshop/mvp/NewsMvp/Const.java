package com.zqf.vshop.mvp.NewsMvp;

import android.content.Context;

import com.zqf.vshop.bean.NewsBean;
import com.zqf.vshop.bean.NewsBean3;
import com.zqf.zhangqflibrary.base.mvp.BaseIModel;
import com.zqf.zhangqflibrary.base.mvp.BaseIView;
import com.zqf.zhangqflibrary.base.mvp.BasePresenter;

/**
 * -----------------------------
 * Created by zqf on 2018/2/5.
 * ---------------------------
 */

public interface Const {

    interface NewsModel extends BaseIModel{
        interface callBack{
            void setNewsData(NewsBean newsBean2);
        }
        interface callBack2{
            void setNewsData2(NewsBean3 newsBean3);
        }

        void  requestNewsData(callBack callBack, Context context);

        void  requestNewsData2(callBack2 callBack2, Context context);
    }

    interface NewsView extends BaseIView{
        Context getCt();
        void showData(NewsBean newsBean);
        void showData2(NewsBean3 newsBean3);
    }

    abstract class pNewsPresenter extends BasePresenter<NewsModel,NewsView>{

        public abstract  void setMvp();
        public abstract  void setMvp2();

    }

}
