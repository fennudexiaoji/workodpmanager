package com.app.base.adapter;

import android.content.Context;
import android.view.View;

import com.anthony.rvhelper.adapter.CommonAdapter;
import com.anthony.rvhelper.base.ViewHolder;
import com.app.base.R;
import com.app.base.bean.User;

/**
 * 只有一种viewType
 */

public class JustSimpleAdapter extends CommonAdapter<User> {
    public JustSimpleAdapter(Context context) {
        super(context, R.layout.layout_prj_just_title);
    }


    @Override
    protected void convert(ViewHolder holder, final User topic, int position) {
        holder.setText(R.id.tv_item_title, topic.firstname);
        holder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
    }
}
