package app.odp.qidu;

import android.app.Application;

import com.androidnetworking.AndroidNetworking;
import com.app.base.base.CommonApplication;

/**
 * Created by 7du-28 on 2018/1/31.
 */

public class AppApplication extends CommonApplication {

    private static AppApplication context;
    public static AppApplication getInstance (){
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context=this;
        AndroidNetworking.initialize(getApplicationContext());
    }
}
