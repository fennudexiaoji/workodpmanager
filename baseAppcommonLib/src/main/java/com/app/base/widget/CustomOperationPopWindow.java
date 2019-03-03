package com.app.base.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;


import com.app.base.R;
import com.app.base.adapter.TypeSelectPopuAdapter;
import com.app.base.bean.TypeSelect;
import com.common.lib.global.AppUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 仿QQ空间根据位置弹出PopupWindow显示更多操作效果
 */

public class CustomOperationPopWindow extends PopupWindow {
    /*项目中使用
    *List<TypeSelect> operationTypeSelectlist=new ArrayList<>();
        operationTypeSelectlist.add(new TypeSelect("attention","关注"));
        operationTypeSelectlist.add(new TypeSelect("attention","屏蔽"));
        operationTypeSelectlist.add(new TypeSelect("attention","收藏"));
        final CustomOperationPopWindow customOperationPopWindow = new CustomOperationPopWindow(context, operationTypeSelectlist);
        customOperationPopWindow.setOnItemMyListener(new CustomOperationPopWindow.OnItemListener() {
            @Override
            public void OnItemListener(int position, TypeSelect typeSelect) {
                //此处实现列表点击所要进行的操作
            }
        });
        final View showMoreHandle=holder.getView(R.id.show_more_handle);

        showMoreHandle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customOperationPopWindow.showPopupWindow(showMoreHandle);//可以传个半透明view v_background过去根据业务需要显示隐藏
            }
        });
    * */


    private Activity context;
    private View conentView;
    private View backgroundView;
    private Animation anim_backgroundView;
    private ListView listView;
    private TypeSelectPopuAdapter selectAdapter;
    ImageView arrow_up, arrow_down;
    List<TypeSelect> typeSelectlist = new ArrayList<>();
    int[] location = new int[2];
    private OnItemListener onItemListener;
    private AdapterView.OnItemClickListener onItemClickListener;

    public interface OnItemListener {
        public void OnItemListener(int position, TypeSelect typeSelect);
    }

    ;

    public void setOnItemMyListener(OnItemListener onItemListener) {
        this.onItemListener = onItemListener;
    }

    public CustomOperationPopWindow(Activity context) {
        this.context = context;
        initView();
    }

    public CustomOperationPopWindow(Activity context, List<TypeSelect> typeSelectlist) {
        this.context = context;
        this.typeSelectlist = typeSelectlist;
        initView();
    }

