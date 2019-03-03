package com.net.rx_retrofit_network.location.rxandroid;

import com.net.rx_retrofit_network.location.model.BaseResponse;
import com.net.rx_retrofit_network.location.ExceptionHandle;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @param <T>
 */
public class SimpleTransformer<T> implements ObservableTransformer<BaseResponse<T>, T> {

    @Override
    public ObservableSource<T> apply(@NonNull Observable<BaseResponse<T>> upstream) {
        return upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .timeout(5, TimeUnit.SECONDS)//重连间隔时间
                .retry(5)//重连次数
                .map(new HandleFuc<T>()).onErrorResumeNext(new HttpResponseFunc<T>());
    }

    private class HandleFuc<T> implements Function<BaseResponse<T>, T> {

        @Override
        public T apply(BaseResponse<T> tBaseResponse) throws Exception {
            if (tBaseResponse.getCode()!=200) throw new RuntimeException(tBaseResponse.getCode() + "" + tBaseResponse.getMsg() != null ? tBaseResponse.getMsg(): "");
            return tBaseResponse.getData();
        }
    }
    private static class HttpResponseFunc<T> implements Function<Throwable, Observable<T>> {
        @Override
        public Observable<T> apply(Throwable throwable) throws Exception {
            return Observable.error(ExceptionHandle.handleException(throwable));
        }
    }
    public <T> ObservableTransformer<BaseResponse<T>, T> transformer() {

        return new ObservableTransformer() {

            @Override
            public ObservableSource apply(Observable upstream) {
                return ((Observable) upstream).map(new HandleFuc<T>()).onErrorResumeNext(new HttpResponseFunc<T>());
            }

        };
    }
}