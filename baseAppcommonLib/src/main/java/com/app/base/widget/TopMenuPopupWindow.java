package com.app.base.widget;

import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;

import com.app.base.R;
import com.app.base.utils.AnimUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 7du-28 on 2018/3/21.
 */

public class TopMenuPopupWindow extends PopupWindow{
    /*
    仿微信右上角弹框
    调用
    TopMenuPopupWindow menuPopupWindow=new TopMenuPopupWindow(getActivity());
                menuPopupWindow.showPop(v);*/
    private AnimUtil animUtil;
    private View mMenuView;
    private float bgAlpha = 1f;
    private boolean bright = false;

    private static final long DURATION = 500;
    private static final float START_ALPHA = 0.7f;
    private static final float END_ALPHA = 1f;

    private Activity activity;
    public TopMenuPopupWindow(Activity context) {
        super(context);
        this.activity=context;
        animUtil = new AnimUtil();
        // 设置布局文件
        setContentView(LayoutInflater.from(context).inflate(R.layout.pop_add, null));
        mMenuView=getContentView();
        // 设置pop透明效果
        setBackgroundDrawable(new ColorDrawable(0x0000));
        // 设置pop出入动画
        setAnimationStyle(R.style.pop_add);
        // 设置pop获取焦点，如果为false点击返回按钮会退出当前Activity，如果pop中有Editor的话，focusable必须要为true
        setFocusable(true);
        // 设置pop可点击，为false点击事件无效，默认为true
        setTouchable(true);
        // 设置点击pop外侧消失，默认为false；在focusable为true时点击外侧始终消失
        setOutsideTouchable(true);
        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                toggleBright();
            }
        });


        View tv_1=mMenuView.findViewById(R.id.tv_1);
        View tv_2=mMenuView.findViewById(R.id.tv_2);
        View tv_3=mMenuView.findViewById(R.id.tv_3);
        View tv_4=mMenuView.findViewById(R.id.tv_4);
        View tv_5=mMenuView.findViewById(R.id.tv_5);
        View tv_6=mMenuView.findViewById(R.id.tv_6);
        tv_1.setOnClickListener(listener);
        tv_2.setOnClickListener(listener);
        tv_3.setOnClickListener(listener);
        tv_4.setOnClickListener(listener);
        tv_5.setOnClickListener(listener);
        tv_6.setOnClickListener(listener);

    }

    private View.OnClickListener listener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dismiss();
            int i = v.getId();
            if (i == R.id.tv_1) {
                /*Intent intent = new Intent(activity, ShowPicActivity.class);
                intent.putExtra("type", 1);
                activity.startActivity(intent);*/

            }
        }
    };

    public void showPop(View view) {
        // 相对于 + 号正下面，同时可以设置偏移量
        this.showAsDropDown(view, -100, 0);
        // 设置pop关闭监听，用于改变背景透明度
        toggleBright();
    }

    private void toggleBright() {
        // 三个参数分别为：起始值 结束值 时长，那么整个动画回调过来的值就是从0.5f--1f的
        animUtil.setValueAnimator(START_ALPHA, END_ALPHA, DURATION);
        animUtil.addUpdateListener(new AnimUtil.UpdateListener() {
            @Override
            public void progress(float progress) {
                // 此处系统会根据上述三个值，计算每次回调的值是多少，我们根据这个值来改变透明度
                bgAlpha = bright ? progress : (START_ALPHA + END_ALPHA - progress);
                backgroundAlpha(bgAlpha);
            }
        });
        animUtil.addEndListner(new AnimUtil.EndListener() {
            @Override
            public void endUpdate(Animator animator) {
                // 在一次动画结束的时候，翻转状态
                bright = !bright;
            }
        });
        animUtil.startAnimator();
    }
    /**
     * 此方法用于改变背景的透明度，从而达到“变暗”的效果
     */
    private void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        // 0.0-1.0
        lp.alpha = bgAlpha;
        activity.getWindow().setAttributes(lp);
        // everything behind this window will be dimmed.
        // 此方法用来设置浮动层，防止部分手机变暗无效
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }
}
