package com.voctex.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Voctex on 2016/8/23.
 * 不爽就来大战300回合
 */
public class BestArcView extends View {
    public BestArcView(Context context) {
        super(context);
        initView();
    }

    public BestArcView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public BestArcView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BestArcView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    private int mWidth,mHeight,radius;
    private Paint picPaint=new Paint();
    private int arcDist;
    private int index;
    private Timer mTimer = new Timer();
    private RadialGradient mRadialGradient;

    private void initView(){
        picPaint.setColor(Color.RED);
        picPaint.setAntiAlias(true);
        picPaint.setStrokeJoin(Paint.Join.ROUND);
        picPaint.setStrokeCap(Paint.Cap.ROUND);
        picPaint.setStyle(Paint.Style.STROKE);
//        picPaint.setStrokeWidth(0.1f);


        //1.圆心X坐标2.Y坐标3.半径 4.颜色数组 5.相对位置数组，可为null 6.渲染器平铺模式
        mRadialGradient = new RadialGradient(240, 240, 240, new int[] {
                Color.YELLOW, Color.GREEN, Color.TRANSPARENT, Color.RED }, null,
                Shader.TileMode.REPEAT);
//        picPaint.setst
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        index=index+2;
        if (index>arcDist){
            index=0;
        }
//
//        mRadialGradient = new RadialGradient(mWidth/2, mHeight/2, arcDist/2+index, new int[] {
//                Color.WHITE, Color.TRANSPARENT}, null,
//                Shader.TileMode.REPEAT);
//
//        picPaint.setAlpha(400);
//        // 绘制环形渐变
//        picPaint.setShader(mRadialGradient);
//        // 第一个,第二个参数表示圆心坐标
//        // 第三个参数表示半径
//        canvas.drawCircle(mWidth/2, mHeight/2, radius, picPaint);



        for (int i=0;i<radius;i++){
            Paint picPaint=new Paint();
            picPaint.setColor(Color.RED);
            picPaint.setAntiAlias(true);
//            picPaint.setStrokeJoin(Paint.Join.ROUND);
//            picPaint.setStrokeCap(Paint.Cap.ROUND);
            picPaint.setStyle(Paint.Style.STROKE);
            picPaint.setAlpha(400+i*2);
            RectF ovl=new RectF(arcDist-index,arcDist-index,mWidth-arcDist+index,mHeight-arcDist+index);
            picPaint.setStrokeWidth(0.4f);
            canvas.drawArc(ovl,0,360,false,picPaint);
        }
//
////        canvas.draw
//        RectF ovl=new RectF(arcDist+index,arcDist+index,mWidth-arcDist-index,mHeight-arcDist-index);
//
//        picPaint.setStrokeWidth(radius/2);
//        canvas.drawArc(ovl,0,360,false,picPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        mHeight = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        Log.i("tag_voctex", "height=" + mHeight + "--width=" + mWidth);
        if (mWidth > mHeight) {
            radius = mHeight / 2;
        } else {
            radius = mWidth / 2;
        }
        arcDist=radius/2;
    }

    /**
     * Set the point launch time and the before launch time
     */
    public void setDuration(int waiTime, int intervalTime) {
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
//                handler.sendEmptyMessage(0);
                postInvalidate();
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

    /**
     * Set the point view cancel run
     */
    public void cancel() {
        mTimer.cancel();
    }

    /**
     * 装换成圆形的bitmap
     */
    private Bitmap createRoundConerImage(Bitmap source) {
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        Bitmap target = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(target);
        RectF rect = new RectF(0, 0, source.getWidth(), source.getHeight());
        canvas.drawRoundRect(rect, radius, radius, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(source, 0, 0, paint);
        return target;
    }

    public int dip2px(float dipValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
