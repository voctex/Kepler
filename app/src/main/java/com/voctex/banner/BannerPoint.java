package com.voctex.banner;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import com.voctex.R;


/**
 * Created by voctex on 20160707
 */
public class BannerPoint extends View {

    private Paint mPaint;
    private int mWidth;
    private int mHeight;
    private int[] displays = new int[2];
    private int mPageSize;
    private float mPageWidth = 0f;
    private int mPosition;
    private int mCount;
    private float mPositionOffset;
    private int mLightColor = ContextCompat.getColor(getContext(), R.color.banner_red);
    private int mDimColor = ContextCompat.getColor(getContext(), R.color.banner_gery_dark);

    public BannerPoint(Context context) {
        this(context, null);
        initView();
    }

    public BannerPoint(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        initView();
    }

    public BannerPoint(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @TargetApi(21)
    public BannerPoint(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    private void initView() {
        getCurrentDisplaySize();

        mPaint = new Paint();
        mPaint.setColor(mLightColor);
        mPaint.setAntiAlias(true);//true为抗锯齿
//        mPaint.setStrokeWidth(1000f);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float partW = mWidth / mCount;
        float radius = mWidth / mCount / 2;
        for (int i = 0; i < mCount; i++) {
            if (i == mPosition) {
                mPaint.setColor(Color.GREEN);
                canvas.drawCircle(partW * i + partW / 2, mHeight / 2, radius, mPaint);
            } else {
                mPaint.setColor(Color.GRAY);
                canvas.drawCircle(partW * i + partW / 2, mHeight / 2, radius, mPaint);
            }
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

    /**
     * 重写onMeasure，解决在wrap_content下与match_parent效果一样的问题
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec),
                getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec));
        mWidth = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        mHeight = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
    }


    /**
     * Get the display size
     */
    @SuppressWarnings({"deprecation"})
    private void getCurrentDisplaySize() {
        WindowManager window = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);

        displays[0] = window.getDefaultDisplay().getWidth();
        displays[1] = window.getDefaultDisplay().getHeight();

    }
}
