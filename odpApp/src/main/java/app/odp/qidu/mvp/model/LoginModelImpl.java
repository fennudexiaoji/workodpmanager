package app.odp.qidu.mvp.model;


import app.odp.qidu.mvp.contract.LoginContract;

import com.app.base.bean.User;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;


public class LoginModelImpl implements LoginContract.Model {
    @Override
    public Observable<List<User>> login(HashMap<String, String> hashMap) {
        return getAllMyFriendsObservable();
    }

    private Observable<List<User>> getAllMyFriendsObservable() {
        return Rx2AndroidNetworking.get("https://fierce-cove-29863.herokuapp.com/getAllFriends/{userId}")
                //.addQueryParameter()
                .addPathParameter("userId", "1")
                .build()
                .getObjectListObservable(User.class);
    }
}
