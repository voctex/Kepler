//package com.voctex.tools;
//
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
//import android.view.View;
//
//import com.android.base.app.fragment.FragmentDelegate;
//
///**
// * Author Ztiany                   <br/>
// * Email ztiany3@gmail.com      <br/>
// * Date 2016-05-06 15:02      <br/>
// * Description：在ViewPager中实现懒加载的Fragment
// *                      changed--1: Android Support 24 把setUserVisibleHint方法放到了Attach之前调用了,只能在构造代码块中设置LazyDelegate
// */
//public abstract class LazyDelegate extends FragmentDelegate {
//
//    /**
//     * View是否准备好，如果不需要绑定view数据，只是加载网络数据，那么该字段可以去掉
//     */
//    private boolean isViewPrepared;
//    /**
//     * 滑动过来后，View是否可见
//     */
//    private boolean isViewVisible;
//
//    public LazyDelegate(Fragment fragment) {
//        super(fragment);
//    }
//
//
//
//    /**
//     * 在这里实现Fragment数据的缓加载.
//     *
//     * @param isVisibleToUser true表用户可见，false表不可见
//     */
//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        if (isVisibleToUser) {
//            isViewVisible = true;
//            onVisible();
//        } else {
//            isViewVisible = false;
//            onInvisible();
//        }
//    }
//
//
//    @Override
//    protected void onViewCreated(@Nullable View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        isViewPrepared = true;
//    }
//
//    @Override
//    protected void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        lazyLoad();
//    }
//
//    /**
//     * 滑动过来后，界面可见时执行
//     */
//    protected void onVisible() {
//        lazyLoad();
//    }
//
//    /**
//     * 滑动过来后，界面不可见时执行
//     */
//    protected void onInvisible() {
//    }
//
//    private void lazyLoad() {
//        if (isViewPrepared && isViewVisible) {
//            lazyLoadData();
//        }
//    }
//
//
//    /**
//     * 懒加载数据，并在此绑定View数据
//     */
//    protected abstract void lazyLoadData();
//
//
//}
