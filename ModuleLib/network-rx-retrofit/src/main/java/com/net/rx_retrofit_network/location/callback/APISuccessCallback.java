package com.net.rx_retrofit_network.location.callback;


import com.net.rx_retrofit_network.location.model.BaseResponse;

/**
 * Created by baozi on 2017/10/18.
 */

public enum APISuccessCallback implements APIExceptionCallBack {

    INSTANCE;

    @Override
    public String callback(BaseResponse baseResponse) {
        return null;
    }
}
