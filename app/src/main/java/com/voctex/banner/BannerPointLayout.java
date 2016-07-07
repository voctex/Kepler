package com.voctex.banner;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.voctex.R;


/**
 * Created by voctex on 20160707
 */
public class BannerPointLayout extends LinearLayout {

    private int count;

    public BannerPointLayout(Context context) {
        super(context);
    }

    public BannerPointLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(LinearLayout.HORIZONTAL);
                           setPadding(0, 0, 0, 8);
    }

    @TargetApi(11)
    public BannerPointLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setOrientation(LinearLayout.HORIZONTAL);
                           setPadding(0, 0, 0, 8);
    }

    public void setChildCount(int count) {
        Log.i("banner","数量等于="+count);
        this.count = count-2;
        removeAllViews();
        for (int i = 0; i < this.count; i++) {
            ImageView point = new ImageView(getContext());
            point.setLayoutParams(new LinearLayout.LayoutParams(-2, -2));
            point.setScaleType(ImageView.ScaleType.CENTER);
            if (i == 0) {
                point.setImageResource(R.mipmap.indicator_checked);
            } else {
                point.setImageResource(R.mipmap.indicator_unchecked);
            }
            point.setPadding(8, 0, 8, 0);
            addView(point);
        }


    }

    public void setCurrentPoint(int position) {
        for (int i = 0; i < count; i++) {
            ImageView point = (ImageView) getChildAt(i);
            point.setImageResource(R.mipmap.indicator_unchecked);
        }
        ImageView point = (ImageView) getChildAt(position-1);
        point.setImageResource(R.mipmap.indicator_checked);

    }
}
