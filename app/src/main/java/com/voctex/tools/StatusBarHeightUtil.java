//package com.voctex.tools;
//
//import android.app.Activity;
//import android.app.Application;
//import android.content.Context;
//import android.os.Build;
//import android.view.Window;
//import android.view.WindowManager;
//import android.widget.LinearLayout;
//
//import com.yidont.view.AlHeadCustomView;
//import com.yidont.view.AlHeadView;
//
//import org.yidont.ylife.base.YLifeApplication;
//
//import java.lang.reflect.Field;
//
///**
// * Created by SunySan on 2016/7/6.
// */
//public class StatusBarHeightUtil {
//    public static void setStatusBar(Activity mActivity) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//
//            Window window = mActivity.getWindow();
//            // 半透明状态栏
//            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
//                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
////            // 半透明导航栏
////            window.setFlags(
////                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
////                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        }
//    }
//
//
//    /**
//     * 获得状态栏的高度
//     *
//     * @param context
//     * @return
//     */
//    public static int getStatusHeight(Context context) {
//
//        int statusHeight = -1;
//        try {
//            Class clazz = Class.forName("com.android.internal.R$dimen");
//            Object object = clazz.newInstance();
//            int height = Integer.parseInt(clazz.getField("status_bar_height")
//                    .get(object).toString());
//            statusHeight = context.getResources().getDimensionPixelSize(height);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return statusHeight;
//    }
//
//    /**
//     * 获得状态栏的高度
//     *
//     * @param context
//     * @return
//     */
//    public static void getStatusHeight(AlHeadView context) {
//
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//
//
//        params.setMargins(0, YLifeApplication.statusHeight,0,0);
//        context.setLayoutParams(params);
//
//    }
//
//    /**
//     * 获得状态栏的高度
//     *
//     * @param context
//     * @return
//     */
//    public static void getStatusHeight(AlHeadCustomView context) {
//
//
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//
//
//        params.setMargins(0, YLifeApplication.statusHeight,0,0);
//        context.setLayoutParams(params);
//
//    }
//
//    /**
//     * 获取状态栏高度
//     *
//     * @param m
//     * @return
//     */
//    public static int getStatusBarH(Application m) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            Class<?> c = null;
//            Object obj = null;
//            Field field = null;
//            int x = 0, sbar = 0;
//            try {
//                c = Class.forName("com.android.internal.R$dimen");
//                obj = c.newInstance();
//                field = c.getField("status_bar_height");
//                x = Integer.parseInt(field.get(obj).toString());
//                sbar = m.getResources().getDimensionPixelSize(x);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return sbar;
//        }
//        return 0;
//    }
//}
