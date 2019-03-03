package com.app.base.bean;

import android.content.Context;
import android.util.Log;

import com.app.base.utils.RealmUtils;

import java.sql.SQLException;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Created by 7du-28 on 2018/5/22.
 */

public class UserRealm extends RealmObject {

    private int id;
    private String name;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //开启事务的时候记得需要close关闭事务
    //异步插入
    public static void insertUserRealm(Context context,final UserRealm userRealm){
        RealmUtils.getInstance(context).getRealm().executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(userRealm);

            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.i("aaaaaa","新增数据成功");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Log.i("aaaaa","新增消息失败");
            }
        });
    }

    private static boolean isExist(Context context,final UserRealm userRealm){
        Realm mRealm=RealmUtils.getInstance(context).getRealm();
        UserRealm object = mRealm.where(UserRealm.class).equalTo("id", userRealm.getId()).findFirst();
        if(object==null){
            return false;
        }
        return true;
    }
    //更新数据
    public static void updateUserRealm(Context context,final UserRealm userRealm) {
        Realm mRealm=RealmUtils.getInstance(context).getRealm();
        if(isExist(context,userRealm)){
            mRealm.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    UserRealm object = realm.where(UserRealm.class).equalTo("id", userRealm.getId()).findFirst();
                    object.setName(userRealm.getName());
                }
            }, new Realm.Transaction.OnSuccess() {
                @Override
                public void onSuccess() {
                    Log.i("aaaaaa","更新成功");
                }
            }, new Realm.Transaction.OnError() {
                @Override
                public void onError(Throwable error) {
                    Log.i("aaaaaa","更新失败");
                }
            });
        }

    }

    //删除数据

    //查询所有
    public static void queryAllUserRealm(final Context context,final QueryDbCallBack<UserRealm> callBack){
        final RealmResults<UserRealm> results = RealmUtils.getInstance(context).getRealm().where(UserRealm.class).findAllAsync();
        results.addChangeListener(new RealmChangeListener<RealmResults<UserRealm>>() {
            @Override
            public void onChange(RealmResults<UserRealm> element) {
                //element = element.sort("id");
                List<UserRealm> objects = RealmUtils.getInstance(context).getRealm().copyFromRealm(element);
                callBack.querySuccess(objects, false);
                results.removeAllChangeListeners();
            }
        });
    }

    public interface QueryDbCallBack<T>{
        void querySuccess(List<T> items, boolean hasMore);
    }

    private interface SearchExistCallBack {
        void findResult(boolean isExist);
    }



        /*
    * 异步插入User
    * @param user 需要添加的用户对象
    * @throws SQLException
    */
    public static void insertUserAsync(Context context,final UserRealm user) throws SQLException {
            //一个Realm只能在同一个线程访问，在子线程中进行数据库操作必须重新获取realm对象
            Realm mRealm=RealmUtils.getInstance(context).getRealm();
            mRealm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                        realm.beginTransaction();//开启事务
                        UserRealm user1 = realm.copyToRealm(user);
                        realm.commitTransaction();
                        realm.close();//记得关闭事务
                 }
            });
            mRealm.close();//外面也不能忘记关闭事务
    }

    //同步删除
    public static void deleteAll(Context context) throws SQLException{
        Realm mRealm=RealmUtils.getInstance(context).getRealm();
        mRealm.beginTransaction();
        mRealm.where(UserRealm.class).findAll().deleteAllFromRealm();
        mRealm.commitTransaction();
        mRealm.close();
    }

    public static void deleteAllUserRealm(Context context){
        final Realm mRealm=RealmUtils.getInstance(context).getRealm();
        mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                final RealmResults<UserRealm> dogs=  mRealm.where(UserRealm.class).findAll();
                //删除所有数据
                dogs.deleteAllFromRealm();
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {

            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {

            }
        });
        /*mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                Dog dog=dogs.get(5);
                dog.deleteFromRealm();
                //删除第一个数据
                dogs.deleteFirstFromRealm();
                //删除最后一个数据
                dogs.deleteLastFromRealm();
                //删除位置为1的数据
                dogs.deleteFromRealm(1);
                //删除所有数据
                dogs.deleteAllFromRealm();
            }
        });*/
    }


    /*
    * //增序排列
        dogs=dogs.sort("id");
        //降序排列
        dogs=dogs.sort("id", Sort.DESCENDING);
User user = mRealm.where(User.class)
128                 .equalTo("name",name1)//相当于where name = name1
129                 .or()//或，连接查询条件，没有这个方式时会默认是&连接
130                 .equalTo("age",age1)//相当于where age = age1
131                 .findFirst();
132         //整体相当于select * from (表名) where name = (传入的name) or age = （传入的age）limit 1;
        */
}
