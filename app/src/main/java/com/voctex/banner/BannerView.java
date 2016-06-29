package com.voctex.banner;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;


import com.voctex.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 作者： 巴掌 on 16/6/9 17:32
 */
public class BannerView extends FrameLayout {

    private ViewPager mVpBanner;
//    private BannerLine mLine;
//    private BannerPoint mPoint;
    private BannerPointLayout mPointLayout;
    private List<BannerEntity> mEntities = new ArrayList<BannerEntity>();
    private BannerPagerAdapter mAdapter;
    private Timer mTimer = new Timer();
    private int currentPosition;

    public BannerView(Context context) {
        this(context, null);
    }

    public BannerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View.inflate(getContext(), R.layout.banner_view, this);
        mVpBanner = (ViewPager) this.findViewById(R.id.vp_banner);
//        mLine = (BannerLine) this.findViewById(R.id.line);
//        mPoint=(BannerPoint)this.findViewById(R.id.banner_point);
        mPointLayout= (BannerPointLayout) this.findViewById(R.id.point_layout);
        TypedArray typeArray = context.obtainStyledAttributes(attrs,
                R.styleable.QingtingBanner);
        int lineColor = typeArray.getColor(R.styleable.QingtingBanner_qt_line_color, ContextCompat.getColor(getContext(), R.color.banner_red));
        typeArray.recycle();
//        mLine.setLineColor(lineColor);
    }

    public void setEntities(List<BannerEntity> entities) {
        addExtraPage(entities);
        showBanner();
        schedule();
    }

    private void addExtraPage(List<BannerEntity> entities) {
        mEntities.add(entities.get(entities.size() - 1));
        mEntities.addAll(entities);
        mEntities.add(entities.get(0));
    }

    private void showBanner() {
//        mLine.setPageWidth(mEntities.size());
        mPointLayout.setChildCount(mEntities.size());
//        mPoint.setmCount(mEntities.size());
        mAdapter = new BannerPagerAdapter(getContext(), mEntities);

        mVpBanner.setAdapter(mAdapter);
        mVpBanner.setCurrentItem(1, false);
        mVpBanner.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                mLine.setPageScrolled(position, positionOffset);
//                mPoint.setPageScrolled(position,positionOffset);

                if (positionOffsetPixels == 0.0) {
                    if (position == mEntities.size() - 1) {
                        mVpBanner.setCurrentItem(1, false);
                    } else if (position == 0) {
                        mVpBanner.setCurrentItem(mEntities.size() - 2, false);
                    } else {
                        mVpBanner.setCurrentItem(position);
                        mPointLayout.setCurrentPoint(position);
                    }
                }
            }

            @Override
            public void onPageSelected(int position) {
                currentPosition=position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void schedule() {
        mTimer.schedule(mTimerTask, 3000, 3000);
    }

    private TimerTask mTimerTask = new TimerTask() {
        @Override
        public void run() {

            handler.sendEmptyMessage(0);
        }
    };
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (currentPosition!=mEntities.size()-1){
                currentPosition=currentPosition+1;
                mVpBanner.setCurrentItem(currentPosition);

            }else{
                currentPosition=0;
                mVpBanner.setCurrentItem(0);
            }
        }
    };

    public void setOnBannerClickListener(OnBannerClickListener clickListener) {
        if (mAdapter != null) {
            mAdapter.setOnBannerClickListener(clickListener);
        }
    }

}
