package com.zqf.vshop.mvp.NewsMvp;

import android.content.Context;

import com.google.gson.Gson;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.CacheMode;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;
import com.zqf.vshop.bean.NewsBean;
import com.zqf.vshop.bean.NewsBean3;
import com.zqf.vshop.httputil.CallServer;
import com.zqf.vshop.httputil.HttpConst;

import util.DialogUtil;
import util.ToastUtil;

/**
 * -----------------------------
 * Created by zqf on 2018/2/5.
 * ---------------------------
 */

public class NewsModel implements Const.NewsModel {

    @Override
    public void OnDestroy() {

    }

    @Override
    public void requestNewsData(final callBack callBack, final Context context) {

//        if(newsBean!=null){
//            callBack.setNewsData(newsBean,newsBean2);
//        }
//        if(newsBean2!=null){
//            callBack.setNewsData(newsBean,newsBean2);
//        }
        Request<String> request = NoHttp.createStringRequest(HttpConst.NewsUrl, RequestMethod.GET);
        request.setCancelSign(HttpConst.NewsCancelSign)//设置取消标识
                .setCacheMode(CacheMode.REQUEST_NETWORK_FAILED_READ_CACHE);//设置缓存模式

        CallServer.getInstance().request(0, request, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {
                DialogUtil.showDialog(context);
            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                NewsBean newsBean = new Gson().fromJson(response.get(), NewsBean.class);
                callBack.setNewsData(newsBean);
            }

            @Override
            public void onFailed(int what, Response<String> response) {
                ToastUtil.showShort("大家都看到了啊,是他先没网了");
            }

            @Override
            public void onFinish(int what) {
                DialogUtil.disMissDialog();
            }
        });



    }

    @Override
    public void requestNewsData2(final callBack2 callBack2,
                                 final Context context) {
        Request<String> request2 = NoHttp.createStringRequest(HttpConst.NewsUrl_3, RequestMethod.GET);
        request2.setCancelSign(HttpConst.News2CancelSign)//设置取消标识
                .setCacheMode(CacheMode.REQUEST_NETWORK_FAILED_READ_CACHE);//设置缓存模式

        CallServer.getInstance().request(1, request2, new OnResponseListener<String>() {
            @Override
            public void onStart(int what) {
                DialogUtil.showDialog(context);
            }

            @Override
            public void onSucceed(int what, Response<String> response) {
                NewsBean3 newsBean2 = new Gson().fromJson(response.get(), NewsBean3.class);
                callBack2.setNewsData2(newsBean2);


            }

            @Override
            public void onFailed(int what, Response<String> response) {
                ToastUtil.showShort("大家都看到了啊,是他先没网了");
            }

            @Override
            public void onFinish(int what) {
                DialogUtil.disMissDialog();
            }
        });

    }

}
