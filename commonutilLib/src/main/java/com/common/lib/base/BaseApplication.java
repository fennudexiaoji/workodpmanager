package com.common.lib.base;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.common.lib.utils.BaseAppManager;


public class BaseApplication extends Application {
    public final static String APP_EXIT_ACTION = "APP_EXIT_ACTION";
    private static Application mContext;
    public static Application getInstance() {
        return mContext;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        mContext=this;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }


    public void exit() {
        Intent intent = new Intent(APP_EXIT_ACTION);
        ((android.app.NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).cancelAll();
        sendBroadcast(intent);
        BaseAppManager.getInstance().AppExit(this);
    }
}
