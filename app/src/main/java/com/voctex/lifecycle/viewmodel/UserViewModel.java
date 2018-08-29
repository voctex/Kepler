//package com.voctex.lifecycle.viewmodel;
//
//import android.arch.lifecycle.LiveData;
//import android.arch.lifecycle.ViewModel;
//
//import com.voctex.lifecycle.UserRepository;
//import com.voctex.lifecycle.bean.UserBean;
//
//import javax.inject.Inject;
//
///**
// * @author Voctex
// * on 2018/08/16 17:58
// */
//public class UserViewModel extends ViewModel {
//
//    private String userId;
//
////    private UserBean mUserBean;
//
//    private LiveData<UserBean> user;
//
//    private UserRepository mUserRepository;
//
//    @Inject
//    public void setUserRepository(UserRepository userRepository) {
//        mUserRepository = userRepository;
//    }
//
//    public LiveData<UserBean> getUser() {
//        return user;
//    }
//
//
//    public void init(String userId) {
//        if (this.user!=null){
//            return ;
//        }
//        this.user = mUserRepository.getUser(userId);
//    }
//
////    public UserBean getUserBean() {
////        return mUserBean;
////    }
//}
