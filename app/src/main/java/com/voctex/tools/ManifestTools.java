package com.voctex.tools;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

/**
 * Created by voctex on 2016/7/8.
 */

public class ManifestTools {

    public static String getData(Context context,String key){
        String value=null;
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(),
                    PackageManager.GET_META_DATA);
            value = appInfo.metaData.getString(key);
            Log.d("Tag", " app key : " + value);  // Tag﹕ app key : AIzaSyBhBFOgVQclaa8p1JJeqaZHiCo2nfiyBBo
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return value;
    }


}
