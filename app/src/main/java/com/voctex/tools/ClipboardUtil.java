package com.voctex.tools;


import android.content.Context;
import android.os.Build;

/**
 * @author Voctex 2015-11-27
 */
public class ClipboardUtil {
    /**
     * 实现文本复制功能
     * <p>
     * content
     */
    @SuppressWarnings("deprecation")
    public static void copy(Context context, String textString) {

        if (isCompatible(Build.VERSION_CODES.HONEYCOMB)) {// ----------api 在11及11以上
            // 得到剪贴板管理器
            android.content.ClipboardManager cmb = (android.content.ClipboardManager) context
                    .getSystemService(Context.CLIPBOARD_SERVICE);
            cmb.setText(textString.trim());
        } else {// --------------------------------------api 在10以下
            // 得到剪贴板管理器
            android.text.ClipboardManager cmb = (android.text.ClipboardManager) context
                    .getSystemService(Context.CLIPBOARD_SERVICE);
            cmb.setText(textString.trim());
        }
    }

    /**
     * 实现粘贴功能
     * <p>
     * context
     *
     * @return
     */
    @SuppressWarnings("deprecation")
    public static String paste(Context context) {

        if (isCompatible(Build.VERSION_CODES.HONEYCOMB)) {// ------------api 在11及11以上
            // 得到剪贴板管理器
            android.content.ClipboardManager cmb = (android.content.ClipboardManager) context
                    .getSystemService(Context.CLIPBOARD_SERVICE);
            return cmb.getText().toString().trim();
        } else {// -------------------------------------api 在10以下
            // 得到剪贴板管理器
            android.text.ClipboardManager cmb = (android.text.ClipboardManager) context
                    .getSystemService(Context.CLIPBOARD_SERVICE);
            return cmb.getText().toString().trim();
        }

    }

    private static boolean isCompatible(int apiLevel) {
        return android.os.Build.VERSION.SDK_INT >= apiLevel;
    }
}
