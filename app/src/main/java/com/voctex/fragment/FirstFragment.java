package com.voctex.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.voctex.R;
import com.voctex.activity.DataActivity;
import com.voctex.activity.ShowActivity;
import com.voctex.activity.SpannerActivity;


/**
 * A simple {@link Fragment} subclass.
 * Created by voctex on 2016/08/12.
 */
public class FirstFragment extends Fragment implements View.OnClickListener{

    private ViewGroup mViewGroup;
    private Context mContext;

    public FirstFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext=context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mViewGroup= (ViewGroup) inflater.inflate(R.layout.fragment_first, container, false);
        initView();
        return mViewGroup;
    }

    private void initView(){
        mViewGroup.findViewById(R.id.hello_work).setOnClickListener(this);
        mViewGroup.findViewById(R.id.main_data).setOnClickListener(this);
        mViewGroup.findViewById(R.id.function_spanner).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.hello_work:
                startActivity(new Intent(mContext, ShowActivity.class));
                break;
            case R.id.main_data:
                startActivity(new Intent(mContext, DataActivity.class));
//                startActivity(new Intent(this, SpannerActivity.class));
                break;
            case R.id.function_spanner:
//                startActivity(new Intent(this, DataActivity.class));
                startActivity(new Intent(mContext, SpannerActivity.class));
                break;
        }
    }
}
