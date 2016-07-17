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
    private int currentItem = 3;

    private Paint.FontMetricsInt fontMetrics;

    private String[] time = {"18:15", "18:15", "18:15", "18:15", "18:15"};
    private String[] text = {"已支付", "已接单", "已配送", "已签收", "已评论"};
    private String[] texting = {"正在支付", "正在接单", "正在配送", "正在签收", "正在评论"};

    private List<ShopStatusBean> mList = new ArrayList<ShopStatusBean>();

    private void initView() {

        getCurrentDisplaySize();

        //实心圆的画笔
        pointPaint.setColor(Color.parseColor("#FE980E"));
        pointPaint.setAntiAlias(true);

        //圆环实心圆的画笔
        arcPointPaint.setColor(Color.parseColor("#f0f0f0"));
        arcPointPaint.setAntiAlias(true);

        //圆环的画笔
        arcPaint.setColor(Color.parseColor("#FE980E"));
        arcPaint.setAntiAlias(true);
        arcPaint.setStrokeJoin(Paint.Join.ROUND);
        arcPaint.setStrokeCap(Paint.Cap.ROUND);
        arcPaint.setStyle(Paint.Style.STROKE);
        arcPaint.setStrokeWidth(3);

        //线的画笔
        linePaint.setColor(Color.BLACK);
        linePaint.setAntiAlias(true);
        linePaint.setStyle(Paint.Style.STROKE);//设置画直线格式
        linePaint.setPathEffect(new DashPathEffect(new float[]{5, 5, 5, 5}, 1));//设置虚线效果

        //文本画笔
        textPaint.setColor(Color.parseColor("#333333"));
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(20);

        for (int i = 0; i < pointNum; i++) {
            ShopStatusBean bean=new ShopStatusBean();
            try {
                bean.setTime(time[i]);
                bean.setFinish(text[i]);
                bean.setDoing(texting[i]);
            } catch (Exception e) {

            }
            mList.add(bean);
        }

        setBackgroundColor(Color.parseColor("#f0f0f0"));
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

            if (i == 0) {
                x = pointDist + pointRadius;
            } else {
                x = x + pointRadius * 2 + pointDist;
            }
            RectF oval = new RectF(x - pointRadius, y - pointRadius, x + pointRadius, y + pointRadius);
            if (i >= currentItem) {
                //画背景色实心圆
                canvas.drawCircle(x, y, pointRadius, arcPointPaint);
                //画圆环
                canvas.drawArc(oval, 0, 360, false, arcPaint);
                //画正在做的文本
                drawText(x, (mHeight / 2 + mHeight / 3 * 2) / 2, mList.get(i).getDoing(), textPaint, canvas);
                break;
            } else {
                //画已完成实心圆
                canvas.drawCircle(x, y, pointRadius, pointPaint);
                //画已完成时间
                drawText(x, mHeight / 2, mList.get(i).getTime(), textPaint, canvas);
                //画已完成文本
                drawText(x, mHeight / 3 * 2, mList.get(i).getFinish(), textPaint, canvas);
            }

        }

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
        canvas.drawText(text, textLeft, y + textTop, textPaint);
    }

    /**
     * 设置当前的状态在哪个点上
     */
    public void setCurrentItem(int index) {
        this.currentItem = index;
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
