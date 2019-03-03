package app.odp.qidu.activity;

import android.content.Context;
import android.content.ContextWrapper;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.app.base.webview.CordovaWebActivity;
import com.mvp.lib.presenter.BasePresenter;

import org.apache.cordova.Config;
import app.odp.qidu.R;


//https://www.cnblogs.com/lenkevin/p/7676286.html
public class CordovaHomeActivity extends CordovaWebActivity {


    public View headerLeftBack;//模拟回退一步
    public View headerLeftTvClose;//关闭页面
    //private String url_path = "http://m.dapu.com/mgallery-promotion-298.html";
    //String test_path = "file:///android_asset/test_test.html";
    ////"http://new.7dugo.com/index";
    //String test_path ="http://new.7dugo.com/index";
    String test_path = "file:///android_asset/test_test.html";

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {
        initWebview();
        if(loadUrl==null){
            return;
        }

        //默认起始页面是读取config.xml里面配置
        String startUrl = Config.getStartUrl();

        headerCenterTv = (TextView) findViewById(R.id.header_text);
        headerLeftBack=findViewById(R.id.header_left_btn_img);
        headerLeftBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mookKeyBack();
            }
        });
        //loadUrl=test_path;
        appView.loadUrl(loadUrl);//发现一个问题，如果不加载任何链接，按返回键Activity退不出来

    }

    @NonNull
    @Override
    protected View initView(@NonNull LayoutInflater inflater, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_common_title_cordovaview, null);
        //AutoUtils.auto(inflate);
        return view;
    }

    @Override
    protected BasePresenter initPresenter() {
        return new BasePresenter() {
            @Override
            public void onCreate() {

            }

            @Override
            public void loadData() {

            }
        };
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(new ContextWrapper(newBase) {
            @Override
            public Object getSystemService(String name) {
                if (Context.AUDIO_SERVICE.equals(name))
                    return getApplicationContext().getSystemService(name);
                return super.getSystemService(name);
            }
        });
    }
}
