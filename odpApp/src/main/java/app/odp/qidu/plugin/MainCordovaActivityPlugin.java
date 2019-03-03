package app.odp.qidu.plugin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.app.base.utils.IntentParams;
import com.app.base.webview.PluginAction;
import com.common.lib.bean.ActionItem;
import com.common.lib.widget.ActionSheetDialog;

import org.apache.cordova.CordovaWebView;
import org.apache.cordova.api.CallbackContext;
import org.apache.cordova.api.CordovaInterface;
import org.apache.cordova.api.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import app.odp.qidu.activity.CordovaHomeActivity;

import java.util.ArrayList;
import java.util.List;


public class MainCordovaActivityPlugin extends CordovaPlugin {
    //
    private String TAG=this.getClass().getSimpleName();
    public Activity activity;
    private CordovaHomeActivity mainCordovaActivity;
    public CordovaWebView appView;
    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
        /*if (cordova instanceof Activity) {
            this.activity = (Activity) cordova;
        }else if(cordova.getActivity() instanceof Activity){//fragment的时候
            this.activity=cordova.getActivity();
        }*/
        if(cordova.getActivity() instanceof Activity){
            this.activity= cordova.getActivity();
        }
        if(cordova.getActivity() instanceof CordovaHomeActivity){
            this.mainCordovaActivity= (CordovaHomeActivity) cordova.getActivity();
        }
        this.appView=webView;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean execute(String action, JSONArray array, final CallbackContext callbackContext) throws JSONException {
        Log.i(TAG,"action:"+action);
        Log.i("bbbbbbb","js返回内容"+array.toString());

        if(action.equals(PluginAction.ACTION_CREATE_NEW_WINDOW)) {
            JSONObject object= (JSONObject) array.get(0);
            String url=object.getString("url");
            Intent intent=new Intent(activity,CordovaHomeActivity.class);
            intent.putExtra(IntentParams.KEY_LOAD_URL,url);
            this.activity.startActivity(intent);
        }else if (action.equals(PluginAction.ACTION_RELOAD_LAST_PAGE)) {
            if(this.mainCordovaActivity!=null){
                mainCordovaActivity.reloadPage();
            }
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


    //
    private void createActionDialog(){
        List<ActionItem> list=new ArrayList<>();
        ActionItem item=new ActionItem();
        item.setItemName("确定");
        item.setItemType(1);
        list.add(item);
        new ActionSheetDialog(activity)
                .builder()
                .setTitle("确定要删除...吗")
                .setCancelable(true)
                .setCanceledOnTouchOutside(true)
                .setItemTextColor("#FA4A46")
                .showSelectIcon(false)
                .setOnSheetItemClickListener(new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onItemClick(ActionItem data, int which) {
                        if(data.getItemType()==1){
                            //确定

                        }
                    }
                }).setSheetItems(list).show();
    }
}