    private void initView() {
        this.anim_backgroundView = AnimationUtils.loadAnimation(context, R.anim.alpha_show_anim);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.conentView = inflater.inflate(R.layout.layout_view_opertation_popupwindow, null);
        // 设置SelectPicPopupWindow的View
        this.setContentView(conentView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        // 刷新状态
        this.update();
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x00000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        this.setBackgroundDrawable(dw);

        //this.setBackgroundDrawable(null);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.operation_popwindow_anim_style_up);
        this.listView = (ListView) conentView.findViewById(R.id.lv_list);
        this.arrow_up = (ImageView) conentView.findViewById(R.id.arrow_up);
        this.arrow_down = (ImageView) conentView.findViewById(R.id.arrow_down);

        //设置适配器
        this.selectAdapter = new TypeSelectPopuAdapter(context, typeSelectlist);
        this.listView.setAdapter(selectAdapter);
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (isShowing()) {
                    dismiss();
                }
                onItemListener.OnItemListener(position, typeSelectlist.get(position));
            }
        });

        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                if (backgroundView != null) {
                    backgroundView.setVisibility(View.GONE);
                }
                setBackgroundAlpha(1);
            }
        });
    }
    private void setBackgroundAlpha(float bgAlpha){
        WindowManager.LayoutParams layoutParams = context.getWindow().getAttributes();
        layoutParams.alpha = bgAlpha; //0.0-1.0
        context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//多加这一句，问题就解决了！这句的官方文档解释是：让窗口背景后面的任何东西变暗
        context.getWindow().setAttributes(layoutParams);
    }
    //设置数据
    public void setDataSource(List<TypeSelect> typeSelectlist) {
        this.typeSelectlist = typeSelectlist;
        this.selectAdapter.notifyDataSetChanged();
        getListViewSelfHeight(listView);

    }
    private void getListViewSelfHeight(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        //健壮性的判断
        if (listAdapter == null) {
            return;
        }
        // 统计所有子项的总高度
        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            // 调用measure方法 传0是测量默认的大小
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        //通过父控件进行高度的申请
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        //listAdapter.getCount() - 1 从零开始  listView.getDividerHeight()获取子项间分隔符占用的高度
        params.height = totalHeight+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    /**
     * 没有半透明背景  显示popupWindow
     *
     * @param
     */
    public void showPopupWindow(View v) {
        v.getLocationOnScreen(location);  //获取控件的位置坐标
        //获取自身的长宽高
        conentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        if (location[1] > AppUtils.getScreenHeight() / 2 + 100) {  //MainApplication.SCREEN_H 为屏幕的高度，方法可以自己写
            this.setAnimationStyle(R.style.operation_popwindow_anim_style_up);
            arrow_up.setVisibility(View.GONE);
            arrow_down.setVisibility(View.VISIBLE);
            //this.showAtLocation(v, Gravity.NO_GRAVITY, (location[0]), location[1] - conentView.getMeasuredHeight());
            this.showAtLocation(v, Gravity.NO_GRAVITY, (location[0]), location[1] - conentView.getMeasuredHeight()-100-v.getHeight());
            setBackgroundAlpha(0.4f);
        } else {
            this.setAnimationStyle(R.style.operation_popwindow_anim_style_down);
            arrow_up.setVisibility(View.VISIBLE);
            arrow_down.setVisibility(View.GONE);
            this.showAsDropDown(v, 0, 0);
            setBackgroundAlpha(0.4f);
        }
    }

    /**
     * 携带半透明背景  显示popupWindow
     *
     * @param
     */
    public void showPopupWindow(View v, View backgroundView) {
        this.backgroundView = backgroundView;
        v.getLocationOnScreen(location);  //获取控件的位置坐标
        //获取自身的长宽高
        conentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        backgroundView.setVisibility(View.VISIBLE);
        //对view执行动画
        backgroundView.startAnimation(anim_backgroundView);
        if (location[1] > AppUtils.getScreenHeight() / 2 + 100) {  //若是控件的y轴位置大于屏幕高度的一半，向上弹出
            this.setAnimationStyle(R.style.operation_popwindow_anim_style_up);
            arrow_up.setVisibility(View.GONE);
            arrow_down.setVisibility(View.VISIBLE);
            this.showAtLocation(v, Gravity.NO_GRAVITY, (location[0]), location[1] - conentView.getMeasuredHeight());  //显示指定控件的上方
        } else {
            this.setAnimationStyle(R.style.operation_popwindow_anim_style_down);  //反之向下弹出
            arrow_up.setVisibility(View.VISIBLE);
            arrow_down.setVisibility(View.GONE);
            this.showAsDropDown(v, 0, 0);    //显示指定控件的下方
        }
    }
    /**
     * 显示popupWindow  根据特殊要求高度显示位置
     *
     * @param
     */
    public void showPopupWindow(View v, View backgroundView, int hight) {
        this.backgroundView = backgroundView;
        v.getLocationOnScreen(location);
        //获取自身的长宽高
        conentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        backgroundView.setVisibility(View.VISIBLE);
        //对view执行动画
        backgroundView.startAnimation(anim_backgroundView);
        if (location[1] > AppUtils.getScreenHeight() / 2 + 100) {
            this.setAnimationStyle(R.style.operation_popwindow_anim_style_up);
            arrow_up.setVisibility(View.GONE);
            arrow_down.setVisibility(View.VISIBLE);
            this.showAtLocation(v, Gravity.NO_GRAVITY, (location[0]), location[1] - conentView.getMeasuredHeight()-hight);
        } else {
            this.setAnimationStyle(R.style.operation_popwindow_anim_style_down);
            arrow_up.setVisibility(View.VISIBLE);
            arrow_down.setVisibility(View.GONE);
            this.showAsDropDown(v, 0, 0);
        }
    }
}
