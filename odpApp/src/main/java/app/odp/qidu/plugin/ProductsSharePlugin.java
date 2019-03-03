package app.odp.qidu.plugin;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.base.webview.PluginAction;
import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.listener.CustomListener;

import org.apache.cordova.CordovaWebView;
import org.apache.cordova.api.CallbackContext;
import org.apache.cordova.api.CordovaInterface;
import org.apache.cordova.api.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import app.odp.qidu.R;
import app.odp.qidu.activity.CordovaHomeActivity;

import java.util.ArrayList;
import java.util.List;


public class ProductsSharePlugin extends CordovaPlugin {

    private String TAG=this.getClass().getSimpleName();
    public Activity activity;
    private CordovaHomeActivity mainCordovaActivity;
    public CordovaWebView appView;
    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);

        if(cordova.getActivity() instanceof Activity){
            this.activity= cordova.getActivity();
            this.appView=webView;
        }
        if(cordova.getActivity() instanceof CordovaHomeActivity){
            this.mainCordovaActivity= (CordovaHomeActivity) cordova.getActivity();
            this.appView=webView;
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean execute(String action, JSONArray array,final CallbackContext callbackContext) throws JSONException {
        Log.i(TAG,"action:"+action);
        Log.i(TAG,"js返回内容"+array.toString());
        if(action.equals(PluginAction.ACTION_SHOW_WHEEL_VIEW_REASON_LIST)){
            final JSONObject object= (JSONObject) array.get(0);
            this.mainCordovaActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //面积限制小数点开头
                    initCustomOptionPicker(callbackContext,object);
                }
            });
        }

        return true;
    }
    //onActivityResult为第二个Activity执行完后的回调接收方法
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent){
        switch (resultCode) { //resultCode为回传的标记，我在第二个Activity中回传的是RESULT_OK
            case Activity.RESULT_OK:
                Bundle b=intent.getExtras();  //data为第二个Activity中回传的Intent
                String str=b.getString("change01");//str即为回传的值
                break;
            default:
                break;
        }
    }

    private OptionsPickerView pvCustomOptions;
    private List<String> list;
    private void initCustomOptionPicker(final CallbackContext callbackContext, JSONObject object) {//条件选择器初始化，自定义布局
        String title = "";
        list=new ArrayList<>();
        try {
            title = object.getString("title");
            JSONArray items = object.getJSONArray("list");
            for (int i = 0; i < items.length(); i++) {
                list.add(items.getString(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        pvCustomOptions = new OptionsPickerView.Builder(mainCordovaActivity, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                String tx = list.get(options1);
                pvCustomOptions.dismiss();
                callbackContext.success(tx);
            }
        }).setLayoutRes(R.layout.pickerview_custom_options, new CustomListener() {
            @Override
            public void customLayout(View v) {
                final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                ImageView ivCancel = (ImageView) v.findViewById(R.id.iv_cancel);
                tvSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pvCustomOptions.returnData();

                    }
                });

                ivCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pvCustomOptions.dismiss();
                    }
                });


            }
        })
                //.isDialog(true)//是否弹窗
                //.setTitleText(title)
                .setContentTextSize(20)//设置滚轮文字大小
                .setDividerColor(Color.GRAY)//设置分割线的颜色
                .setSelectOptions(0, 1)//默认选中项
                //.setBgColor(Color.BLACK)
                /*.setTitleBgColor(Color.DKGRAY)
                .setTitleColor(Color.LTGRAY)
                .setCancelColor(Color.YELLOW)
                .setSubmitColor(Color.YELLOW)*/
                .setTextColorCenter(Color.BLACK)
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setBackgroundId(0x80000000) //设置外部遮罩颜色
                .build();
        TextView tvTitle=(TextView) pvCustomOptions.findViewById(R.id.tv_title);
        tvTitle.setText(title);
        pvCustomOptions.setPicker(list);//添加数据
        pvCustomOptions.show();
    }






}
