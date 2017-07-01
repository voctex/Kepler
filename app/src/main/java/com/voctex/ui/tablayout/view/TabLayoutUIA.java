package com.voctex.ui.tablayout.view;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;


import com.voctex.R;
import com.voctex.base.BaseActivity;
import com.voctex.ui.tablayout.adapter.MyViewPagerAdapter;

/**
 * Created by mac_xihao on 17/6/28.
 */
public class TabLayoutUIA extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uia_tablayout_main);

        initView();
    }

    private void initView() {

        CoordinatorLayout coordinatorLayout = ((CoordinatorLayout) findViewById(R.id.tablayout_coordinator));
        AppBarLayout appBarLayout = ((AppBarLayout) findViewById(R.id.tablayout_appbar));
        Toolbar toolbar = ((Toolbar) findViewById(R.id.tablayout_toolbar));
        TabLayout mTabLayout = ((TabLayout) findViewById(R.id.tablayout_tabs));
        ViewPager mViewPager = ((ViewPager) findViewById(R.id.tablayout_viewpager));

        toolbar.setTitle("TabLayout界面");
        setSupportActionBar(toolbar);


        mTabLayout.addTab(mTabLayout.newTab().setText("TabOne"));//给TabLayout添加Tab
        mTabLayout.addTab(mTabLayout.newTab().setText("TabTwo"));
        mTabLayout.addTab(mTabLayout.newTab().setText("TabThree"));
        mTabLayout.setupWithViewPager(mViewPager);
        //给TabLayout设置关联ViewPager，如果设置了ViewPager，那么ViewPagerAdapter中的getPageTitle()方法返回的就是Tab上的标题

        MyViewPagerAdapter viewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(OneTabLayoutUIF.newInstance(), "TabOne");//添加Fragment
        viewPagerAdapter.addFragment(TwoTabLayoutUIF.newInstance(), "TabTwo");
        viewPagerAdapter.addFragment(ThrTabLayoutUIF.newInstance(), "TabThree");
        mViewPager.setAdapter(viewPagerAdapter);//设置适配器


    }
}
