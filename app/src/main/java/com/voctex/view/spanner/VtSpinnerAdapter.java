package com.voctex.view.spanner;

import android.content.Context;

import java.util.List;
/**
 * Created by voctex on 2016/07/15
 */
public class VtSpinnerAdapter<T> extends VtSpinnerBaseAdapter {

    private final List<T> mItems;
    private int currentMode;

    public VtSpinnerAdapter(Context context, int currentMode, List<T> items, int textColor, int backgroundSelector) {
        super(context, textColor, backgroundSelector);
        this.currentMode = currentMode;
        mItems = items;
    }

    @Override
    public int getCount() {
        switch (this.currentMode) {
            case VtSpinner.MODE_ALL:
                return mItems.size();
            case VtSpinner.MODE_SELECT:
                return mItems.size() - 1;
        }
        return mItems.size();

    }

    @Override
    public T getItem(int position) {
        switch (this.currentMode) {
            case VtSpinner.MODE_ALL:
                return mItems.get(position);
            case VtSpinner.MODE_SELECT:
                if (position >= mSelectedIndex) {
                    return mItems.get(position + 1);
                } else {
                    return mItems.get(position);
                }
        }

        return mItems.get(position);
    }

    @Override
    public T getItemInDataset(int position) {
        return mItems.get(position);
    }
}