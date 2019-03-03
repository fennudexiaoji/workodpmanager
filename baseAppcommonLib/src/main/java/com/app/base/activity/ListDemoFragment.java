package com.app.base.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.anthony.rvhelper.adapter.MultiItemTypeAdapter;
import com.anthony.rvhelper.divider.GridDividerItemDecoration;
import com.app.base.R;
import com.app.base.adapter.JustSimpleAdapter;
import com.app.base.base.AbsListFragment;
import com.common.lib.widget.StatusViewLayout;
import com.mvp.lib.presenter.BasePresenter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.processors.PublishProcessor;

/**
 * 会话
 */

public class ListDemoFragment extends AbsListFragment {
    /*@BindView(R.id.recyclerview)*/
    protected RecyclerView mRecyclerView;
    /*@BindView(R.id.refresh_layout)*/
    protected SwipeRefreshLayout mPtr;

    /*@BindView(R.id.status_view_layout)*/
    protected StatusViewLayout mStatusViewLayout;

    private MultiItemTypeAdapter adapter;


    @Override
    public void loadData(int pageIndex) {
        Log.i("aaaa", "pageIndex:" + pageIndex);
        if (pageIndex == 2) {
            /*List<Topic> list=new ArrayList();
            for(int i=0;i<4;i++){
                list.add(new Topic("item------"+i, "1014",false));
            }
            onDataSuccessReceived(pageIndex, new ArrayList());*/
            showError(new Exception("simulate data error!!!"));
        } else {
            onDataSuccessReceived(pageIndex, new ArrayList());
        }
        //onDataSuccessReceived(pageIndex, new ArrayList());

        //showError(new Exception("simulate data error!!!"));




    }





    @Override
    public View initView(LayoutInflater inflater, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lib_fragment_base_recyclerview, null);
        return view;
    }

    @Override
    public void initViewsAndEvents(View view, @Nullable Bundle savedInstanceState) {
        adapter = new JustSimpleAdapter(mContext);
        /*DividerItemDecoration decoration=new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL,R.drawable.list_divider_default);
        decoration.showLastFootViewDivider(false);
        mRecyclerView.addItemDecoration(decoration);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));*/
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        int color = Color.parseColor("#4285F4");
        mRecyclerView.addItemDecoration(new GridDividerItemDecoration(2, color));
        initPagingList(mRecyclerView, adapter, mPtr, mStatusViewLayout);
        TextView t3 = new TextView(getActivity());
        t3.setText("HeadView Text");
        headerAndFooterWrapper.addHeaderView(t3);
        headerAndFooterWrapper.addHeaderView(t3);

        //refreshData();
    }

    @Override
    protected BasePresenter initPresenter() {
        return null;
    }

}