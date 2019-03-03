package com.app.base.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.app.base.R;
import com.app.base.bean.TypeSelect;

import java.util.ArrayList;
import java.util.List;


public class TypeSelectPopuAdapter extends BaseAdapter{

    private List<TypeSelect> dataList=new ArrayList<>();

    //上下文
    private Context mContext;
    //构造方法
    public TypeSelectPopuAdapter(Context context, List<TypeSelect> listBeans) {
        this.mContext = context;
        this.dataList = listBeans;

    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public TypeSelect getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //ViewHolder
        ViewHolder viewHolder=null;
        //绘制每一项的时候先判断convertView是否为空，不为空，则else里面去复用，为空，则重新赋予item布局
        if (convertView == null) {
            //new出ViewHolder ，初始化布局文件
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_item_operation_popu, null);
            viewHolder.title = (TextView) convertView.findViewById(R.id.tv_item_title);
            //调用convertView的setTag方法，将viewHolder放入进去，用于下次复用
            convertView.setTag(viewHolder);
        } else {
            //复用已经存在的item的项
            viewHolder = (ViewHolder) convertView.getTag();
        }
        TypeSelect data=dataList.get(position);
        viewHolder.title.setText(data.getTitle());
        return convertView;
    }

    class ViewHolder {
        TextView title;
    }

}
