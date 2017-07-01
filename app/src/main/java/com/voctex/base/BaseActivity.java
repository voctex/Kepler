package com.voctex.base;

import android.content.Context;
import android.os.Bundle;

import com.voctex.R;
import com.voctex.tools.SPUtil;

import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

public class BaseActivity extends UniversalSwipeBackActivity {

    protected Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;

    }

//    public void setActTheme(){
//        setActTheme(0);
//    }
//    public void setActTheme(int style) {
//        if (style==0) {
//            if ((boolean) SPUtil.get(this, SPUtil.FileName.SYSTEM, "isNight", false)) {
//                setTheme(R.style.AppDarkTheme);
//            } else {
//                setTheme(R.style.AppBaseTheme);
//            }
//        }else{
//            setTheme(style);
//        }
//        //重新设置主题需要走的方法
//        recreate();
//    }

//    @Override
//    public FragmentAnimator onCreateFragmentAnimator() {
//        // 设置横向(和安卓4.x动画相同)
//        return new DefaultHorizontalAnimator();
//    }
//    @Override
//    public void onBackPressedSupport() {
//        // 对于 4个类别的主Fragment内的回退back逻辑,已经在其onBackPressedSupport里各自处理了
//        super.onBackPressedSupport();
//    }


}
