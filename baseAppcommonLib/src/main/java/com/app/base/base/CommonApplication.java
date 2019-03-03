package com.app.base.base;

import com.common.lib.base.BaseApplication;

import io.realm.Realm;


public class CommonApplication extends BaseApplication{

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
