package com.net.rx_retrofit_network.location.download;

import android.content.Context;

import com.net.rx_retrofit_network.location.callback.CallBack;

import io.reactivex.observers.DisposableObserver;

/**
 * Created by 7du-28 on 2018/2/1.
 */

public class DownSubscriber<ResponseBody> extends DisposableObserver<ResponseBody> {
    private CallBack callBack;
    private Context context;
    public DownSubscriber(Context context,CallBack callBack) {
        this.context=context;
        this.callBack = callBack;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (callBack != null) {
            callBack.onStart();
        }
    }


    @Override
    public void onError(Throwable e) {
        if (callBack != null) {
            callBack.onError(e);
        }
    }

    @Override
    public void onComplete() {
        if (callBack != null) {
            callBack.onCompleted();
        }
    }

    @Override
    public void onNext(ResponseBody responseBody) {
        DownLoadManager.getInstance(callBack).writeResponseBodyToDisk(context, (okhttp3.ResponseBody) responseBody);

    }
}
