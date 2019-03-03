package com.net.rx_retrofit_network.location.rxandroid;



import com.net.rx_retrofit_network.location.model.BaseResponse;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import okhttp3.ResponseBody;


public class ModelFilterFactory {

    private static ObservableTransformer transformer = new SimpleTransformer();

    public static void setTransformer(ObservableTransformer transformer) {
        if (transformer == null) return;
        ModelFilterFactory.transformer = transformer;
    }
    /**
     * 将Observable<BaseResponse<T>>转化Observable<T>,并处理BaseResponse
     *
     * @return 返回过滤后的Observable.
     */
    @SuppressWarnings("unchecked")
    public static <T> Observable<T> compose(Observable<BaseResponse<T>> observable) {
        return observable.compose(transformer);
    }
    //文件下载的时候使用
    public static <T> Observable<T> composeResponseBody(Observable<ResponseBody> observable) {
        return observable.compose(new DownLoadFileTransformer());
    }
    /**
     * 将Observable<BaseResponse<T>>转化Observable<T>,并处理BaseResponse
     *
     * @return Observable<T>
     */
    @SuppressWarnings("unchecked")
    public static <T> Observable<T> filter(Observable<BaseResponse<T>> observable) {
        return observable.compose(transformer);
    }
}
