package com.voctex.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by voctex on 2016/7/15.
 */
public class VtShopStatusView extends View {
    public VtShopStatusView(Context context) {
        super(context);
        initView();
    }

    public VtShopStatusView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public VtShopStatusView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @TargetApi(21)
    public VtShopStatusView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    private Paint pointPaint = new Paint();
    private Paint arcPaint = new Paint();
    private Paint arcPointPaint = new Paint();
    private Paint linePaint = new Paint();
    private Paint textPaint = new Paint();

    private int mWidth, mHeight;
    private int[] displays = new int[2];

    private int pointRadius = 0;
    private int pointNum = 5;
    private int pointDist = 0;
    private int currentItem = 3;

    private Paint.FontMetricsInt fontMetrics;

    private String[] time = {"18：15", "18：15", "18：15", "18：15", "18：15"};
    private String[] text = {"已支付", "已接单", "已配送", "已签收", "已评论"};
    private String[] texting = {"正在支付", "正在接单", "正在配送", "正在签收", "正在评论"};

    private void initView() {

        getCurrentDisplaySize();

        pointPaint.setColor(Color.parseColor("#FE980E"));
        pointPaint.setAntiAlias(true);

        arcPointPaint.setColor(Color.parseColor("#f0f0f0"));
        arcPointPaint.setAntiAlias(true);

        arcPaint.setColor(Color.parseColor("#FE980E"));
        arcPaint.setAntiAlias(true);
        arcPaint.setStrokeJoin(Paint.Join.ROUND);
        arcPaint.setStrokeCap(Paint.Cap.ROUND);
        arcPaint.setStyle(Paint.Style.STROKE);
        arcPaint.setStrokeWidth(3);

        linePaint.setColor(Color.BLACK);
        linePaint.setAntiAlias(true);
        //设置画直线格式
        linePaint.setStyle(Paint.Style.STROKE);
        //设置虚线效果
        linePaint.setPathEffect(new DashPathEffect(new float[]{5, 2}, 0));


        textPaint.setColor(Color.parseColor("#333333"));
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(13);


        setBackgroundColor(Color.parseColor("#f0f0f0"));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画虚线
        canvas.drawLine(0, mHeight / 4, mWidth, mHeight / 4, linePaint);


        //画圆点
        int x = 0, y = mHeight / 4;
        for (int i = 0; i < 5; i++) {

            if (i == 0) {
                x = pointDist + pointRadius;
            } else {
                x = x + pointRadius * 2 + pointDist;
            }
            RectF oval = new RectF(x - pointRadius, y - pointRadius, x + pointRadius, y + pointRadius);
            if (i + 1 >= currentItem) {
                canvas.drawCircle(x,y,pointRadius,arcPointPaint);
                canvas.drawArc(oval, 0, 360, false, arcPaint);

                drawText(x,(mHeight / 2+mHeight / 3 * 2)/2,texting[i],textPaint,canvas);
                break;
            } else {
                canvas.drawCircle(x, y, pointRadius, pointPaint);

                drawText(x,mHeight/2,time[i],textPaint,canvas);

                drawText(x,mHeight/3*2,text[i],textPaint,canvas);
            }




        }

        //


    }

    private void drawText(int x,int y,String text,Paint textPaint,Canvas canvas){
        //画文本
        int textlen = (int) textPaint.measureText(text);
        Paint.FontMetricsInt fontMetrics = textPaint.getFontMetricsInt();
        int textTop = fontMetrics.bottom - fontMetrics.top;
        int textLeft = x - textlen / 2;
        canvas.drawText(text, textLeft, y + textTop, textPaint);
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

        pointRadius = mWidth / 22;
        pointDist = (mWidth - pointNum * pointRadius * 2) / (pointNum + 1);
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
