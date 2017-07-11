package com.voctex.fragment;

import android.widget.TextView;

import com.voctex.R;
import com.voctex.base.BaseFragment;

/**
 * Created by mac_xihao on 17/5/6.
 * test
 */
public class TextFragment extends BaseFragment {
    @Override
    protected int getLayout() {
        return R.layout.uif_text_main;
    }

    @Override
    protected void initView() {
        ((TextView) mViewGroup.findViewById(R.id.uif_text_view)).setText("position:"+getArguments().getInt("key",0));
    }
}
