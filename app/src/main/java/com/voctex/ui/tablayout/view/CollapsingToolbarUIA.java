package com.voctex.ui.tablayout.view;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.OnApplyWindowInsetsListener;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.WindowInsetsCompat;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.voctex.R;
import com.voctex.base.BaseActivity;
import com.voctex.tools.VtToast;
import com.voctex.view.TitleBarLayout;

/**
 * Created by mac_xihao on 17/6/28.
 * 可伸缩的toolbar界面
 */
public class CollapsingToolbarUIA extends BaseActivity {

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uia_collapsingtoolbar_main);
        initView();
    }

    private void initView(){
        TitleBarLayout toolbar= ((TitleBarLayout) findViewById(R.id.collapsing_toolbar));
        toolbar.setNavigationIcon(R.mipmap.ic_launcher);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //使用CollapsingToolbarLayout必须把title设置到CollapsingToolbarLayout上，设置到Toolbar上则不会显示
        CollapsingToolbarLayout mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);
        mCollapsingToolbarLayout.setTitle("这是标题");
        //通过CollapsingToolbarLayout修改字体颜色
        mCollapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);//设置还没收缩时状态下字体颜色
        mCollapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);//设置收缩后Toolbar上字体的颜色
        //toolbar navigationicon 改变返回按钮颜色
//        final Drawable upArrow = getResources().getDrawable(R.drawable.vt_arrow);
//        upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
//        getSupportActionBar().setHomeAsUpIndicator(upArrow);

//        mCollapsingToolbarLayout.setClipToPadding(false);

//        ViewCompat.setOnApplyWindowInsetsListener(mCollapsingToolbarLayout, new OnApplyWindowInsetsListener() {
//            @Override
//            public WindowInsetsCompat onApplyWindowInsets(View v, WindowInsetsCompat insets) {
//                //insets.replaceSystemWindowInsets(0, 0, 0, 0); 该行代码无效
//                //  return insets.consumeSystemWindowInsets();
//                return insets;
//            }
//        });


        mWebView = (WebView) findViewById(R.id.collapsing_webview);
        //设置支持js
        mWebView.getSettings().setJavaScriptEnabled(true);
        //!!设置跳转的页面始终在当前WebView打开
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.loadUrl("http://www.baidu.com");


        //

        FloatingActionButton fab= ((FloatingActionButton) findViewById(R.id.collapsing_fab));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v,"floatingActionBtn",Snackbar.LENGTH_SHORT)
                        .setAction("action", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                VtToast.s(mContext,"仙剑奇侠传");
                            }
                        }).show();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack(); // goBack()表示返回WebView的上一页面
            return true;
        } else {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
