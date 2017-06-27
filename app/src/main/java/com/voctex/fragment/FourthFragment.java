package com.voctex.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.voctex.R;
import com.voctex.base.BaseFragment;
import com.voctex.tools.PhoneUtil;
import com.voctex.view.slidetab.SlideTabBean;
import com.voctex.view.slidetab.SlideTabView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FourthFragment extends BaseFragment {


    public FourthFragment() {
        // Required empty public constructor
    }

    private TextFragment text1=new TextFragment();
    private TextFragment text2=new TextFragment();
    private TextFragment text3=new TextFragment();

    private Fragment[] fragments = {text1, text2, text3};


//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//
//        viewGroup= (ViewGroup) inflater.inflate(R.layout.fragment_fourth, container, false);
//        initView();
//        return viewGroup;
//
//    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_fourth;
    }

    @Override
    protected void  initView(){
        SlideTabView slideTabView= (SlideTabView) mViewGroup.findViewById(R.id.four_slidetab);

//        List<SlideTabBean> fragmentList = new ArrayList<>();
//        String[] titleTexts = new String[]{"距离", "资费", "当前"};
//        for (int i = 0; i < titleTexts.length; i++) {
//            Bundle mBundle = new Bundle();
//            fragments[i].setArguments(mBundle);// 传不同值代表不同类型显示不同片段
//            SlideTabBean fragTextBean = new SlideTabBean();
//            fragTextBean.setText(titleTexts[i]);
//            fragTextBean.setFragment(fragments[i]);
//            fragmentList.add(fragTextBean);
//        }
//        slideTabView.initAll(fragmentList, getChildFragmentManager());
//        slideTabView.setTopLayoutHeight(40);
//        slideTabView.setTitleTextSize(16);
//        slideTabView.setLineColor(0xff1cadf7);
//        slideTabView.setSeleteTextColor(0xff1cadf7);

        TextView textView= ((TextView) mViewGroup.findViewById(R.id.test_key));

        textView.setText("数据为："+PhoneUtil.getStringData(mContext,"testKey"));

    }

}
