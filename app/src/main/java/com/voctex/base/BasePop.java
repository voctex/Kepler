package com.voctex.base;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;


/**
 * Created by Voctex on 2016/8/22.
 */

public abstract class BasePop extends PopupWindow implements View.OnClickListener{

    protected Context mContext;
    protected View positionView;

    private BasePop(){

    }

    protected BasePop(Context mContext,View positionView,int layout){
        super(LayoutInflater.from(mContext).inflate(layout,null),-1,-1);
        this.mContext=mContext;
        this.positionView=positionView;

        initConfig();
        initView(getContentView());
        initData();
    }

    protected abstract void initView(View view);
    protected abstract void initData();

    private void initConfig(){
        this.setFocusable(true);
        this.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        this.setOutsideTouchable(true);
        this.setBackgroundDrawable(new ColorDrawable(0x77000000));
//        this.setAnimationStyle(R.style.popwin_anim_style);// pop动画
    }

    @Override
    public void onClick(View v) {

    }
}
