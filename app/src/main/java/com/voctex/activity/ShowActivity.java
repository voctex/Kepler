package com.voctex.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.voctex.R;
import com.voctex.base.BaseActivity;
import com.voctex.fragment.TextFragment;
import com.voctex.view.slidetab.SlideTabBean;
import com.voctex.view.slidetab.SlideTabView;

import java.util.ArrayList;
import java.util.List;

public class ShowActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        initView();
    }



    private TextFragment text1=new TextFragment();
    private TextFragment text2=new TextFragment();
    private TextFragment text3=new TextFragment();
    private TextFragment text4=new TextFragment();

    private Fragment[] fragments = {text1, text2, text3,text4};

    private void initView(){

        SlideTabView slideTabView= (SlideTabView) findViewById(R.id.show_slidetab);

        List<SlideTabBean> fragmentList = new ArrayList<>();
        String[] titleTexts = new String[]{"距离", "资费", "当前", "第四"};
        for (int i = 0; i < titleTexts.length; i++) {
            Bundle mBundle = new Bundle();
            fragments[i].setArguments(mBundle);// 传不同值代表不同类型显示不同片段
            SlideTabBean fragTextBean = new SlideTabBean();
            fragTextBean.setText(titleTexts[i]);
            fragTextBean.setFragment(fragments[i]);
            fragmentList.add(fragTextBean);
        }
        slideTabView.initAll(fragmentList, getSupportFragmentManager());
        slideTabView.setTopLayoutHeight(40);
        slideTabView.setTitleTextSize(16);
        slideTabView.setLineColor(0xff1cadf7);
        slideTabView.setSeleteTextColor(0xff1cadf7);
        slideTabView.setCanSlideForTabLine(true);


    }


}
