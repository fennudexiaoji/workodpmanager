package com.app.base.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.anthony.rvhelper.adapter.MultiItemTypeAdapter;
import com.anthony.rvhelper.divider.DividerItemDecoration;
import com.anthony.rvhelper.divider.GridDividerItemDecoration;
import com.app.base.R;
import com.app.base.adapter.JustSimpleAdapter;
import com.app.base.base.AbsListActivity;
import com.app.base.bean.User;
import com.app.base.mvp.contract.ListContract;
import com.app.base.mvp.presenter.ListPresenterImpl;
import com.common.lib.widget.StatusViewLayout;
import com.mvp.lib.base.BaseActivity;
import com.mvp.lib.presenter.BasePresenter;

import java.util.ArrayList;
import java.util.List;
import io.reactivex.processors.PublishProcessor;

/**
 * 会话
 */

public class ListDemoActivity extends AbsListActivity<ListPresenterImpl> implements ListContract.View{
    /*@BindView(R.id.recyclerview)*/
    protected RecyclerView mRecyclerView;
    /*@BindView(R.id.refresh_layout)*/
    protected SwipeRefreshLayout mPtr;

    /*@BindView(R.id.status_view_layout)*/
    protected StatusViewLayout mStatusViewLayout;

    private MultiItemTypeAdapter adapter;


    @Override
    public void loadData(int pageIndex) {
        mPresenter.loadData(pageIndex);
    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {
        mStatusViewLayout=findView(R.id.status_view_layout);
        mPtr=findView(R.id.refresh_layout);
        mRecyclerView=findView(R.id.recyclerview);
        adapter = new JustSimpleAdapter(this);
        DividerItemDecoration decoration=new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL,R.drawable.list_divider_default);
        decoration.showLastFootViewDivider(false);
        mRecyclerView.addItemDecoration(decoration);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        /*mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        int color = Color.parseColor("#4285F4");
        mRecyclerView.addItemDecoration(new GridDividerItemDecoration(2, color));*/
        initPagingList(mRecyclerView, adapter, mPtr, mStatusViewLayout);
        TextView t3 = new TextView(getActivity());
        t3.setText("HeadView Text");
        headerAndFooterWrapper.addHeaderView(t3);
        headerAndFooterWrapper.addHeaderView(t3);

        //refreshData();
    }

    @Override
    public View initView(LayoutInflater inflater, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lib_fragment_base_recyclerview, null);
        return view;
    }

    @Override
    protected ListPresenterImpl initPresenter() {
        return new ListPresenterImpl();
    }

    @Override
    public void showViewData(int pageIndex,List<User> list) {
        onDataSuccessReceived(pageIndex,list);
        if(pageIndex==getInitPageIndex()&&list.size()>0){
            mRecyclerView.scrollToPosition(0);
        }
    }

    @Override
    public void showMessageListFailure() {
        showError(new Exception("加载错误"));
    }
}