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

        initTheme();

    }

    protected void initTheme() {
        if ((boolean)SPUtil.get(this,SPUtil.FileName.SYSTEM,"isNight",false)) {
            setTheme(R.style.AppDarkTheme);
        } else {
            setTheme(R.style.AppLightTheme);
        }
    }





}
