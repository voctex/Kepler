package com.voctex.activity;

import android.os.Bundle;

import com.voctex.R;
import com.voctex.base.BaseActivity;
import com.voctex.view.spanner.VtSpinner;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class SpannerActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spanner);

        VtSpinner vtSpinner = (VtSpinner) findViewById(R.id.nice_spinner);
        List<String> dataset = new LinkedList<>(Arrays.asList("One", "Two", "Three", "Four", "Five"));
        vtSpinner.attachDataSource(dataset);
    }
}
