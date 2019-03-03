package com.app.base.webview;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import org.apache.cordova.Config;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.api.LOG;

import java.util.Map;

/**
 * Created by Administrator on 2017/5/12.
 */

public class MyCordovaWebView extends CordovaWebView {
    public static final String ERROR_HTML = "file:///android_asset/loading_error.html";
    private String TAG = "MyCordovaWebView";
    public String lastUrl;


    public MyCordovaWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LOG.d(TAG, "MyCordovaWebView init()");
    }

    @Override
    public void loadUrl(String url) {////Config.addWhiteListEntry(url, true);//添加白名单
        super.loadUrl(url);
        if ((url.toLowerCase().startsWith("http://") || url.toLowerCase().startsWith("https://")) && !ERROR_HTML.equals(url)) {
            lastUrl = url;
        }
    }

    @Override
    public void loadUrl(String url, Map<String, String> additionalHttpHeaders) {
        this.pluginManager.init();
        super.loadUrl(url, additionalHttpHeaders);
        if ((url.toLowerCase().startsWith("http://") || url.toLowerCase().startsWith("https://")) && !ERROR_HTML.equals(url)) {
            lastUrl = url;
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    public void loadError() {
        loadUrl(ERROR_HTML);
    }

    public void reloadPage() {
        Log.i("onMessage","lastUrl--"+lastUrl);
        if (TextUtils.isEmpty(lastUrl))
            return;
        loadUrl(lastUrl);
    }
}