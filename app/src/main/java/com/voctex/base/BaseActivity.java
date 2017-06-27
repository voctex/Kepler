package com.voctex.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.voctex.R;
import com.voctex.tools.SPUtil;

public class BaseActivity extends AppCompatActivity {

    protected Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;

    }

    public void setActTheme(){
        setActTheme(0);
    }
    public void setActTheme(int style) {
        if (style==0) {
            if ((boolean) SPUtil.get(this, SPUtil.FileName.SYSTEM, "isNight", false)) {
                setTheme(R.style.AppDarkTheme);
            } else {
                setTheme(R.style.AppLightTheme);
            }
        }else{
            setTheme(style);
        }
        //重新设置主题需要走的方法
        recreate();
    }





}
