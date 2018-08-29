//package com.voctex.lifecycle.database;
//
//import android.arch.lifecycle.LiveData;
//import android.arch.persistence.room.Dao;
//import android.arch.persistence.room.Insert;
//import android.arch.persistence.room.OnConflictStrategy;
//import android.arch.persistence.room.Query;
//
//import com.voctex.lifecycle.bean.UserBean;
//
//
///**
// * @author Voctex
// * on 2018/08/17 10:39
// */
//@Dao
//public interface UserDao {
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    void save(UserBean userBean);
//
//    @Query("Select * from UserBean where id=:userId")
//    LiveData<UserBean> load(String userId);
//
//}
