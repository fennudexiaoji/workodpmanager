package com.net.rx_retrofit_network.location.rxandroid;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;


public class DownLoadFileTransformer implements ObservableTransformer{

    @Override
    public ObservableSource apply(@NonNull Observable upstream) {
        return upstream.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(5, TimeUnit.SECONDS)//重连间隔时间
                .retry(5);//重连次数;
    }


}