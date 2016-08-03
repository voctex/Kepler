package com.voctex.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;

import com.voctex.bean.ShopStatusBean;

import java.util.ArrayList;
import java.util.List;

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
    private int currentItem = 0;

    private int bgColor = Color.parseColor("#f0f0f0");
    private int pointColor = Color.parseColor("#FE980E");
    private int textColor = Color.parseColor("#333333");
    private int lineColor = Color.BLACK;

    private List<ShopStatusBean> mList = new ArrayList<ShopStatusBean>();

    private void initView() {

        getCurrentDisplaySize();

        //实心圆的画笔
        pointPaint.setColor(pointColor);
        pointPaint.setAntiAlias(true);

        //圆环实心圆的画笔
        arcPointPaint.setColor(bgColor);
        arcPointPaint.setAntiAlias(true);

        //圆环的画笔
        arcPaint.setColor(pointColor);
        arcPaint.setAntiAlias(true);
        arcPaint.setStrokeJoin(Paint.Join.ROUND);
        arcPaint.setStrokeCap(Paint.Cap.ROUND);
        arcPaint.setStyle(Paint.Style.STROKE);
        arcPaint.setStrokeWidth(3);

        //线的画笔
        linePaint.setColor(lineColor);
        linePaint.setAntiAlias(true);
        linePaint.setStyle(Paint.Style.STROKE);//设置画直线格式
        linePaint.setPathEffect(new DashPathEffect(new float[]{5, 5, 5, 5}, 1));//设置虚线效果

        //文本画笔
        textPaint.setColor(textColor);
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(20);

        for (int i = 0; i < pointNum; i++) {
            ShopStatusBean bean = new ShopStatusBean();
            if (i == 0) {
                bean.setDoing("正在支付");
            }
            mList.add(bean);
        }

        setBackgroundColor(bgColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //画虚线
        Path path = new Path();
        path.moveTo(0, mHeight / 4);
        path.lineTo(mWidth, mHeight / 4);
        canvas.drawPath(path, linePaint);

        //画圆点
        int x = 0, y = mHeight / 4;
        for (int i = 0; i < pointNum; i++) {
            if (mList.size() < 1) {
                return;
            }
            ShopStatusBean bean = mList.get(i);

            if (i == 0) {
                x = pointDist + pointRadius;
            } else {
                x = x + pointRadius * 2 + pointDist;
            }
            RectF oval = new RectF(x - pointRadius, y - pointRadius, x + pointRadius, y + pointRadius);
            if (i >= currentItem - 1) {
                //画背景色实心圆
                canvas.drawCircle(x, y, pointRadius, arcPointPaint);
                //画圆环
                canvas.drawArc(oval, 0, 360, false, arcPaint);

                //画正在做的文本
                drawText(x, (mHeight / 2 + mHeight / 3 * 2) / 2, bean.getDoing(), textPaint, canvas);
                break;
            } else {
                //画已完成实心圆
                canvas.drawCircle(x, y, pointRadius, pointPaint);
                try {
                    //画已完成文本
                    drawText(x, mHeight / 3 * 2, bean.getFinish(), textPaint, canvas);
                    //画已完成时间
                    drawText(x, mHeight / 2, bean.getTime(), textPaint, canvas);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }

    }

    public void setData(int index, String time, String finish, String doing) {
        mList.get(index).setTime(time);
        mList.get(index).setFinish(finish);
        mList.get(index).setDoing(doing);
    }

    public void attachDataSource(List<ShopStatusBean> mList) {
        this.mList.clear();
        this.mList.addAll(mList);

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
     * 设置当前的状态在哪个点上
     */
    public void setCurrentItem(List<ShopStatusBean> mList) {
        this.mList = mList;
        this.currentItem = mList.size();
        refreshView();
    }

    private void refreshView() {
        post(new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        });
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
