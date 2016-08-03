package com.voctex.activity;

import android.os.Bundle;

import com.voctex.R;
import com.voctex.base.BaseActivity;
import com.voctex.pullload.pullableview.PullableListView;

public class DataActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

//        TextView dataText= (TextView) findViewById(R.id.data_text);
//        dataText.setText(ManifestTools.getData(this,"testData"));


        PullableListView listView= (PullableListView) findViewById(R.id.content_view);


    }
}
