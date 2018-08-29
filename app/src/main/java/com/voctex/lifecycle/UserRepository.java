//package com.voctex.lifecycle;
//
//import android.arch.lifecycle.LiveData;
//import android.arch.lifecycle.MutableLiveData;
//
//import com.voctex.lifecycle.bean.UserBean;
//import com.voctex.lifecycle.database.UserDao;
//
//import java.io.IOException;
//import java.util.concurrent.Executor;
//
//import javax.inject.Inject;
//import javax.inject.Singleton;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
///**
// * @author Voctex
// * on 2018/08/17 9:38
// */
//@Singleton
//public class UserRepository {
//
//
//
//    private WebServiece mWebServiece;
//    private UserCache mUserCache;
//    private UserDao mUserDao;
//    private Executor mExecutor;
//
//    @Inject
//    public UserRepository(WebServiece webServiece, Executor executor, UserDao userDao) {
//        mWebServiece = webServiece;
//        mExecutor = executor;
//        mUserDao = userDao;
//    }
//
////    public LiveData<UserBean> getUserByDB(String userId){
////        refreshUser(userId);
////        return mUserDao.load(userId);
////    }
//
//    public void refreshUser(final String userId){
//        mExecutor.execute(new Runnable() {
//            @Override
//            public void run() {
////                boolean userExists = mUserDao.hashUser(FRESH_TIMEOUT);
////                if (!userExists){
////                    Response response = null;
////                    try {
////                        response = mWebServiece.getUser(userId).execute();
////                    } catch (IOException e) {
////                        e.printStackTrace();
////                    }
////                    mUserDao.save(response.body());
////                }
//            }
//        });
//    }
//
////    public LiveData<UserBean> getUser(String userId){
////        LiveData<UserBean> cached= ((LiveData) mUserCache.get(userId));
////        if (cached!=null){
////            return cached;
////        }
////
////        final MutableLiveData<UserBean> data = new MutableLiveData<>();
////        mUserCache.put(userId, data);
////        mWebServiece.getUser(""+userId).enqueue(new Callback<UserBean>() {
////            @Override
////            public void onResponse(Call<UserBean> call, Response<UserBean> response) {
////                data.setValue(response.body());
////            }
////
////            @Override
////            public void onFailure(Call<UserBean> call, Throwable t) {
////
////            }
////        });
////        return data;
////    }
//
//
//}
