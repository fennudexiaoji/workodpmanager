package com.app.base.mvp.contract;

import com.app.base.bean.User;
import com.mvp.lib.view.IUIView;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;

/**
 * Created by 7du-28 on 2018/5/21.
 */

public class ListContract {
    public interface Model {
        /**
         * 获取登陆数据
         * @return Observable<LoginData>
         */
        Observable<List<User>> showList(HashMap<String, String> treeMap);
    }
    //prensent接口省略
    public interface Presenter{
        void loadData(int pageIndex);
    }

    public interface View extends IUIView {
        void showViewData(int pageIndex,List<User> list);
        void showMessageListFailure();
    }
}
