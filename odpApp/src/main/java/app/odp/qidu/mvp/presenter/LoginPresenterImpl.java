package app.odp.qidu.mvp.presenter;


import android.util.Log;

import app.odp.qidu.mvp.contract.LoginContract;
import app.odp.qidu.mvp.model.LoginModelImpl;

import com.app.base.bean.User;
import com.mvp.lib.presenter.BasePresenter;

import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;



public class LoginPresenterImpl extends BasePresenter<LoginContract.View> implements LoginContract.Presenter{

    private LoginModelImpl loginModelImpl;
    @Override
    public void onCreate(){
        loginModelImpl = new LoginModelImpl();//创建modle实例
    }

    @Override
    public void loadData() {
        HashMap<String,String> hashMap=new HashMap<>();
        Disposable disposable=loginModelImpl.login(hashMap).subscribeOn(Schedulers.io())
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
                    }

                    @Override
                    public void onNext(List<User> value) {
                        /*textView.append(" onNext : value : " + value);
                        textView.append(AppConstant.LINE_SEPARATOR);
                        Log.d(TAG, " onNext value : " + value);*/
                        Log.d("aaaaaaaaa", " onNext : " + value.get(0).firstname);

                    }
                });

        mCompositeSubscription.add(disposable);
    }

    @Override
    public void login(){

    }

}
