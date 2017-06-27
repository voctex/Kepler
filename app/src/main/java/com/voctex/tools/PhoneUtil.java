package com.voctex.tools;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * Created by Voctex on 2016/9/18.
 */

public class PhoneUtil {

    /**
     * 获得sim的本机号码
     */
    public static String getSimPhoneNum(Context ctx) {
        TelephonyManager telephonyManager
                = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);

        return telephonyManager.getLine1Number();
    }

    /**
     * 获得渠道商
     */
    public static String getAgentId(Context context) {
        return "" + getIntData(context, "UMENG_CHANNEL");
    }

    /**
     * 获得版本号
     */
    public static int getVersionCode(Context context) {
        int versionCode = 0;

        try {
            String e = context.getPackageName();
            versionCode = context.getPackageManager().getPackageInfo(e, 0).versionCode;
        } catch (PackageManager.NameNotFoundException var3) {
            var3.printStackTrace();
        }

        return versionCode;
    }

    /**
     * 获得版本名
     */
    public static String getVersionName(Context context) {
        String verName = "";
        String pkgname = context.getPackageName();

        try {
            verName = context.getPackageManager().getPackageInfo(pkgname, 0).versionName;
        } catch (PackageManager.NameNotFoundException var4) {
            var4.printStackTrace();
        }

        return verName;
    }

    /**
     * 获得包号
     */
    public static String getPackageName(Context context) {
        return context.getPackageName();
    }


    /**
     * 检查当前网络是否可用
     * <p>
     * context
     *
     * @return
     */
    public static boolean isNetworkAvailable(Context mContext) {
        ConnectivityManager connectivity = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                // 当前网络是连接的
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    // 当前所连接的网络可用
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 通过key在manifest文件获得相应的value值
     */
    public static String getStringData(Context context, String key) {
        String value = null;
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(),
                    PackageManager.GET_META_DATA);
            value = appInfo.metaData.getString(key);
            Log.i("tag_vt", " app key : " + value);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * 通过key在manifest文件获得相应的value值
     */
    public static int getIntData(Context context, String key) {
        int value = 0;
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(),
                    PackageManager.GET_META_DATA);
            value = appInfo.metaData.getInt(key);
            Log.i("tag_vt", " app key : " + value);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return value;
    }

    public static boolean isRoaming(Context mContext) {

        /**数据漫游模式是否打开*/
        String statusCode = android.provider.Settings.System.getString(mContext.getContentResolver(),
                android.provider.Settings.System.DATA_ROAMING);
//        String dataRoamStatus = "";
        if (statusCode.equals("1")) {

//            Settings.Global.DATA_ROAMING
//            dataRoamStatus = "ENABLE";
            return true;
        } else {
//            dataRoamStatus = "DISABLE";
        }

        return false;
    }

    public static boolean isMobileConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mMobileNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);   //获取移动网络信息
            if (mMobileNetworkInfo != null) {
                return mMobileNetworkInfo.isAvailable();    //getState()方法是查询是否连接了数据网络
            }
        }
        return false;
    }
}
