package com.voctex;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.voctex.base.BaseActivity;
import com.voctex.fragment.FirstFragment;
import com.voctex.fragment.FourthFragment;
import com.voctex.fragment.SecondFragment;
import com.voctex.fragment.WebFragment;
import com.voctex.tools.VtLog;

import java.util.Arrays;
import java.util.List;

/**
 * 主界面
 */
public class MainActivity extends BaseActivity implements View.OnClickListener ,TabHost.OnTabChangeListener{

    private Class<?>[] fragments = new Class[] { FirstFragment.class,
            SecondFragment.class, WebFragment.class,
            FourthFragment.class };
    private List<String> tabList;
    private TypedArray tabImgs;
    private int beforeTag = 0;
    private FragmentTabHost tabHost;

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

        initView();

    }

    public void setTheme() {
        //重新设置主题需要走的方法
        recreate();
    }

    private void initView(){
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
}
