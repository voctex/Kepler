package com.voctex.contacts.js;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.webkit.JavascriptInterface;

import com.voctex.contacts.tools.ContactFilterUtil;
import com.voctex.tools.VtLog;

import org.json.JSONObject;

/**
 * Created by Voctex on 2016/8/31.
 */
public class JavaScriptObject {
    private Context mContext;
    private String style;
    private String dataJson;

    public JavaScriptObject(Context mContext) {
        this.mContext = mContext;
    }

    @JavascriptInterface
    public void getAllContact(String json) {
        try {
            style = new JSONObject(json).getString("style");

            new Thread(new Runnable() {
                @Override
                public void run() {
                    dataJson = ContactFilterUtil.getJsonData(mContext, style);
                    VtLog.i("dataJson==" + dataJson);
                    handler.sendEmptyMessage(0);
                }
            }).start();
            VtLog.i("style==" + style);
        } catch (Exception e) {
            e.printStackTrace();
            VtLog.i("json.exception==" + e.getMessage());
        }
    }

    @JavascriptInterface
    public String sendAllContact() {


        return dataJson;
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (onJsListener != null) {
                onJsListener.onJs(dataJson);
            }
        }
    };

    private OnJsListener onJsListener;

    public void setOnJsListener(OnJsListener onJsListener) {
        this.onJsListener = onJsListener;
    }

    public interface OnJsListener {
        void onJs(String value);
    }


}
