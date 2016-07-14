package com.voctex.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.voctex.R;
import com.voctex.base.BaseActivity;
import com.voctex.tools.ManifestTools;

public class DataActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        TextView dataText= (TextView) findViewById(R.id.data_text);
        dataText.setText(ManifestTools.getData(this,"testData"));
    }
}
