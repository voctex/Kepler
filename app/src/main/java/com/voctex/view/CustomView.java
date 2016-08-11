package com.voctex.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import com.voctex.R;

/**
 * Created by Voctex on 2016/8/2.
 */
public class CustomView extends View {
    public CustomView(Context context) {
        super(context);
        initView(null);
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(attrs);
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CustomView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(attrs);
    }

    /**
     * 整个view内容的宽高
     */
    private int contentWidth;
    private int contentHeight;

    private float mBorderWidth;
    private int mBorderColor;

    private Paint mPaint;

    private RectF mBounds;
    private float width;
    private float height;
    float radius;
    float smallLength;
    float largeLength;

    private void initView(AttributeSet attrs) {
        TypedArray typedArray = getContext().getTheme()
                .obtainStyledAttributes(
                        attrs,
                        R.styleable.CustomView
                        , 0, 0);

        try{
            mBorderColor = typedArray.getColor(R.styleable.CustomView_border_color,0xff000000);
            mBorderWidth = typedArray.getDimension(R.styleable.CustomView_border_width,2);
        }finally {
            typedArray.recycle();
        }

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mBorderWidth);
        mPaint.setColor(mBorderColor);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mBounds = new RectF(getLeft(),getTop(),getRight(),getBottom());

        width = mBounds.right - mBounds.left;
        height = mBounds.bottom - mBounds.top;

        if(width<height){
            radius = width/4;
        }else{
            radius = height/4;
        }

        smallLength =10;
        largeLength=20;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(0xff000000);
        mPaint.setColor(0x66555555);
        canvas.drawRoundRect(new RectF(mBounds.centerX()-(float)0.9*width/2, mBounds.centerY() - (float)0.9*height/2, mBounds.centerX() + (float)0.9*width/2, mBounds.centerY() + (float)0.9*height/2), 30, 30, mPaint);
        mPaint.setColor(mBorderColor);
        canvas.drawCircle(mBounds.centerX(),mBounds.centerY(),radius,mPaint);
        float start_x,start_y;
        float end_x,end_y;
        for(int i=0;i<60;++i){
            start_x= radius *(float)Math.cos(Math.PI/180 * i * 6);
            start_y= radius *(float)Math.sin(Math.PI/180 * i * 6);
            if(i%5==0){
                end_x = start_x+largeLength*(float)Math.cos(Math.PI / 180 * i * 6);
                end_y = start_y+largeLength*(float)Math.sin(Math.PI/180 * i * 6);
            }else{
                end_x = start_x+smallLength*(float)Math.cos(Math.PI/180 * i * 6);
                end_y = start_y+smallLength*(float)Math.sin(Math.PI/180 * i * 6);
            }
            start_x+=mBounds.centerX();
            end_x+=mBounds.centerX();
            start_y+=mBounds.centerY();
            end_y+=mBounds.centerY();
            canvas.drawLine(start_x, start_y, end_x, end_y, mPaint);
        }
        canvas.drawCircle(mBounds.centerX(),mBounds.centerY(),20,mPaint);
        canvas.rotate(60,mBounds.centerX(),mBounds.centerY());
        canvas.drawLine(mBounds.centerX(),mBounds.centerY(),mBounds.centerX(),mBounds.centerY()-radius,mPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //先利用MeasureSpec得到宽高的mode
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        //利用MeasureSpec得到宽高的size
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        //最后要设置的宽高
        int width;
        int height;

        //我们期待的宽高
        int desiredWidth = getPaddingLeft() + getPaddingRight() + contentWidth;
        int desiredHeight = getPaddingTop() + getPaddingBottom() + contentHeight;

        //根据模式来对我们需要设置的宽高进行判断
        switch (widthMode) {
            case MeasureSpec.AT_MOST:
                width = Math.min(widthSize, desiredWidth);
                break;
            case MeasureSpec.UNSPECIFIED:
                width = desiredWidth;
                break;
            default:
            case MeasureSpec.EXACTLY:
                width = widthSize;
                break;
        }
        switch (heightMode) {
            case MeasureSpec.AT_MOST:
                height = Math.min(heightSize, desiredHeight);
                break;
            case MeasureSpec.UNSPECIFIED:
                height = desiredHeight;
                break;
            default:
            case MeasureSpec.EXACTLY:
                height = heightSize;
                break;
        }
        //调用父类的测量方法
        setMeasuredDimension(width, height);
    }
}
