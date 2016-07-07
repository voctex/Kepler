package com.voctex.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by voctex on 2016/7/1.
 */
public class HorizontalPointView extends View {

    private int[] displays = new int[2];
    private Paint mPaint;
    private int index = -1;
    private int pointCount = 6;
    private int mWidth, mHeight;
    private Timer mTimer = new Timer();
    private int smallPointColor = Color.parseColor("#f0f0f0");
    private int largePointColor = Color.WHITE;


    public HorizontalPointView(Context context) {
        super(context);
        initView();
    }

    public HorizontalPointView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public HorizontalPointView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @TargetApi(21)
    public HorizontalPointView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    private void initView() {
        getCurrentDisplaySize();
        //test
//        setBackgroundColor(Color.GRAY);

        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setColor(Color.WHITE);
        int itemW = mWidth / pointCount;

        for (int i = 0; i < pointCount; i++) {

            if (index == i) {
                mPaint.setColor(largePointColor);
                canvas.drawCircle(itemW * i + itemW / 2, mHeight / 2, itemW / 3, mPaint);
            } else {
                mPaint.setColor(smallPointColor);
                canvas.drawCircle(itemW * i + itemW / 2, mHeight / 2, itemW / 2 / 2, mPaint);

            }
        }


        if (index == pointCount - 1) {
            index = 0;
        } else {
            index++;
        }

    }

    /**
     * Set the point count
     */
    public void setPointNum(int pointNum) {
        this.pointCount = pointNum;
    }

    /**
     * Set the point launch time and the before launch time
     */
    public void setDuration(int waiTime, int intervalTime) {
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(0);
            }
        }, waiTime, intervalTime);
    }

    /**
     * Set the point color
     */
    public void setPointColor(int small, int large) {
        this.smallPointColor = small;
        this.largePointColor = large;
    }


    /**
     * Set the point view cancel run
     */
    public void cancel() {
        mTimer.cancel();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            invalidate();
        }
    };

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
