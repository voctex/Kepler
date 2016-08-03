//package com.voctex.tools;
//
//import android.support.v4.app.Fragment;
//import android.view.MenuItem;
//
///**
// * Created by voctex on 2016/7/20.
// */
//
//public abstract class FragmentDelegate {
//
//    private static FragmentDelegate mCurrentInstance;
//
//    public interface Delegator {
//        public boolean isPortrait();
//    }
//
//    public static void initActivityDelegate(Delegator delegator) {
//        if (delegator.isPortrait()) {
//            mCurrentInstance = new PortraitDelegate();
//        } else {
//            mCurrentInstance = new LandscapeDelegate();
//        }
//    }
//
//    public static FragmentDelegate getInstance() {
//        if (mCurrentInstance == null) {
//            throw new IllegalStateException("FragmentDelegate must be initialized first.");
//        }
//        return mCurrentInstance;
//    }
//
//    public static void resetInstance() {
//        mCurrentInstance = null;
//    }
//
//    public abstract void onActivityCreatedFinished(Fragment fragment);
//
//    public abstract boolean onOptionsItemSelected(Fragment fragment, MenuItem item);
//
//}
