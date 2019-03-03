package com.app.base.mvp.presenter;

import android.util.Log;
import android.view.View;

import com.app.base.bean.User;
import com.app.base.mvp.contract.ListContract;
import com.app.base.mvp.model.ListModelImpl;
import com.mvp.lib.presenter.BasePresenter;

import org.reactivestreams.Publisher;

import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.processors.PublishProcessor;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 7du-28 on 2018/5/21.
 */

public class ListPresenterImpl extends BasePresenter<ListContract.View> implements ListContract.Presenter{
    private ListModelImpl listModel;
    private PublishProcessor<Integer> paginator = PublishProcessor.create();
    @Override
    public void loadData(final int pageIndex) {
        HashMap<String,String> hashMap=new HashMap<>();
        Disposable disposable=listModel.showList(hashMap).subscribeOn(Schedulers.io())
                // Be notified on the main thread
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<User>>() {
                    @Override
                    public void onComplete() {
                        //textView.append(" onComplete");
                        //textView.append(AppConstant.LINE_SEPARATOR);
                        //Log.d(TAG, " onComplete");
                    }

                    @Override
                    public void onError(Throwable e) {
                        //textView.append(" onError : " + e.getMessage());
                        //textView.append(AppConstant.LINE_SEPARATOR);
                        Log.d("aaaaaaaaa", " onError : " + e.getMessage());
                        mView.showMessageListFailure();
                    }

                    @Override
                    public void onNext(List<User> value) {
                        /*textView.append(" onNext : value : " + value);
                        textView.append(AppConstant.LINE_SEPARATOR);
                        Log.d(TAG, " onNext value : " + value);*/
                        mView.showViewData(pageIndex,value);
                        Log.d("aaaaaaaaa", " onNext : " + value.get(0).firstname);

                    }
                });

        mCompositeSubscription.add(disposable);

    }

    @Override
    public void onCreate() {
        listModel=new ListModelImpl();
    }

    @Override
    public void loadData() {

    }
}
