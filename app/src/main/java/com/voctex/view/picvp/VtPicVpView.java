package com.voctex.view.picvp;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.voctex.R;
import com.voctex.tools.VtToast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by voctex on 2016/7/21.
 */
public class VtPicVpView extends LinearLayout implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private ViewPager viewPager;
    private ImageView leftImg;
    private ImageView rightImg;
    private PicVpAdapter adapter;
    private TextView detailText;

    public VtPicVpView(Context context) {
        super(context);
        initView();
    }

    public VtPicVpView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public VtPicVpView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @TargetApi(21)
    public VtPicVpView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    private int currentIndex = 1;
    private List<String> vList = new ArrayList<>();

    private void initView() {
        setOrientation(VERTICAL);


        LinearLayout picLayout = new LinearLayout(getContext());
        LayoutParams picLayoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        picLayoutParams.weight = 1;
        picLayout.setLayoutParams(picLayoutParams);
        picLayout.setOrientation(HORIZONTAL);

        leftImg = new ImageView(getContext());
        rightImg = new ImageView(getContext());
        setImage(leftImg, true);
        setImage(rightImg, false);

        viewPager = new ViewPager(getContext());
        LayoutParams vLayoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        vLayoutParams.weight = 1;
        viewPager.setId(R.id.pic_vp_id);
        viewPager.setLayoutParams(vLayoutParams);

        picLayout.addView(leftImg);
        picLayout.addView(viewPager);
        picLayout.addView(rightImg);
        picLayout.setGravity(Gravity.CENTER_VERTICAL);

        addView(picLayout);

        detailText = new TextView(getContext());
        LayoutParams tLayoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        detailText.setLayoutParams(tLayoutParams);
        detailText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        detailText.setText("text");
        detailText.setGravity(Gravity.CENTER_HORIZONTAL);
        int lrPd = getResources().getDimensionPixelOffset(R.dimen.vt_pic_text_lr_pd);
        int tbPd = getResources().getDimensionPixelOffset(R.dimen.vt_pic_text_tb_pd);
        detailText.setPadding(lrPd, tbPd, lrPd, tbPd);
        detailText.setTextColor(Color.parseColor("#333333"));

        addView(detailText);

    }

    private void setImage(ImageView imageView, boolean isLeft) {
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        if (isLeft) {
            imageView.setId(R.id.pic_vp_left);
            imageView.setImageResource(R.mipmap.ic_picvp_left);
        } else {
            imageView.setId(R.id.pic_vp_right);
            imageView.setImageResource(R.mipmap.ic_picvp_right);
        }
        imageView.setLayoutParams(layoutParams);
        imageView.setOnClickListener(this);
    }


    /**
     * 暴露给外面的绑定数据的方法
     */
    public void attachDataSource(List<String> mList) {
        vList.clear();
        vList.add(mList.get(mList.size() - 1));
        vList.addAll(mList);
        vList.add(mList.get(0));
        setAdapter(vList);
    }

    /**设置底部文本*/
    public void setBottomText(String text) {
        if (TextUtils.isEmpty(text)) {
            detailText.setVisibility(View.GONE);
        } else {
            detailText.setVisibility(View.VISIBLE);
            detailText.setText(text);
        }
    }

    /**
     * 绑定数据的初始操作
     */
    private void setAdapter(List<String> mList) {
        adapter = new PicVpAdapter(getContext(), mList);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(this);
        viewPager.setCurrentItem(1, false);
        currentIndex = 1;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (positionOffsetPixels == 0.0) {
            if (position == vList.size() - 1) {
                viewPager.setCurrentItem(1, false);
            } else if (position == 0) {
                viewPager.setCurrentItem(vList.size() - 2, false);
            } else {
                viewPager.setCurrentItem(position);
            }
        }
    }

    @Override
    public void onPageSelected(int position) {
        currentIndex = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * 设置点击事件
     */
    public void setOnVpItemClickListener(OnVpItemClickListener onVpItemClickListener) {
        if (this.adapter != null) {
            this.adapter.setOnVpItemClickListener(onVpItemClickListener);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pic_vp_left: {
                viewPager.setCurrentItem(currentIndex - 1, false);
                break;
            }
            case R.id.pic_vp_right: {
                viewPager.setCurrentItem(currentIndex + 1, false);
                break;
            }
        }
    }


}
