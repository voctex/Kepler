package com.voctex.view.classify;

import android.content.Context;

import com.voctex.view.spanner.VtSpinner;
import com.voctex.view.spanner.VtSpinnerBaseAdapter;

import java.util.List;

/**
 * Created by voctex on 2016/07/15
 */
public class VtClassifyAdapter<T> extends VtClassifyBaseAdapter {

    private final List<T> mItems;

    public VtClassifyAdapter(Context context, List<T> items, int textColor, int backgroundSelector) {
        super(context, textColor, backgroundSelector);
        mItems = items;
    }

    @Override
    public int getCount() {
//        switch (this.currentMode) {
//            case VtSpinner.MODE_ALL:
                return mItems.size();
//            case VtSpinner.MODE_SELECT:
//                return mItems.size() - 1;
//        }
//        return mItems.size();

    }

    @Override
    public T getItem(int position) {
//        switch (this.currentMode) {
//            case VtSpinner.MODE_ALL:
//                return mItems.get(position);
//            case VtSpinner.MODE_SELECT:
//                if (position >= mSelectedIndex) {
//                    return mItems.get(position + 1);
//                } else {
//                    return mItems.get(position);
//                }
//        }

        return mItems.get(position);
    }

    @Override
    public T getItemInDataset(int position) {
        return mItems.get(position);
    }
}