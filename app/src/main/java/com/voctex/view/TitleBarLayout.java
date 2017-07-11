package com.voctex.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.voctex.R;
import com.voctex.tools.StatusBarUtil;

/**
 * Created by mac_xihao on 17/7/6.
 *
 */
public class TitleBarLayout extends Toolbar {

    private Context mContext;
    public TitleBarLayout(Context context) {
        super(context);
        initView();
    }

    public TitleBarLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public TitleBarLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView(){

        if (getContext() instanceof Activity) {
            mContext=getContext();
        }else if (getContext() instanceof ContextThemeWrapper){
            mContext=((ContextThemeWrapper) getContext()).getBaseContext();
        }


        this.setNavigationIcon(R.drawable.selector_ic_back);
        this.setNavigationOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getContext() instanceof Activity) {
                    ((Activity) getContext()).finish();
                }else if (getContext() instanceof ContextThemeWrapper){
                    ((Activity) ((ContextThemeWrapper) getContext()).getBaseContext()).finish();
                }
            }
        });
        this.setTitle(R.string.app_name);
        this.setTitleTextColor(getContext().getResources().getColor(R.color.white));

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){

            if (Build.VERSION.SDK_INT<Build.VERSION_CODES.LOLLIPOP){
                WindowManager.LayoutParams localLayoutParams= ((Activity) getContext()).getWindow().getAttributes();
                localLayoutParams.flags=WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS|localLayoutParams.flags;
            }else{
                if (getContext() instanceof Activity) {
                    Window window = ((Activity) getContext()).getWindow();
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    window.setStatusBarColor(Color.TRANSPARENT);
                }else if (getContext() instanceof ContextThemeWrapper){
                    Window window = ((Activity) ((ContextThemeWrapper) getContext()).getBaseContext()).getWindow();
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    window.setStatusBarColor(Color.TRANSPARENT);
                }

            }
            setFitsSystemWindows(true);
        }

    }


}
