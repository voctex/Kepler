package com.voctex;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.voctex.activity.NFCActivity;
import com.voctex.activity.ShowActivity;
import com.voctex.base.BaseActivity;
import com.voctex.view.HorizontalPointView;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HorizontalPointView sixPointView=(HorizontalPointView) findViewById(R.id.sixpoint);
        sixPointView.setPointNum(8);
        sixPointView.setDuration(1000,500);
        sixPointView.setOnClickListener(this);

        findViewById(R.id.hello_work).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.hello_work:
                startActivity(new Intent(this, ShowActivity.class));
                break;
            case R.id.sixpoint:
                startActivity(new Intent(this, NFCActivity.class));
                break;
        }
    }
}
