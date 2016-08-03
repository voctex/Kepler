package com.voctex.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.voctex.tools.StrValues;

/**
 * Created by voctex on 2016/7/25.
 */
public class PriceView extends View {
    public PriceView(Context context) {
        super(context);
        initView();
    }

    public PriceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public PriceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @TargetApi(value = Build.VERSION_CODES.LOLLIPOP)//21
    public PriceView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    /**
     * view的宽高
     */
    private int mWidth, mHeight;
    /**
     * 圆环宽度
     */
    private int arcWidth = 5;
    /**
     * 圆环的画笔
     */
    private Paint arcPaint = new Paint();
    /**
     * 价格的画笔
     */
    private Paint textPaint = new Paint();
    /**
     * 单位的画笔
     */
    private Paint unitPaint = new Paint();
    /**
     * 价格，默认价格是0.00
     */
    private String price = "0.00";
    /**
     * 单位，默认单位是人民币
     */
    private String unit = "￥";
    /**
     * 颜色，默认颜色是绿色
     */
    private int defaultColor = Color.GREEN;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mWidth = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        mHeight = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        Log.i(StrValues.Tags.VOCTEX, "height=" + mHeight + "--width=" + mWidth);

        //这里设置为宽度的比例值是考虑到不同分辨率的手机会显示不一样的字体
        textPaint.setTextSize(mWidth / 7);
        unitPaint.setTextSize(mWidth / 8);

    }

    private void initView() {
        //圆环实心圆的画笔
        arcPaint.setAntiAlias(true);
        arcPaint.setStrokeWidth(2);
        arcPaint.setStrokeJoin(Paint.Join.ROUND);
        arcPaint.setStrokeCap(Paint.Cap.ROUND);
        arcPaint.setStyle(Paint.Style.STROKE);
        arcPaint.setColor(defaultColor);

        //文本画笔
        textPaint.setColor(defaultColor);
        textPaint.setAntiAlias(true);

        //文本画笔
        unitPaint.setColor(defaultColor);
        unitPaint.setAntiAlias(true);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        RectF oval = new RectF(arcWidth, arcWidth, mWidth - arcWidth, mHeight - arcWidth);

        //画圆环
        canvas.drawArc(oval, 0, 360, false, arcPaint);

        //画单位
        drawText(mWidth / 2, (mHeight / 2 - arcWidth) / 2, unit, unitPaint, canvas);

        //画价格
        drawText(mWidth / 2, mHeight *9/ 20, "" + price, textPaint, canvas);
    }


    /**
     * 根据所在的中心坐标画出文本
     */
    private void drawText(int x, int y, String text, Paint textPaint, Canvas canvas) {
        if (TextUtils.isEmpty(text)) {
            return;
        }
        //画文本
        int textlen = (int) textPaint.measureText(text);
        Paint.FontMetricsInt fontMetrics = textPaint.getFontMetricsInt();
        int textTop = fontMetrics.bottom - fontMetrics.top;
        int textLeft = x - textlen / 2;
        canvas.drawText(text, textLeft, y + textTop/2, textPaint);
    }

    /**
     * 设置单位值
     */
    public void setUnit(String unit) {
        this.unit = unit;
        refreshView();
    }

    /**
     * 设置价格
     */
    public void setPrice(String price) {
        this.price = price;
        refreshView();
    }

    /**
     * 设置价格
     */
    public void setPrice(double price) {
        try {
            this.price = String.valueOf(price);
        } catch (Exception e) {
            e.printStackTrace();
        }
        refreshView();
    }

    public void setColor(int color){
        this.defaultColor=color;
        refreshView();
    }

    /**
     * 自我刷新的方法
     */
    private void refreshView() {
        post(new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        });
    }

}
