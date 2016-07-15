package com.voctex.view.spanner;

import android.content.Context;
import android.widget.ListAdapter;

/**
 * Created by voctex on 2016/07/15
 */
public class VtSpinnerAdapterWrapper extends VtSpinnerBaseAdapter {

    private final ListAdapter mBaseAdapter;
    private int currentMode;

    public VtSpinnerAdapterWrapper(Context context, int currentMode, ListAdapter toWrap, int textColor, int backgroundSelector) {
        super(context, textColor, backgroundSelector);
        this.currentMode = currentMode;
        mBaseAdapter = toWrap;
    }

    @Override
    public int getCount() {

        switch (this.currentMode) {
            case VtSpinner.MODE_ALL:
                return mBaseAdapter.getCount();
            case VtSpinner.MODE_SELECT:
                return mBaseAdapter.getCount() - 1;
        }
        return mBaseAdapter.getCount();
    }

    @Override
    public Object getItem(int position) {

        switch (this.currentMode) {
            case VtSpinner.MODE_ALL:
                return mBaseAdapter.getItem(position);
            case VtSpinner.MODE_SELECT:
                if (position >= mSelectedIndex) {
                    return mBaseAdapter.getItem(position + 1);
                } else {
                    return mBaseAdapter.getItem(position);
                }
        }
        return mBaseAdapter.getItem(position);
    }

    @Override
    public Object getItemInDataset(int position) {
        return mBaseAdapter.getItem(position);
    }
}