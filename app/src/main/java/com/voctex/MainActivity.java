package com.voctex;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.voctex.base.BaseActivity;
import com.voctex.base.UniversalActivity;
import com.voctex.fragment.FirstFragment;
import com.voctex.fragment.FourthFragment;
import com.voctex.fragment.SecondFragment;
import com.voctex.fragment.ThreeFragment;
import com.voctex.fragment.WebFragment;
import com.voctex.tools.VtLog;
import com.voctex.tools.VtToast;
//import com.yidont.esdk.pay.bean.GamePayParams;
//import com.yidont.esdk.pay.interfac.PayStatusListener;
//import com.yidont.esdk.pay.view.PayView;

import java.util.Arrays;
import java.util.List;

/**
 * 主界面
 */
public class MainActivity extends UniversalActivity implements View.OnClickListener ,TabHost.OnTabChangeListener/*,PayStatusListener*/{

    private Class<?>[] fragments = new Class[] { FirstFragment.class,
            SecondFragment.class, ThreeFragment.class,
            FourthFragment.class };
    private List<String> tabList;
    private TypedArray tabImgs;
    private int beforeTag = 0;
    private FragmentTabHost tabHost;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        FragmentTabHost
//        HorizontalPointView sixPointView=(HorizontalPointView) findViewById(R.id.sixpoint);
//        sixPointView.setPointNum(8);
//        sixPointView.setDuration(1000,500);
//        sixPointView.setOnClickListener(this);

//


//                GamePayParams payParams=new GamePayParams();
//        //订单名称，如：屠龙刀
//        payParams.setGameOrderName("1111");
//        //订单总价，String类型
//        payParams.setGameOrderTotal("0.01");
//        //商品id，如：屠龙刀id为009
//        payParams.setGameProductId("abcdaeraer");
//        //订单id（时间戳形式），如果没有自己的订单生成规则，可以传入null，
//        payParams.setGameOrderNo(null);
//        PayView.showPayUI(this, payParams, this);

        initView();



    }

    private void initView(){
        //toolbar实现部分
        Toolbar toolbar= ((Toolbar) findViewById(R.id.main_toolbar));
        toolbar.setTitle("Kepler");
        //替换actionbar
        setSupportActionBar(toolbar);


        tabHost = (FragmentTabHost) findViewById(R.id.main_tabhost);
        tabHost.setup(this, getSupportFragmentManager(), R.id.main_content);

        tabList = Arrays.asList(getResources().getStringArray(
                R.array.main_tab_texts));
        tabImgs = getResources().obtainTypedArray(R.array.main_tab_icons);

        for (int i = 0; i < tabList.size(); i++) {
            TabHost.TabSpec spec = tabHost.newTabSpec(tabList.get(i)).setIndicator(
                    getView(i));
            tabHost.addTab(spec, fragments[i], null);

            VtLog.i( "spec.getTag()=" + spec.getTag());
        }

        // 设置tabs之间的分隔线不显示
        tabHost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);
        tabHost.getTabWidget().setGravity(Gravity.CENTER_VERTICAL);
        tabHost.setCurrentTab(0);
        tabHost.setOnTabChangedListener(this);

        tabHost.getTabContentView().addView(getView(1),0);


        navigationView = ((NavigationView) findViewById(R.id.navigation_view));
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                VtToast.s(mContext,"--"+item.getItemId());
                VtLog.i("itemId="+item.getItemId()+"--groupId="+item.getGroupId());
                VtLog.i("itemId="+R.id.navigation_item_home+"--groupId="+item.getGroupId());
                VtLog.i("itemId="+R.id.navigation_item_blog+"--groupId="+item.getGroupId());
                VtLog.i("itemId="+R.id.navigation_item_about+"--groupId="+item.getGroupId());
                drawerLayout.closeDrawer(navigationView);
                return false;
            }
        });

        LinearLayout headerLayout=(LinearLayout) navigationView.getHeaderView(0);
        headerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawers();

            }
        });


        drawerLayout = ((DrawerLayout) findViewById(R.id.drawer_layout));

        ActionBarDrawerToggle actionBarDrawerToggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close);
        actionBarDrawerToggle.syncState();
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                VtLog.i("drawer--slide"+slideOffset);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                VtLog.i("drawer--open"+"");
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                VtLog.i("drawer--close"+"");
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                VtLog.i("drawer--changed"+newState);
            }
        });




    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.hello_work:
//                startActivity(new Intent(this, ShowActivity.class));
//                break;
//            case R.id.main_data:
//                startActivity(new Intent(this, DataActivity.class));
////                startActivity(new Intent(this, SpannerActivity.class));
//                break;
//            case R.id.function_spanner:
////                startActivity(new Intent(this, DataActivity.class));
//                startActivity(new Intent(this, SpannerActivity.class));
//                break;
        }
    }

    @Override
    public void onTabChanged(String tabId) {

    }

    private View getView(int index) {
        final View view = LayoutInflater.from(this).inflate(
                R.layout.item_main_tabhost, null, true);
        LinearLayout linearLayout = (LinearLayout) view
                .findViewById(R.id.tab_fa_item_layout);
        TextView textView = (TextView) view.findViewById(R.id.tab_fa_item_text);
        ImageView imageView = (ImageView) view
                .findViewById(R.id.tab_fa_item_img);
        textView.setText(tabList.get(index));
        imageView.setImageResource(tabImgs.getResourceId(index, 0));

//        BadgeView badgeView = new BadgeView(MainTabActivity.this, linearLayout);
//        badgeView.setBadgeMargin(7);
//        badgeView.setTextSize(11.8f);
        // badgeView.show();

//        view.setTag(badgeViewIds[index], badgeView);
        view.setTag(imageView);
        if (index == 0) {
            linearLayout.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    VtLog.i(
                            "I click this linearlayout in first item");
                    // ((BadgeView) view.getTag()).toggle();
                    if (beforeTag == 0) {
                        FirstFragment firstFragment = (FirstFragment) getSupportFragmentManager()
                                .findFragmentByTag(tabList.get(0));
//                        firstFragment.tableToggle();
                        ImageView img = (ImageView) tabHost.getTabWidget()
                                .getChildAt(0).getTag();
//                        if (!callJieHeFra.getTableVisibility()) {
//                            img.setImageResource(R.drawable.tab_item_dialpad_on);
//                        } else {
//                            img.setImageResource(R.drawable.tab_item_dialpad_off);
//                        }
                    }
                    // 注：放在最下面，要先判断beforeTag的值才可以切换tab
                    tabHost.setCurrentTab(0);
                }
            });
        }

        return view;
    }

//    @Override
//    public void onPaySuccess(GamePayParams payParams) {
//
//    }
//
//    @Override
//    public void onPayFailure(GamePayParams payParams, String reason) {
//
//    }
}
