package com.app.base.webview;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.widget.TextView;

import com.app.base.R;
import com.app.base.utils.HttpUrl;
import com.app.base.utils.PhotoUtils;
import com.common.lib.fileutils.FileUtils;
import com.mvp.lib.base.BaseActivity;
import com.mvp.lib.presenter.BasePresenter;

import org.apache.cordova.Config;
import org.apache.cordova.CordovaChromeClient;
import org.apache.cordova.CordovaWebViewClient;
import org.apache.cordova.IceCreamCordovaWebViewClient;
import org.apache.cordova.api.CallbackContext;
import org.apache.cordova.api.CordovaInterface;
import org.apache.cordova.api.CordovaPlugin;
import org.apache.cordova.api.LOG;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *lzh
 */

public abstract class CordovaWebActivity<T extends BasePresenter> extends BaseActivity<T> implements CordovaInterface {
    private int cropRequestCode=0x898;
    protected int output_X = 480, output_Y = 480;
    private String mCM;
    private ValueCallback<Uri> mUM;
    private ValueCallback<Uri[]> mUMA;
    private final static int FCR=1;
    private String TAG="CordovaWebActivity";
    public TextView headerCenterTv;
    public MyCordovaWebView appView;
    //private File fileUri  = new File(Environment.getExternalStorageDirectory().getPath() + "/photo.jpg");
    //private File fileCropUri  = new File(Environment.getExternalStorageDirectory().getPath() + "/crop_photo.jpg");
    private File fileCropUri  =null;
    private Uri imageUri;
    private Uri cropImageUri;
    public CallbackContext callbackContext = null;
    // 存储运行时变量
    private Map<String, Object> runtimeVariable = new HashMap<String, Object>();
    // 线程池
    private final ExecutorService threadPool = Executors.newCachedThreadPool();
    private String initCallbackClass;
    //private ProgressBar progressbar;

