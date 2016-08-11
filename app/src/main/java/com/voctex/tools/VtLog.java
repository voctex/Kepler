package com.voctex.tools;

import android.util.Log;

/**
 * Created by Voctex on 2016/8/11.
 */

public class VtLog {

    public static final String TAG = "tag_voctex";

    public static void i(String value) {
        Log.i(TAG, "" + value);
    }
}
