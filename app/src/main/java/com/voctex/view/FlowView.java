package com.voctex.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.voctex.tools.StrValues;

/**
 * Created by voctex on 2016/6/27.
 */
public class FlowView extends View {

    private Paint mPaint, textPaint;

    private Paint mScalePaint;//圆弧刻度表
    private Paint mCirclePaint;//圆

    private int mWidth, mHeight, textPaintSize = 20;

    private int tbMargin;

    private double largeRadius, sqrtRadius;

    private int defaultColor = Color.argb(100, 100, 100, 100);


    private int[] flowArray = {0, 50, 200, 500, 1024, 10240, 20480};//"∞"
    /**
     * 开始的角度
     */
    private int startAngle = -225;
    /**
     * 弧线的角度
     */
    private int arcAngle = 270;

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

    private void initView() {

        //Android3.0 (API level11)开始,通过View.setLayerType()方法来使用layer有了更多的控制能力．此方法有两个参数：你想使用的layer的类型和一个可选的Paint对象，这个对象描述了layer应被如何组合．你可以使用Paint参数来应用颜色过滤，或指定混合模式或不透明度到一个layer．view可以使用以下三个类型之一：
        //LAYER_TYPE_NONE:view按一般方式绘制，不使用离屏缓冲．这是默认的行为．
        // LAYER_TYPE_HARDWARE:如果应用被硬加速了，view会被绘制到一个硬件纹理中．如果应用没被硬加速，此类型的layer的行为同于LAYER_TYPE_SOFTWARE．
        //LAYER_TYPE_SOFTWARE:view被绘制到一个bitmap中．
        if (Build.VERSION.SDK_INT >= 11) {
            this.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

        mPaint = new Paint();
        textPaint = new Paint();
        setBackgroundColor(Color.YELLOW);

        mCirclePaint = new Paint();
        mCirclePaint.setAntiAlias(true);//抗锯齿
        mCirclePaint.setColor(Color.BLUE);//设置画笔颜色

        mScalePaint = new Paint();
        mScalePaint.setAntiAlias(true);//抗锯齿
        mScalePaint.setStyle(Paint.Style.STROKE);//仅描边
        mScalePaint.setColor(Color.BLUE);//设置画笔颜色

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        int height=this.getMeasuredHeight();
//        int width=this.getMeasuredWidth();
//
//        int height1=MeasureSpec.getSize(heightMeasureSpec);
//        int width1=MeasureSpec.getSize(widthMeasureSpec);

        mWidth = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        mHeight = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        tbMargin = mHeight / 12;
        Log.i(StrValues.Tags.VOCTEX, "height=" + mHeight + "--width=" + mWidth);

        largeRadius = mHeight / 2 - tbMargin;
        sqrtRadius = Math.sqrt(largeRadius * largeRadius / 2);
        Log.i(StrValues.Tags.VOCTEX, "largeRadius=" + largeRadius + "--sqrtRadius=" + sqrtRadius);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.i(StrValues.Tags.VOCTEX, "zoudao onDraw");

        canvas.drawCircle(mWidth / 2, mHeight / 2 + tbMargin, mHeight * 7 / 24, mCirclePaint);

        mPaint.reset();
        mPaint.setStrokeWidth(10);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.GREEN);
        RectF oval = new RectF((mWidth - mHeight) / 2, 0 + tbMargin, (mWidth - mHeight) / 2 + mHeight, mHeight + tbMargin);
        //画弧线
        canvas.drawArc(oval, startAngle, arcAngle, false, mPaint);


        textPaint.reset();
        textPaint.setColor(Color.BLACK);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextSize(textPaintSize);

        mPaint.reset();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.GREEN);
        mPaint.setStrokeWidth(20);

        RectF oval1 = new RectF((mWidth - mHeight) / 2 + 5, 0 + tbMargin + 5, (mWidth - mHeight) / 2 + mHeight - 5, mHeight + tbMargin - 5);

        canvas.drawArc(oval1, -180, 1, false, mPaint);
        canvas.drawArc(oval1, -135, 1, false, mPaint);
        canvas.drawArc(oval1, -90, 1, false, mPaint);
        canvas.drawArc(oval1, -45, 1, false, mPaint);
        canvas.drawArc(oval1, 0, 1, false, mPaint);
        canvas.drawArc(oval1, 45, 1, false, mPaint);
        canvas.drawArc(oval1, 90, 1, false, mPaint);
        canvas.drawArc(oval1, 135, 1, false, mPaint);

//        scaleDraw(canvas);

        setMarkText(canvas);

    }

    /***
     * 画表盘刻度
     *
     * @param canvas
     */
    private void scaleDraw(Canvas canvas) {

        canvas.save(); // 保存canvas状态
        canvas.rotate(startAngle);
        for (int i = 0; i < 7; i++) {
//            if (0 == i % 10) {
            mScalePaint.setStrokeWidth(5);//设置刻度的描边的框
            /**画线
             * canvas.drawLine(startX, startY, stopX, stopY, paint)：
             * 前四个参数的类型均为float，
             * 最后一个参数类型为Paint。
             * 表示用画笔paint从点（startX,startY）到点（stopX,stopY）画一条直线；
             */
            canvas.drawLine((float) (mWidth / 2 - sqrtRadius), (float) (mHeight / 2 + tbMargin + sqrtRadius),
                    (float) (mWidth / 2 - sqrtRadius+10), (float) (mHeight / 2 + tbMargin + sqrtRadius-10), mScalePaint);
//            }
            canvas.rotate((float) Math.toDegrees(45));
        }
        mScalePaint.setStrokeWidth(5);
        canvas.restore();
    }

    private void setMarkText(Canvas canvas) {
        int offset = textPaintSize;
        double x = mWidth / 2, y = mHeight / 2 + tbMargin;
        double x0 = x - sqrtRadius - offset, y0 = y + sqrtRadius;
        double x1 = x - largeRadius - offset, y1 = y;
        double x2 = x - sqrtRadius - offset, y2 = y - sqrtRadius;
        double x3 = x - offset, y3 = y - largeRadius;
        double x4 = x + sqrtRadius - offset * 3 / 2, y4 = y - sqrtRadius;
        double x5 = x + largeRadius - offset * 2, y5 = y;
        double x6 = x + sqrtRadius - offset, y6 = y + sqrtRadius;

        canvas.drawText(getUnitText(flowArray[0]), (float) x0, (float) y0, textPaint);
        canvas.drawText(getUnitText(flowArray[1]), (float) x1, (float) y1, textPaint);
        canvas.drawText(getUnitText(flowArray[2]), (float) x2, (float) y2, textPaint);
        canvas.drawText(getUnitText(flowArray[3]), (float) x3, (float) y3, textPaint);
        canvas.drawText(getUnitText(flowArray[4]), (float) x4, (float) y4, textPaint);
        canvas.drawText(getUnitText(flowArray[5]), (float) x5, (float) y5, textPaint);
        canvas.drawText(getUnitText(flowArray[6]), (float) x6, (float) y6, textPaint);

    }


    private String getUnitText(float flow) {
        String unitText;
        if (flow >= 1024) {
            flow = flow / 1024;
            if (flow >= 20) {
                unitText = "∞";
            } else {
                unitText = flow + "MB";
            }
        } else {
            unitText = flow + "KB";
        }

        return unitText;
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
