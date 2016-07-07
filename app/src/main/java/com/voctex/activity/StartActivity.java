package com.voctex.activity;

import android.content.Intent;
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

        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
