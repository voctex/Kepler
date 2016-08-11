package com.voctex.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.voctex.R;
import com.voctex.tools.StrValues;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by voctex on 2016/7/13.
 */
public class RotateView extends View {
    public RotateView(Context context) {
        super(context);
        initView();
    }

    public RotateView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public RotateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @TargetApi(21)
    public RotateView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    /**
     * 弧线的画笔
     */
    private Paint arcPaint = new Paint();
    /**
     * 背景圆环的画笔
     */
    private Paint circlePaint = new Paint();
    /**
     * 文本的画笔
     */
    private Paint textPaint = new Paint();
    /**
     * 图片的画笔
     */
    private Paint picPaint = new Paint();
    /**
     * 当前view自身的宽高
     */
    private int mWidth, mHeight;
    /**
     * 开始的角度
     */
    private float startAngle = 0;
    /**
     * 弧线的角度
     */
    private float arcAngle = 150;
    private Timer mTimer = new Timer();
    /**
     * 弧线宽度
     */
    private float arcWidth = 5;
    /**
     * 开始的角度
     */
    private double beginAngle = 0;
    /**弧线每一次自增或自减的长度*/
    private double selfDist;
    /**
     * 旋转一圈中自增或自减的次数
     */
    private int i;
    /**
     * 改变弧长自增或自减的变量
     */
    private boolean isChange = false;

    private void initView() {
        arcPaint.setColor(Color.GREEN);
        arcPaint.setStrokeWidth(arcWidth);
        arcPaint.setStrokeJoin(Paint.Join.ROUND);
        arcPaint.setStrokeCap(Paint.Cap.ROUND);
        arcPaint.setStyle(Paint.Style.STROKE);
        arcPaint.setAntiAlias(true);//抗锯齿


        circlePaint.setColor(Color.GRAY);
        circlePaint.setStrokeWidth(arcWidth);
        circlePaint.setStrokeJoin(Paint.Join.ROUND);
        circlePaint.setStrokeCap(Paint.Cap.ROUND);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setAntiAlias(true);//抗锯齿

        textPaint.setColor(Color.BLUE);
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(25);

        selfDist = arcAngle / 41;

        setDuration(0, 30);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //圆的属性
        RectF oval = new RectF(0 + arcWidth, 0 + arcWidth, mWidth - arcWidth, mHeight - arcWidth);
        //画背景圆环
        canvas.drawArc(oval, 0, 360, false, circlePaint);
        //画图片
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.upload_ico);
        int bmpW=bmp.getWidth();
        int bmpH=bmp.getHeight();
        picPaint.setAntiAlias(true);
        canvas.drawBitmap(bmp, (mWidth-bmpW)/2, (mHeight-bmpH)/2, picPaint);

        startAngle = (float) (beginAngle);
        float arcAngles = (float) (arcAngle - selfDist * (i));
        float arcAngles2 = (float) ((arcAngle - selfDist * 40) + selfDist * (i));

        Log.i("voctex_tag", "start=" + startAngle + "---arc=" + arcAngles);
        if (!isChange) {
            //画弧线
            canvas.drawArc(oval, startAngle, arcAngles, false, arcPaint);
        } else {
            //画弧线
            canvas.drawArc(oval, startAngle, arcAngles2, false, arcPaint);
        }
        //转完一圈后初始化并改变自增或自减
        if (beginAngle >= 360) {
            beginAngle = 0;
            i = 0;
            isChange = !isChange;
            return;
        }
        i++;
        beginAngle = beginAngle + 9;

    }
    /**设置转圈的速率*/
    public void setDuration(int waiTime, int intervalTime) {
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(0);
            }
        }, waiTime, intervalTime);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            invalidate();
        }
    };

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mWidth = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        mHeight = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        Log.i(StrValues.Tags.VOCTEX, "height=" + mHeight + "--width=" + mWidth);

    }
}
