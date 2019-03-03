package app.odp.qidu;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.WindowManager;

import com.app.base.utils.DataUtils;
import com.common.lib.base.AbsBaseActivity;
import com.common.lib.fileutils.FileUtils;
import com.common.lib.global.CrashHandler;
import com.common.lib.global.PermissionUtils;
import com.common.lib.utils.BaseAppManager;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.lang.ref.WeakReference;

//https://github.com/LuckSiege/PictureSelector
public class LauncherActivity extends AbsBaseActivity {
    private Handler handler=new Handler();

    private WeakReference<LauncherActivity> reference=new WeakReference<>(this);
    //在library AbsBaseActivity中使用ButterKnife获取view报空指针，rebuild project才正常，尼玛

    private PermissionUtils.PermissionGrant mPermissionGrant = new PermissionUtils.PermissionGrant() {
        @Override
        public void onPermissionGranted(int requestCode) {
            switch (requestCode) {
                case PermissionUtils.CODE_MULTI_PERMISSION:
                    init();
                    break;
                default:
                    break;
            }
        }
    };
    protected void hideBottomUIMenu() {
        //隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); // 隐藏android系统的状态栏
        hideBottomUIMenu();
        super.onCreate(savedInstanceState);
        BaseAppManager.getInstance().addActivity(this);
        setContentView(R.layout.activity_laucher);
        initViewsAndEvents(savedInstanceState);
    }
    @Override
    public void finish() {
        super.finish();
        BaseAppManager.getInstance().removeActivity(this);
    }
    private void init(){

        if(FileUtils.checkFileDirectory(this)){
            FileUtils.initCacheFile(getApplicationContext());
        }

        // 异常处理，不需要处理时注释掉这两句即可！
        CrashHandler crashHandler = CrashHandler.getInstance();
        // 注册crashHandler
        crashHandler.init(getApplicationContext());

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                LauncherActivity activity=reference.get();
                if(activity==null){
                    return;
                }
                /*Intent intent = new Intent(LauncherActivity.this, CordovaHomeActivity.class);
                intent.putExtra(HttpUrl.urlKey, HttpUrl.homeUrl);
                //intent.putExtra(MainActivity.EXTRA_FINISH_ON_BACK_PRESS, true);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();*/
                Intent intent = new Intent(LauncherActivity.this, MainActivity.class);
                startActivity(intent);

            }
        }, 2000);
    }


    private void testShare(){
        IWXAPI msgApi = WXAPIFactory.createWXAPI(LauncherActivity.this, DataUtils.WECHAT_APP_ID);
        msgApi.registerApp(DataUtils.WECHAT_APP_ID);
        String text="这是一个演习";
        // 初始化一个WXTextObject对象
        WXTextObject textObj = new WXTextObject();
        textObj.text = text;

        // 用WXTextObject对象初始化一个WXMediaMessage对象
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = textObj;
        // 发送文本类型的消息时，title字段不起作用
        // msg.title = "Will be ignored";
        msg.description = text;

        // 构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("text"); // transaction字段用于唯一标识一个请求
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneTimeline ;
        req.openId = "oMJSsw6YCMS6sHHp_to0evkRbrmc";
        // 调用api接口发送数据到微信
        msgApi.sendReq(req);
        //finish();
    }
    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }
    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        PermissionUtils.requestPermissionsResult(this, requestCode, permissions, grantResults, mPermissionGrant);
    }
    protected void initViewsAndEvents(Bundle savedInstanceState) {
        //一次申请多个权限
        String[] requestPermissions = {
                PermissionUtils.PERMISSION_ACCESS_FINE_LOCATION,
                PermissionUtils.PERMISSION_ACCESS_COARSE_LOCATION,
                PermissionUtils.PERMISSION_READ_EXTERNAL_STORAGE,
                PermissionUtils.PERMISSION_WRITE_EXTERNAL_STORAGE,
                PermissionUtils.PERMISSION_CAMERA
        };
        PermissionUtils.requestMultiPermissions(null,this,requestPermissions,mPermissionGrant);

        //PermissionUtils.requestMultiPermissions(this,requestPermissions,mPermissionGrant);
        /*HostNameFragment fragment = new HostNameFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container,fragment)
                .commit();*/

    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
