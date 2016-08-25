package com.voctex.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Voctex on 2016/8/12.
 */
public class IndicateView extends LinearLayout implements View.OnClickListener, ViewPager.OnPageChangeListener {
    public IndicateView(Context context) {
        super(context);
        initView();
    }

    public IndicateView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public IndicateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @TargetApi(value = Build.VERSION_CODES.LOLLIPOP)//21
    public IndicateView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    private int currentIndex;
    private List<Holder> mList = new ArrayList<>();
    private ViewPager viewPager;
    private int normalColor = Color.BLUE;
    private int selectColor = Color.RED;


    private void initView() {
        setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        setLayoutParams(layoutParams);

    }

    public void setViewPager(ViewPager viewPager) {
        this.viewPager = viewPager;
        this.viewPager.addOnPageChangeListener(this);
    }

    public void setup(Holder holder) {
        if (holder != null) {
            mList.add(holder);
        }

    }

//    private void

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        currentIndex = position;

        for (Holder holder : mList) {
            holder.title.setTextColor(normalColor);
            holder.icon.setSelected(false);
        }
        mList.get(position).title.setTextColor(selectColor);
        mList.get(position).icon.setSelected(true);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public static class Holder {
        TextView title;
        ImageView icon;
    }
}
