package com.app.base.base;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.anthony.rvhelper.adapter.MultiItemTypeAdapter;
import com.anthony.rvhelper.wrapper.HeaderAndFooterWrapper;
import com.anthony.rvhelper.wrapper.LoadMoreWrapper;
import com.app.base.R;
import com.common.lib.widget.StatusViewLayout;
import com.mvp.lib.base.BaseActivity;
import com.mvp.lib.presenter.BasePresenter;

import java.util.List;


public abstract class AbsListActivity<T extends BasePresenter> extends BaseActivity<T> {

    private StatusViewLayout mStatusViewLayout;
    private RecyclerView mRecyclerView;
    protected int pageSize=10;
    private LoadMoreWrapper mLoadMoreWrapper;
    private int mCurrentPageIndex;
    private boolean mIsCanPullUp = true;
    protected MultiItemTypeAdapter adapter;
    protected SwipeRefreshLayout mRefreshLayout;
    protected HeaderAndFooterWrapper headerAndFooterWrapper;
    protected void initPagingList(final RecyclerView recyclerView, MultiItemTypeAdapter adapter, final SwipeRefreshLayout refreshLayout, final StatusViewLayout mStatusViewLayout){
        mCurrentPageIndex = getInitPageIndex();
        this.mStatusViewLayout=mStatusViewLayout;
        this.mRefreshLayout=refreshLayout;
        this.mRecyclerView=recyclerView;
        this.adapter= adapter;
        this.mRefreshLayout.setColorSchemeResources(R.color.red, R.color.red);
        headerAndFooterWrapper=new HeaderAndFooterWrapper(this.adapter);
        mLoadMoreWrapper = new LoadMoreWrapper(getContext(), headerAndFooterWrapper);
        mLoadMoreWrapper.setOnLoadMoreListener(new LoadMoreWrapper.OnLoadMoreListener() {
            @Override
            public void onRetry() {
                loadData(mCurrentPageIndex);
            }

            @Override
            public void onLoadMore() {
                AbsListActivity.this.loadMore();
            }
        });
        mRecyclerView.setAdapter(mLoadMoreWrapper);
        if(!mIsCanPullUp){
            disEnablePullUp();
        }
        this.mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });
        this.mStatusViewLayout.setOnRetryListener(new View.OnClickListener() {//错误重试
            @Override
            public void onClick(View v) {
                mStatusViewLayout.showLoading();
                refreshData();
            }
        });
        //mStatusViewLayout.showLoading();
    }
    /**
     * 列表数据接收成功时调用（相关的实现类需要手动去调用此方法）
     *
     * @param pageIndex 当前请求的页数
     * @param items     返回的数据
     */
    protected final void onDataSuccessReceived(int pageIndex, List items) {
        mStatusViewLayout.showContent();
        mRefreshLayout.setRefreshing(false);
        if (pageIndex == getInitPageIndex() && items.size() == 0) {//无数据
            mStatusViewLayout.showEmpty("暂无数据");
            adapter.clearData();

            mLoadMoreWrapper.disableLoadMore();
        } else if (pageIndex == getInitPageIndex()) {//刷新
            adapter.refreshData(items);
            if(items.size()==pageSize){
                mCurrentPageIndex++;
                mIsCanPullUp=true;
                mLoadMoreWrapper.showLoadMore();
            }else if(items.size()<pageSize){
                mIsCanPullUp=false;
                mLoadMoreWrapper.showLoadComplete();
            }
        } else if (mIsCanPullUp&&items != null /*&& items.size() != 0*/) {//加载更多
            adapter.appendData(items);
            if(items.size()==pageSize){
                mCurrentPageIndex++;
                mIsCanPullUp=true;
                mLoadMoreWrapper.showLoadMore();
            }else {
                mIsCanPullUp=false;
                mLoadMoreWrapper.showLoadComplete();
            }
        }

        mLoadMoreWrapper.notifyDataSetChanged();

    }
    public void showError(Exception e) {
        if (mCurrentPageIndex == getInitPageIndex()) {
            mStatusViewLayout.showError(e.getMessage());
        } else {
            if(!mIsCanPullUp){
                mLoadMoreWrapper.showLoadError();
            }
            //ToastUtil.getInstance().showShortToast(e.getMessage());
        }
        mRefreshLayout.setRefreshing(false);
    }
    protected int getInitPageIndex() {
        return 1;
    }
    public final void refreshData() {
        mCurrentPageIndex = getInitPageIndex();
        loadData(mCurrentPageIndex);
    }

    public final void loadMore() {
        loadData(mCurrentPageIndex);
    }

    public abstract void loadData(int pageIndex);

    /**
     * 禁掉上拉加载更多
     */
    protected final void disEnablePullUp() {
        mLoadMoreWrapper.disableLoadMore();
    }


}
