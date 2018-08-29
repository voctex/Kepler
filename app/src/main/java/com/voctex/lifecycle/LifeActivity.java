package com.voctex.lifecycle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.voctex.R;

public class LifeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life);
    }
}
