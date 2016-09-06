package com.voctex.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.voctex.MainActivity;
import com.voctex.R;
import com.voctex.base.BaseActivity;

public class StartActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(mContext, MainActivity.class));
                finish();
            }
        }, 2500);
    }

    @Override
    protected void initTheme() {
//        super.initTheme();
        setTheme(R.style.AppScreentTheme);
    }
}
