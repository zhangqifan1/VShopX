package com.zqf.vshop.httputil;


import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.download.DownloadListener;
import com.yanzhenjie.nohttp.download.DownloadQueue;
import com.yanzhenjie.nohttp.download.DownloadRequest;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.RequestQueue;

public class CallServer {

    private static CallServer sInstance;

    public static CallServer getInstance() {
        if (sInstance == null)
            synchronized (CallServer.class) {
                if (sInstance == null)
                    sInstance = new CallServer();
            }
        return sInstance;
    }

    private RequestQueue mRequestQueue;
    private DownloadQueue mDownloadQueue;


    private CallServer() {
        mRequestQueue = NoHttp.newRequestQueue(5);
        mDownloadQueue = NoHttp.newDownloadQueue(3);
    }


    public <T> void request(int what, Request<T> request, OnResponseListener<T> listener) {
        mRequestQueue.add(what, request, listener);
    }


    public void download(int what, DownloadRequest request, DownloadListener listener) {
        mDownloadQueue.add(what, request, listener);
    }
    /**
     * 取消这个sign标记的所有请求。
     *
     * @param sign 请求的取消标志。
     */
    public void cancelBySign(Object sign) {
        mRequestQueue.cancelBySign(sign);
    }

    /**
     * 取消队列中所有请求。
     */
    public void cancelAll() {
        mRequestQueue.cancelAll();
    }
}