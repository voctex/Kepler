package com.voctex.banner;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import com.voctex.R;


/**
 * Created by voctex on 2016/6/22.
 */
public class BannerPoint extends View {

    private Paint mPaint;
    private float mWidth;
    private float mHeight;
    private int mPageSize;
    private float mPageWidth = 0f;
    private int mPosition;
    private int mCount;
    private float mPositionOffset;
    private int mLightColor = ContextCompat.getColor(getContext(), R.color.banner_red);
    private int mDimColor = ContextCompat.getColor(getContext(), R.color.banner_gery_dark);

    public BannerPoint(Context context){
        this(context, null);
    }
    public BannerPoint(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerPoint(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        mWidth = dm.widthPixels;
        mHeight=dm.heightPixels;
        mPaint = new Paint();
        mPaint.setColor(mLightColor);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(1000f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        mPaint.
        for (int i=0;i<mCount;i++){
            canvas.drawCircle(mWidth,mHeight,10,mPaint);
        }

    }
    public void setPageScrolled(int position, float positionOffset) {
        mPosition = position;
        mPositionOffset = positionOffset;
        invalidate();
    }
    public void setPageWidth(int pageSize) {
        mPageSize = pageSize;
//        calcPageWidth();
    }
    public void setmCount(int mCount) {
        this.mCount = mCount;
    }
}
