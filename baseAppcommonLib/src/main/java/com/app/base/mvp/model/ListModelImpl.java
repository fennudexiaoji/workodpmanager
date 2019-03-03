package com.app.base.mvp.model;

import com.app.base.bean.User;
import com.app.base.mvp.contract.ListContract;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;

/**
 * Created by 7du-28 on 2018/5/21.
 */

public class ListModelImpl implements ListContract.Model{

    @Override
    public Observable<List<User>> showList(HashMap<String, String> treeMap) {
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
