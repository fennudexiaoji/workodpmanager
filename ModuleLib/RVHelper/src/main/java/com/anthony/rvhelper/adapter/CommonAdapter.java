package com.anthony.rvhelper.adapter;

import android.content.Context;
import android.view.LayoutInflater;

import com.anthony.rvhelper.base.ItemViewDelegate;
import com.anthony.rvhelper.base.ViewHolder;


/**
 * Created by zhy on 16/4/9.
 */
public abstract class CommonAdapter<T> extends MultiItemTypeAdapter<T> {
    protected int mLayoutId;
    protected LayoutInflater mInflater;


    public CommonAdapter(final Context context, final int layoutId) {
        super(context);
        mInflater = LayoutInflater.from(context);
        mLayoutId = layoutId;

        addItemViewDelegate(new ItemViewDelegate<T>() {
            @Override
            public int getItemViewLayoutId() {
                return layoutId;
            }

            @Override
            public boolean isForViewType(T item, int position) {
                return true;
            }

            @Override
            public void convert(ViewHolder holder, T t, int position) {
                CommonAdapter.this.convert(holder, t, position);
            }
        });
    }

    protected abstract void convert(ViewHolder holder, T t, int position);


}
