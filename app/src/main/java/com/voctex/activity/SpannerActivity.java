package com.voctex.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.voctex.R;
import com.voctex.base.BaseActivity;
import com.voctex.view.VtShopStatusView;
import com.voctex.view.spanner.VtSpinner;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class SpannerActivity extends BaseActivity {

    private VtShopStatusView vtShopStatusView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spanner);

        initView();

    }

    private void initView(){
        VtSpinner vtSpinner = (VtSpinner) findViewById(R.id.nice_spinner);
        List<String> dataset = new LinkedList<>(Arrays.asList("One", "Two", "Three", "Four", "Five"));
        vtSpinner.attachDataSource(dataset);

        vtShopStatusView = (VtShopStatusView) findViewById(R.id.shop_status);

        final EditText editText= (EditText) findViewById(R.id.editText);
        Button btn= (Button) findViewById(R.id.submit_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String num=editText.getText().toString();
                vtShopStatusView.setCurrentItem(Integer.valueOf(num));
            }
        });

    }
}
