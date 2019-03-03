package app.odp.qidu.mvp.contract;

import app.odp.qidu.bean.Store;

import com.app.base.bean.User;
import com.mvp.lib.view.IUIView;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;


/**
 * Created by 7du-28 on 2018/1/31.
 */

public class LoginContract {
    public interface Model {
        /**
         * 获取登陆数据
         * @return Observable<LoginData>
         */
        Observable<List<User>> login(HashMap<String, String> treeMap);
    }
    //prensent接口省略
    public interface Presenter{
        void login();
    }

    public interface View extends IUIView {
        void showViewData(List<Store> list);
    }
}


