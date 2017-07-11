package com.voctex.ui.tablayout.view;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;


import com.voctex.R;
import com.voctex.base.BaseActivity;
import com.voctex.tools.VtToast;
import com.voctex.ui.tablayout.adapter.MyViewPagerAdapter;

/**
 * Created by mac_xihao on 17/6/28.
 */
public class TabLayoutUIA extends BaseActivity {

    private MyViewPagerAdapter myViewPagerAdapter;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uia_tablayout_main);

        initView();
    }

    private void initView() {

        final CoordinatorLayout coordinatorLayout = ((CoordinatorLayout) findViewById(R.id.tablayout_coordinator));
        AppBarLayout appBarLayout = ((AppBarLayout) findViewById(R.id.tablayout_appbar));
        toolbar = ((Toolbar) findViewById(R.id.tablayout_toolbar));
        mTabLayout = ((TabLayout) findViewById(R.id.tablayout_tabs));
        mViewPager = ((ViewPager) findViewById(R.id.tablayout_viewpager));

        toolbar.setTitle("TabLayout界面");
        setSupportActionBar(toolbar);
        //要在setSupportActionBar方法之后调用才会才生效
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mTabLayout.addTab(mTabLayout.newTab().setText("TabOne"));//给TabLayout添加Tab
        mTabLayout.addTab(mTabLayout.newTab().setText("TabTwo"));
        mTabLayout.addTab(mTabLayout.newTab().setText("TabThree"));
        mTabLayout.setupWithViewPager(mViewPager);
        //给TabLayout设置关联ViewPager，如果设置了ViewPager，那么ViewPagerAdapter中的getPageTitle()方法返回的就是Tab上的标题

        myViewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager());
        myViewPagerAdapter.addFragment(OneTabLayoutUIF.newInstance(), "TabOne");//添加Fragment
        myViewPagerAdapter.addFragment(TwoTabLayoutUIF.newInstance(), "TabTwo");
        myViewPagerAdapter.addFragment(ThrTabLayoutUIF.newInstance(), "TabThree");
        mViewPager.setAdapter(myViewPagerAdapter);//设置适配器
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                changeTopBgColor(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        changeTopBgColor(0);

    }

    /**
     * 根据Palette提取的颜色，修改tab和toolbar以及状态栏的颜色
     */
    private void changeTopBgColor(int position) {
        // 用来提取颜色的Bitmap
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), myViewPagerAdapter.getItem(position)
                .getBackgroundBitmapPosition(position));
        // Palette的部分
        Palette.Builder builder = Palette.from(bitmap);
        builder.generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                //获取到充满活力的这种色调
                Palette.Swatch vibrant = palette.getVibrantSwatch();
                //根据调色板Palette获取到图片中的颜色设置到toolbar和tab中背景，标题等，使整个UI界面颜色统一
                mTabLayout.setBackgroundColor(vibrant.getRgb());
                mTabLayout.setSelectedTabIndicatorColor(colorBurn(vibrant.getRgb()));
                toolbar.setBackgroundColor(vibrant.getRgb());

                if (android.os.Build.VERSION.SDK_INT >= 21) {
                    Window window = getWindow();
                    window.setStatusBarColor(colorBurn(vibrant.getRgb()));
                    window.setNavigationBarColor(colorBurn(vibrant.getRgb()));
                }
            }
        });
    }

    /**
     * 颜色加深处理
     *
     * @param RGBValues RGB的值，由alpha（透明度）、red（红）、green（绿）、blue（蓝）构成，
     *                  Android中我们一般使用它的16进制，
     *                  例如："#FFAABBCC",最左边到最右每两个字母就是代表alpha（透明度）、
     *                  red（红）、green（绿）、blue（蓝）。每种颜色值占一个字节(8位)，值域0~255
     *                  所以下面使用移位的方法可以得到每种颜色的值，然后每种颜色值减小一下，在合成RGB颜色，颜色就会看起来深一些了
     * @return
     */
    private int colorBurn(int RGBValues) {
        int alpha = RGBValues >> 24;
        int red = RGBValues >> 16 & 0xFF;
        int green = RGBValues >> 8 & 0xFF;
        int blue = RGBValues & 0xFF;
        red = (int) Math.floor(red * (1 - 0.1));
        green = (int) Math.floor(green * (1 - 0.1));
        blue = (int) Math.floor(blue * (1 - 0.1));
        return Color.rgb(red, green, blue);
    }

}
