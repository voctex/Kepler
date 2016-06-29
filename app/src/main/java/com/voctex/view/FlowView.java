package com.voctex.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by voctex on 2016/6/27.
 */
public class FlowView extends View{

    private Paint mPaint,textPaint;

    private int mWidth,mHeight;

    private int tbMargin;

    private int defaultColor=Color.argb(100,100,100,100);

    public FlowView(Context context) {
        super(context);
        initView();
    }

    public FlowView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public FlowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }
    @TargetApi(21)
    public FlowView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    private void initView(){
        mPaint=new Paint();
        textPaint=new Paint();
        setBackgroundColor(Color.argb(255,240,240,240));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        int height=this.getMeasuredHeight();
//        int width=this.getMeasuredWidth();
//
//        int height1=MeasureSpec.getSize(heightMeasureSpec);
//        int width1=MeasureSpec.getSize(widthMeasureSpec);

        mWidth=getDefaultSize(getSuggestedMinimumWidth(),widthMeasureSpec);
        mHeight=getDefaultSize(getSuggestedMinimumHeight(),heightMeasureSpec);
        tbMargin=mHeight/12;

        Log.i("voctex","height="+mHeight+"--width="+mWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.i("voctex","zoudao onDraw");

        mPaint.setColor(defaultColor);
        canvas.drawCircle(mWidth/2,mHeight/2+tbMargin,mHeight/3,mPaint);

        mPaint.reset();
        mPaint.setStrokeWidth(10);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.GREEN);
        RectF oval=new RectF((mWidth-mHeight)/2,0+tbMargin,(mWidth-mHeight)/2+mHeight,mHeight+tbMargin);

        canvas.drawArc(oval,-225,270,false,mPaint);



        textPaint.reset();
        textPaint.setColor(Color.BLACK);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextSize(20);

        mPaint.reset();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.GREEN);
        mPaint.setStrokeWidth(20);

        RectF oval1=new RectF((mWidth-mHeight)/2+5,0+tbMargin+5,(mWidth-mHeight)/2+mHeight-5,mHeight+tbMargin-5);

        Path path=new Path();
        path.addArc(oval1,-226,1);

        canvas.drawTextOnPath("10KB",path,-10,-10,textPaint);

        canvas.drawArc(oval1,-180,1,false,mPaint);
        canvas.drawArc(oval1,-135,1,false,mPaint);
        canvas.drawArc(oval1,-90,1,false,mPaint);
        canvas.drawArc(oval1,-45,1,false,mPaint);
        canvas.drawArc(oval1,0,1,false,mPaint);
        canvas.drawArc(oval1,45,1,false,mPaint);
        canvas.drawArc(oval1,90,1,false,mPaint);
        canvas.drawArc(oval1,135,1,false,mPaint);



//        canvas.drawTextOnPath();

    }


    /**
     * 作用是返回一个默认的值，如果MeasureSpec没有强制限制的话则使用提供的大小.否则在允许范围内可任意指定大小
     * 第一个参数size为提供的默认大小，第二个参数为测量的大小
     */
    public static int getDefaultSize(int size, int measureSpec) {
        int result = size;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        switch (specMode) {
            // Mode = UNSPECIFIED,AT_MOST时使用提供的默认大小
            case MeasureSpec.UNSPECIFIED:
                result = size;
                break;
            case MeasureSpec.AT_MOST:
                // Mode = EXACTLY时使用测量的大小
            case MeasureSpec.EXACTLY:
                result = specSize;
                break;
        }
        return result;
    }

}
