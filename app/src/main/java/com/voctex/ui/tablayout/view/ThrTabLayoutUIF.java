package com.voctex.ui.tablayout.view;

import android.os.Bundle;

import com.voctex.R;
import com.voctex.base.BaseFragment;

/**
 * Created by mac_xihao on 17/6/28.
 */

public class ThrTabLayoutUIF extends BaseFragment {
    @Override
    protected int getLayout() {
        return R.layout.uif_tablayout_thr_main;
    }

    @Override
    protected void initView() {

    }

    /**
     * 静态工厂方法需要一个int型的值来初始化fragment的参数，
     * 然后返回新的fragment到调用者
     */
    public static ThrTabLayoutUIF newInstance() {
        ThrTabLayoutUIF f = new ThrTabLayoutUIF();
        Bundle args = new Bundle();
        f.setArguments(args);
        return f;
    }
}