    protected CordovaPlugin activityResultCallback = null;
    protected String loadUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        loadUrl=getIntent().getStringExtra(HttpUrl.urlKey);
        Config.init(this);
        if (savedInstanceState != null) {
            initCallbackClass = savedInstanceState.getString("callbackClass");
        }
        //StatusBarUtil.StatusBarLightMode(this);
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        super.onCreate(savedInstanceState);
    }
    /*Android中webView加载H5时,若相关需要的方法配置好后,仍然出现H5页面在不同的手机上出现文字或者图标类似换行的现象,
    在加载Webview的Activity里面重写以下的方法即可:*/
    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        if(Build.VERSION.SDK_INT>Build.VERSION_CODES.M){
            Configuration config=new Configuration();
            config.setToDefaults();
            res.updateConfiguration(config,res.getDisplayMetrics() );
        }
        return res;
    }



    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (this.activityResultCallback != null) {
            String cClass = this.activityResultCallback.getClass().getName();
            outState.putString("callbackClass", cClass);
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
        LOG.d(TAG, "Resuming the App");
        if (this.appView == null) {
            return;
        }
        this.appView.handleResume(true, true);
    }

    @Override
    /**
     * Called when the system is about to start resuming a previous activity.
     */
    public void onPause() {
        super.onPause();
        if (this.appView == null) {
            return;
        }
        this.appView.handlePause(true);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (this.appView != null) {
            appView.handleDestroy();
        } else {
            finish();
        }
    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        // 交给插件处理
        if (this.appView != null)
            this.appView.onNewIntent(intent);
    }

    protected void initWebview() {
        // 创建webview
        final MyCordovaWebView webView = (MyCordovaWebView)findViewById(R.id.appView);
        // 创建CordovaWebViewClient
        CordovaWebViewClient webViewClient;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            webViewClient = new CordovaWebViewClient(this, webView) {
                /*@Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {

                    boolean isOverride = CordovaWebActivity.this.shouldOverrideUrlLoading(view, url);
                    if (!isOverride) {
                        return super.shouldOverrideUrlLoading(view, url);
                    }
                    return isOverride;
                }*/

                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);
                    if (Build.VERSION.SDK_INT < 17) {
                        // 在 h5开始加载时动态给js注入Native对象和call方法,模拟addJavascriptInterface
                        //接口给js注入Native对象
                        //动态注入的好处就是不影响线上的h5数据,不影响ios使用
                        //在onPageStarted方法中注入是因为在h5的onload方法中有与本地交互的处理
                        //prompt()方法是js弹出的可输入的提示框
                        view.loadUrl("javascript:if(window.Native == undefined){window.Native={call:function(arg0,arg1){prompt('{\"methodName\":' + arg0 + ',\"jsonValue\":' +arg1 + '}')}}};");
                    }
                }

                @Override
                public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                    // 忽略SSL认证错误
                    handler.proceed();
                }

                @Override
                public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                    if(url.contains("cordova.js")){//加载指定.js时 引导服务端加载本地Assets/www文件夹下的cordova.js
                        try {
                            return new WebResourceResponse("application/javascript","utf-8",getBaseContext().getAssets().open("www/js/cordova.js"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if(url.contains("qiduvdaolink.js")){//加载指定.js时 引导服务端加载本地Assets/www文件夹下的cordova.js
                        try {
                            return new WebResourceResponse("application/javascript","utf-8",getBaseContext().getAssets().open("www/js/qiduvdaolink.js"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    return super.shouldInterceptRequest(view, url);
                }
            };
        } else {
            webViewClient = new IceCreamCordovaWebViewClient(this, webView) {
                /*@Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    boolean isOverride = CordovaWebActivity.this.shouldOverrideUrlLoading(view, url);
                    if (!isOverride) {
                        return super.shouldOverrideUrlLoading(view, url);
                    }
                    return isOverride;
                }*/

                /*@Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    CookieManager cm = CookieManager.getInstance();
                    String cookies = cm.getCookie(url);
                    Log.i("aaaaa","cookies=========="+cookies);
                    return super.shouldOverrideUrlLoading(view, url);
                }*/

                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    //Log.i("bbbbbbb","onPageStarted"+url);
                    super.onPageStarted(view, url, favicon);
                    if (Build.VERSION.SDK_INT < 17) {
                        // 在 h5开始加载时动态给js注入Native对象和call方法,模拟addJavascriptInterface
                        //接口给js注入Native对象
                        //动态注入的好处就是不影响线上的h5数据,不影响ios使用
                        //在onPageStarted方法中注入是因为在h5的onload方法中有与本地交互的处理
                        //prompt()方法是js弹出的可输入的提示框
                        view.loadUrl("javascript:if(window.Native == undefined){window.Native={call:function(arg0,arg1){prompt('{\"methodName\":' + arg0 + ',\"jsonValue\":' +arg1 + '}')}}};");
                    }
                }
                @Override
                public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                    // 忽略SSL认证错误
                    handler.proceed();
                }

                @Override
                public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                    super.onReceivedError(view, errorCode, description, failingUrl);
                }

                @Override
                public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                    //Log.i("vvvvvvv",url);
                    if(url.contains("cordova.js")){//加载指定.js时 引导服务端加载本地Assets/www文件夹下的cordova.js
                        try {
                            return new WebResourceResponse("application/javascript","utf-8",getBaseContext().getAssets().open("www/js/cordova.js"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if(url.contains("qiduvdaolink.js")){//加载指定.js时 引导服务端加载本地Assets/www文件夹下的cordova.js
                        try {
                            return new WebResourceResponse("application/javascript","utf-8",getBaseContext().getAssets().open("www/js/qiduvdaolink.js"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    //return new WebResourceResponse(null,null,null);//含有广告资源屏蔽请求
                    return super.shouldInterceptRequest(view, url);
                }

                @Override
                public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                    }
                    return super.shouldInterceptRequest(view, request);
                }

            };
        }

        // 创建CordovaChromeClient
        CordovaChromeClient webChromeClient = new CordovaChromeClient(this,
                webView) {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    //progressbar.setVisibility(View.GONE);
                } else {
                    /*if (progressbar.getVisibility() == View.GONE)
                        progressbar.setVisibility(View.VISIBLE);
                    progressbar.setProgress(newProgress);*/
                }
                super.onProgressChanged(view, newProgress);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                //appView.sendJavascript("java_call(\"fuck you\")");//Android 调用js示例
                //appView.loadUrl("javascript:java_call(\"fuck\")");
                //wx.fsga.gov.cn/fsweixin/jmt/jmt/form/toFtkrh.do?servCode=177&businessId=2E01DA497AE64BBD86767F6C7B972E8E
                if (!title.equals("")&& !view.getUrl().contains(title)&&!title.startsWith("http")&&!title.startsWith("file:///android_asset")&&!title.startsWith("loading")&&!title.contains(".jsp")&&!title.contains(".html")) {
                    // 获取到html页面的title后更新标题栏
                    if(headerCenterTv!=null){
                        headerCenterTv.setText(title);
                    }
                }
            }

            //For Android 3.0+
            @Override
            public void openFileChooser(ValueCallback<Uri> uploadMsg){
                mUM = uploadMsg;
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("*/*");
                CordovaWebActivity.this.startActivityForResult(Intent.createChooser(i,"File Chooser"), FCR);
            }
            // For Android 3.0+, above method not supported in some android 3+ versions, in such case we use this
            @Override
            public void openFileChooser(ValueCallback uploadMsg, String acceptType){
                mUM = uploadMsg;
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("*/*");
                CordovaWebActivity.this.startActivityForResult(
                        Intent.createChooser(i, "File Browser"),
                        FCR);
            }
            //For Android 4.1+
            @Override
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture){
                mUM = uploadMsg;
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("*/*");
                startActivityForResult(Intent.createChooser(i, "File Chooser"), CordovaWebActivity.FCR);
            }
            //For Android 5.0+
            @Override
            public boolean onShowFileChooser(
                    WebView webView, ValueCallback<Uri[]> filePathCallback,
                    FileChooserParams fileChooserParams){
                if(mUMA != null){
                    mUMA.onReceiveValue(null);
                }
                mUMA = filePathCallback;
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(takePictureIntent.resolveActivity(getPackageManager()) != null){
                    File photoFile = null;
                    try{
                        photoFile = FileUtils.createImageFile(getActivity());
                        takePictureIntent.putExtra("PhotoPath", mCM);
                    }catch(IOException ex){
                        Log.e(TAG, "Image file creation failed", ex);
                    }
                    takePictureIntent.putExtra("PhotoPath", mCM);
                    if(photoFile != null){
                        fileCropUri=photoFile;
                        mCM = "file:" + photoFile.getAbsolutePath();

                        //mCM =PhotoUtils.getPath(getActivity(),photoFile)
                        imageUri = Uri.fromFile(photoFile);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            takePictureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
                                //通过FileProvider创建一个content类型的Uri
                            String authority = getPackageName() + ".provider";
                            imageUri = FileProvider.getUriForFile(getActivity(), authority,photoFile);
                        }
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri );
                    }else{
                        takePictureIntent = null;
                    }
                }
                Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
                contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
                contentSelectionIntent.setType("*/*");
                Intent[] intentArray;
                if(takePictureIntent != null){
                    intentArray = new Intent[]{takePictureIntent};
                }else{
                    intentArray = new Intent[0];
                }

                Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
                chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
                chooserIntent.putExtra(Intent.EXTRA_TITLE, "Image Chooser");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);
                startActivityForResult(chooserIntent, FCR);
                return true;
            }

        };

        /*CookieSyncManager.createInstance(getActivity());
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.setCookie(url, cookies);//cookies是在HttpClient中获得的cookie
        CookieSyncManager.getInstance().sync();*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            CookieManager.getInstance().acceptThirdPartyCookies(webView);
        }
        CookieManager.getInstance().acceptCookie();
        CookieManager.getInstance().setAcceptCookie(true);

        String userAgent = webView.getSettings().getUserAgentString();//找到webview的useragent
        webView.getSettings().setUserAgentString(userAgent.replace("Android", "APP_WEBVIEW Android"));
        //通过浏览器下载webview的资源
        webView.setDownloadListener(new MyWebViewDownLoadListener());
        //对于不需要使用 file 协议的应用，禁用 file 协议；
        /*webView.getSettings().setAllowFileAccess(false);
        webView.getSettings().setAllowFileAccessFromFileURLs(false);*/
        webView.getSettings().setJavaScriptEnabled(true);
        //webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);//支持通过JS打开浏览器新窗口
        //支持放大缩小
        webView.getSettings().setSupportZoom(false);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        //设置放大缩小
        webView.getSettings().setBuiltInZoomControls(true);
        //隐藏放大缩小的控件
        webView.getSettings().setDisplayZoomControls(false);
        webView.removeJavascriptInterface("searchBoxJavaBridge_");
        webView.removeJavascriptInterface("accessibility");
        webView.removeJavascriptInterface("accessibilityTraversal");
        this.appView = webView;
        this.appView.setWebViewClient(webViewClient);
        this.appView.setWebChromeClient(webChromeClient);
        //this.appView.setVisibility(View.INVISIBLE);

        webViewClient.setWebView(this.appView);
        webChromeClient.setWebView(this.appView);
    }


    // Create an image file
    /*private File createImageFile() throws IOException{
        @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "img_"+timeStamp+"_";
        //File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        //return File.createTempFile(imageFileName,".jpg",storageDir);
        //new File(Environment.getExternalStorageDirectory().getPath() + "/photo.jpg");
        return new File(Environment.getExternalStorageDirectory().getPath()+"/"+ imageFileName+".jpg");
    }*/
    public void setupWebView(String startUrl, JSONObject params) {
        Map<String, String> heads = new HashMap<>();
        //heads.putAll(RetrofitClient.builderDefaultHeader());
        heads.put("client_id","android");
        appView.loadUrl(startUrl, heads);
        // 将外部传入的参数放在运行时变量里面
        this.setRuntimeVariable("PageParams", params);

    }
    /*private int getRespStatus(String url) {
        int status = -1;
        try {
            HttpHead head = new HttpHead(url);
            HttpClient client = new DefaultHttpClient();
            HttpResponse resp = client.execute(head);
            status = resp.getStatusLine().getStatusCode();
        } catch (IOException e) {}
        return status;
    }*/
    // 获取运行时变量
    public Object getRuntimeVariable(String key, Object defaultValue) {
        if (this.runtimeVariable.containsKey(key)) {
            return this.runtimeVariable.get(key);
        }
        return defaultValue;
    }

    // 设置运行时变量
    public void setRuntimeVariable(String key, Object value) {
        this.runtimeVariable.put(key, value);
    }
    @Override
    public void startActivityForResult(CordovaPlugin cordovaPlugin, Intent intent, int requestCode) {
        this.activityResultCallback = cordovaPlugin;
        super.startActivityForResult(intent, requestCode);
    }

    @Override
    public void setActivityResultCallback(CordovaPlugin cordovaPlugin) {
        this.activityResultCallback = cordovaPlugin;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent intent) {
        LOG.d(TAG, "Incoming Result");
        super.onActivityResult(requestCode, resultCode, intent);

        if(Build.VERSION.SDK_INT >= 21){
            //Uri[] results = null;
            //Check if response is positive
            if(null == mUMA){
                return;
            }
            if(requestCode!=cropRequestCode&&resultCode== Activity.RESULT_OK){
                if(requestCode == FCR){
                    if(intent == null /*|| intent.getData() == null*/){
                        //Capture Photo if no image available
                        if(mCM != null){//摄像头对应
                            //results = new Uri[]{Uri.parse(mCM)};
                            /*cropImageBySystem(Uri.parse(mCM));*/
                            cropImageUri = Uri.fromFile(fileCropUri);
                            PhotoUtils.cropImageUri(this, imageUri, cropImageUri, cropRequestCode);
                        }
                    }else{//相册对应
                        if(intent==null){
                            return;
                        }
                        if(intent.getData()==null){
                            return;
                        }
                        File photoFile = null;
                        try{
                            photoFile = FileUtils.createImageFile(getActivity());
                        }catch(IOException ex){
                            Log.e(TAG, "Image file creation failed", ex);
                        }
                        if(photoFile != null) {
                            fileCropUri = photoFile;
                        }
                        cropImageUri = Uri.fromFile(fileCropUri);
                        //storage/emulated/0/DCIM/Camera/IMG_20180101_223938.jpg
                        Uri newUri = Uri.parse(PhotoUtils.getPath(this, intent.getData()));
                        String authority = getPackageName() + ".provider";
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                            newUri = FileProvider.getUriForFile(this,authority, new File(newUri.getPath()));
                        PhotoUtils.cropImageUri(this, newUri, cropImageUri,  cropRequestCode);
                    }

                }
            }else if(requestCode==cropRequestCode&&resultCode== Activity.RESULT_OK){
                //imageUri = Uri.parse(IMAGE_FILE_LOCATION);
                if(cropImageUri==null){
                    return;
                }
                /*Uri newUri = Uri.parse(PhotoUtils.getPath(this, intent.getData()));
                String authority = getPackageName() + ".provider";
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                    newUri = FileProvider.getUriForFile(this,authority, new File(newUri.getPath()));*/
                Uri[] uris = new Uri[]{cropImageUri};
                mUMA.onReceiveValue(uris);
                mUMA = null;
                cropRequestCode=0;
            }else {
                //this.webChromeClient.getmFilePathCallback().onReceiveValue(null);
                /*在弹出选择对话框后，敲击对话框之外的任何地方，或者直接点击返回键，取消掉对话框，这个时候，再次点击网页按钮，就没有任何反应了。
                此时的webview就只能看，上面的任何操作都变的无效了，包括网页链接也是如此！为什么会出现这种情况 因为选择图片这个事件必须要有一个返回值，
                不然程序会一直处于等待状态，当你没有选定的时候你要传回一个null，不然，程序就一直阻塞，就不能进行其它操作了。*/
                mUMA.onReceiveValue(null);
                mUMA = null;
                cropRequestCode=0;
            }

        }else{
            if(null == mUMA){
                return;
            }
            /*if(requestCode == FCR){
                if(null == mUM) return;
                Uri result = intent == null || resultCode != RESULT_OK ? null : intent.getData();
                mUM.onReceiveValue(result);
                mUM = null;
            }*/
            if(requestCode!=cropRequestCode&&requestCode == FCR){

                if(null == mUM) return;
                Uri result = intent == null || resultCode != RESULT_OK ? null : intent.getData();
                if(result==null){
                    return;
                }
                //cropImageBySystem(result);

                File photoFile = null;
                try{
                    photoFile = FileUtils.createImageFile(getActivity());
                }catch(IOException ex){
                    Log.e(TAG, "Image file creation failed", ex);
                }
                if(photoFile != null) {
                    fileCropUri = photoFile;
                }
                cropImageUri = Uri.fromFile(fileCropUri);
                //storage/emulated/0/DCIM/Camera/IMG_20180101_223938.jpg
                Uri newUri = Uri.parse(PhotoUtils.getPath(this, intent.getData()));
                /*String authority = getPackageName() + ".provider";
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                    newUri = FileProvider.getUriForFile(this,authority, new File(newUri.getPath()));*/
                PhotoUtils.cropImageUri(this, newUri, cropImageUri, cropRequestCode);
            }else if(requestCode==cropRequestCode&&resultCode== Activity.RESULT_OK){
                if(intent==null){
                    return;
                }
                Uri uri=intent.getData();
                if(uri==null){
                    return;
                }
                mUM.onReceiveValue(uri);
                mUM = null;
                cropRequestCode=0;
            }else {
                mUM.onReceiveValue(null);
                mUM = null;
            }

        }






        CordovaPlugin callback = this.activityResultCallback;
        if (callback == null) {
            if (initCallbackClass != null) {
                this.activityResultCallback = appView.pluginManager
                        .getPlugin(initCallbackClass);
                callback = activityResultCallback;
                callback.onActivityResult(requestCode, resultCode, intent);
            }
        } else {
            callback.onActivityResult(requestCode, resultCode, intent);
        }
    }



    public void postMessage(String id, Object data) {
        if (this.appView != null) {
            this.appView.postMessage(id, data);
        }
    }

    @Override
    public Object onMessage(String id, Object data) {//这里接收，postMessage发送
        Log.i("onMessage","id--"+id+"data---"+data);
        if ("onPageFinished".equals(id)) {
            // 给appView加上淡入的动画效果
            /*Animation viewIn = AnimationUtils.loadAnimation(this,
                    R.anim.view_in);
            this.appView.setAnimation(viewIn);
            this.appView.setVisibility(View.VISIBLE);*/
            //this.hiddenDialogLoading();
        } else if ("onReceivedError".equals(id)) {

            JSONObject d = (JSONObject) data;
            try {
                this.onReceivedError(d.getInt("errorCode"),
                        d.getString("description"), d.getString("url"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if ("exit".equals(id)) {
            finish();
        }
        return null;
    }
    // 接收到错误的地址后的处理
    public void onReceivedError(final int errorCode, final String description,
                                final String failingUrl) {
        runOnUiThread(new Runnable() {
            public void run() {
                /*{"errorCode":-2,"description":"net::ERR_NAME_NOT_RESOLVED","url":"http:\/\/wofei_wap.7dugo.com\/"}*/
                if(errorCode==-2){
                    appView.loadError();
                }
//				try {
//					AlertDialog.Builder dlg = new AlertDialog.Builder(me);
//					dlg.setMessage("访问出错了,请稍后再试!");
//					dlg.setTitle("温馨提示");
//					dlg.setCancelable(false);
//					dlg.setPositiveButton("确定",
//							new AlertDialog.OnClickListener() {
//						public void onClick(DialogInterface dialog,
//								int which) {
//							dialog.dismiss();
//							endActivity();
//						}
//					});
//					dlg.create();
//					dlg.show();
//				} catch (Exception e) {
//					e.printStackTrace();
//					endActivity();
//				}
                //appView.loadError();
            }
        });
    }
    @Override
    public ExecutorService getThreadPool() {
        return threadPool;
    }


    private class MyWebViewDownLoadListener implements DownloadListener {
        @Override
        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype,
                                    long contentLength) {
            Log.i("tag", "url=" + url);
            Log.i("tag", "userAgent=" + userAgent);
            Log.i("tag", "contentDisposition=" + contentDisposition);
            Log.i("tag", "mimetype=" + mimetype);
            Log.i("tag", "contentLength=" + contentLength);
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    }



    // 模拟点击回退键盘
    public void mookKeyBack() {
        new Thread() {
            public void run() {
                try {
                    Instrumentation inst = new Instrumentation();
                    inst.sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (appView.isCustomViewShowing() && (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_MENU)) {
            return appView.onKeyUp(keyCode, event);
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_MENU) {
            /*if (isIndividuationLB) {
                appView.post(new Runnable() {
                    @Override
                    public void run() {
                        appView.loadUrl("javascript:individuationAppBack();");
                    }
                });
                return false;
            } else {
                if (appView.canGoBack()) {
                    appView.goBack();
                    return true;
                }
                return super.onKeyDown(keyCode, event);
            }*/
            if (appView.canGoBack()) {
                appView.goBack();
                return true;
            }
            return super.onKeyDown(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
    }

    public void reloadPage(){
        if(appView!=null){
            appView.reloadPage();
        }
    }










    //阿里云HTTPDNS
    //https://github.com/aliyun/alicloud-android-demo/blob/master/httpdns_android_demo/src/main/java/alibaba/httpdns_android_demo/WebviewActivity.java
}
