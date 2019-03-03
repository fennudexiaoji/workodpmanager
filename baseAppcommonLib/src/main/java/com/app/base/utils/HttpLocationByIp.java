package com.app.base.utils;

import com.common.lib.utils.NetworkUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 7du-28 on 2018/1/19.
 */

public class HttpLocationByIp {

    public interface LocationListener{
        public void onSuccess(String response);
        public void onError(String error);
    }
    public void httpLocationByIp(final LocationListener listener){
        String ipAddress= NetworkUtil.getLocalIpAddress();
        String url="http://restapi.amap.com/v3/ip?ip="+ipAddress+"&key=96089bf909fc81e44d2004b1978f8c47";

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                listener.onError("定位失败");
            }

            @Override
            public void onResponse(Call call, Response response) {
                try {
                    if(response.isSuccessful()){
                        listener.onSuccess(response.body().string());
                    }else {
                        listener.onError("定位失败");
                    }
                    //Log.i("aaaaaaaaaaa",response.isSuccessful()+response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });
    }
}
