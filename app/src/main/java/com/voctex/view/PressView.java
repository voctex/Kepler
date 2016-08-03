package com.voctex.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.voctex.R;
import com.voctex.tools.StrValues;
import com.voctex.tools.VtToast;

/**
 * Created by voctex on 2016/7/27.
 * 创建一个圆形区域，跟其他view一样是可以点击的，有按下去的效果，不过是绘制出来的，脱离圆形区域后点击是视为无效的
 */
public class PressView extends View {
    public PressView(Context context) {
        super(context);
        initView(null);
    }

    public PressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(attrs);
    }

    public PressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(attrs);
    }

    @TargetApi(value = Build.VERSION_CODES.LOLLIPOP)//21
    public PressView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(attrs);
    }

    /**
     * view的宽高
     */
    private int mWidth, mHeight;
    private int radius;
    private int selectColor = Color.GRAY;
    private int normalColor = Color.WHITE;
    private int bgColor = normalColor;
    private int textColor = Color.BLACK;
    private int textSize = 10;
    private String text = "";
    /**
     * 圆形画笔
     */
    private Paint circlePaint = new Paint();
    private Paint textPaint = new Paint();

    private void initView(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.press_view);
        int count = a.getIndexCount();

        for (int i = 0; i < count; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.press_view_tc_textColor:
                    textColor = a.getColor(attr, textColor);
                    break;
                case R.styleable.press_view_tc_textSize:
                    textSize = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
                    break;
                case R.styleable.press_view_tc_text:
                    text = "" + a.getString(attr);
                    break;
                case R.styleable.press_view_tc_normalColor:
                    normalColor = a.getColor(attr, normalColor);
                    break;
                case R.styleable.press_view_tc_selectColor:
                    selectColor = a.getColor(attr, selectColor);
                    break;

            }
        }
        a.recycle();

        //抗锯齿
        circlePaint.setAntiAlias(true);

        textPaint.setAntiAlias(true);
        textPaint.setColor(textColor);
        textPaint.setTextSize(textSize);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        mHeight = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        Log.i(StrValues.Tags.VOCTEX, "height=" + mHeight + "--width=" + mWidth);
        if (mWidth > mHeight) {
            radius = mHeight / 2;
        } else {
            radius = mWidth / 2;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //画圆形
        circlePaint.setColor(bgColor);
        canvas.drawCircle(mWidth / 2, mHeight / 2, radius, circlePaint);

        //画文本
        drawText(mWidth / 2, mHeight / 2, text, textPaint, canvas);
    }

    private boolean isMoveCircleOut = false, isMove = false;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        // flag为true即控件被点到时，执行移动控件操作
        float x = event.getX();
        float y = event.getY();
        Log.i("tag_voctex", "x=" + x + "--y=" + y);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                //勾股定理，开根号的算法
                double interval = Math.sqrt(Double.valueOf((mWidth / 2 - x) * (mWidth / 2 - x) + (mHeight / 2 - y) * (mHeight / 2 - y)));
                Log.i("tag_voctex", "interval=" + interval + "---radius=" + radius);
                if (radius >= interval) {
                    bgColor = selectColor;
                    refreshView();
                    isMoveCircleOut = false;
                } else {
                    isMoveCircleOut = true;
                }
                isMove = false;
                break;
            case MotionEvent.ACTION_MOVE:
                isMove = true;
                if (isMoveCircleOut) {
                    return true;
                }

                //勾股定理，开根号的算法
                double interval1 = Math.sqrt(Double.valueOf((mWidth / 2 - x) * (mWidth / 2 - x) + (mHeight / 2 - y) * (mHeight / 2 - y)));
                Log.i("tag_voctex", "interval=" + interval1 + "---radius=" + radius);
                if (radius >= interval1) {
                    //手指的坐标移动在当前圆形区域内
                    isMove = false;
                } else {
                    bgColor = normalColor;
                    refreshView();
                    isMoveCircleOut = true;
                    isMove = true;
                }

                break;
            case MotionEvent.ACTION_UP:
                bgColor = normalColor;
                refreshView();

                if (!isMove && !isMoveCircleOut) {
                    VtToast.s(getContext(), "点击了区域内的");
                    if (onPressViewClickListener != null) {
                        onPressViewClickListener.onPressClick(this);
                    }
                }
                break;
        }

        return true;
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
        int textLeft = x - textlen / 2;
        float textCenterVerticalBaselineY = y - fontMetrics.descent + (fontMetrics.descent - fontMetrics.ascent) / 2;

        canvas.drawText(text, textLeft, textCenterVerticalBaselineY, textPaint);
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

    public interface OnPressViewClickListener {
        void onPressClick(View view);
    }

    private OnPressViewClickListener onPressViewClickListener;

    public void setOnPressViewClickListener(OnPressViewClickListener onPressViewClickListener) {
        this.onPressViewClickListener = onPressViewClickListener;
    }
}
