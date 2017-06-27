package com.voctex.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Voctex on 2016/8/22.
 */
public abstract class BaseFragment extends Fragment {
    protected Context mContext;
    protected ViewGroup mViewGroup;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mViewGroup == null) {
            mViewGroup = (ViewGroup) inflater.inflate(getLayout(), container, false);
            initView();
        } else {
            ViewGroup parent1 = (ViewGroup) this.mViewGroup.getParent();
            if (parent1 != null) {
                parent1.removeView(mViewGroup);
            }
        }
        return mViewGroup;
    }

    protected abstract int getLayout();

    protected abstract void initView();
}
