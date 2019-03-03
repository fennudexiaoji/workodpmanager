package com.app.base.utils;


import android.content.Context;

import java.security.SecureRandom;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by 7du-28 on 2018/5/22.
 */

public class RealmUtils {
    private Context context;
    private static RealmUtils mInstance;
    private String realName = "myRealm.realm";
    private RealmUtils(Context context){
        this.context = context;
     }
    public static RealmUtils getInstance(Context context){
        if (mInstance == null){
            synchronized (RealmUtils.class){
                if (mInstance == null){
                    mInstance = new RealmUtils(context);
                 }
            }
        }
        return mInstance;
     }
    /**
     * 获得Realm对象
      * @return
      */
    RealmConfiguration config;
       public Realm getRealm(){
           if(config==null){
               realName=context.getPackageName()+".realm";
               byte[] key = new byte[64];
               new SecureRandom().nextBytes(key);
               config = new RealmConfiguration.Builder()
                       .name(realName).deleteRealmIfMigrationNeeded()
                       .schemaVersion(0) //版本号
                       .encryptionKey(key)
                       .build();
           }
           return Realm.getInstance(config);
       }
}
