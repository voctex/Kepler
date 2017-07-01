package com.voctex.contacts;

import com.voctex.R;
import com.voctex.base.BaseActivity;
import com.voctex.contacts.js.JavaScriptObject;
import com.voctex.tools.VtLog;
import com.voctex.ui.tablayout.adapter.TabLayoutAdapter;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Voctex on 2016/8/29.
 */
public class ContactActivity extends BaseActivity implements View.OnClickListener/*,JavaScriptObject.OnJsListener*/ {
//    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uia_contact_main);

        initView();

        initData();
    }

    private void initView() {

        RecyclerView recyclerView= ((RecyclerView) findViewById(R.id.contact_recyclerview));

        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        //设置一个垂直方向的layout manager
        int orientation = LinearLayoutManager.VERTICAL;
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, orientation, false));

        List<String> mList=new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            mList.add("位置为："+i);
        }

        TabLayoutAdapter tabLayoutAdapter=new TabLayoutAdapter(recyclerView,mList);

        recyclerView.setAdapter(tabLayoutAdapter);

//        LinearLayout topLayout= (LinearLayout) findViewById(R.id.uia_contact_toplayout);
//        topLayout.setOnClickListener(this);
//
//        webView = (WebView) findViewById(R.id.uia_contact_webview);
//        WebSettings webSettings = webView.getSettings();
//        webSettings.setJavaScriptEnabled(true);
//
//        JavaScriptObject javaScriptObject=new JavaScriptObject(this);
//        javaScriptObject.setOnJsListener(this);
//
//        webSettings.setAppCacheEnabled(false);
//        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
//        webView.addJavascriptInterface(javaScriptObject, "jsObj");
//
//
//        webView.setWebViewClient(new WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                webView.loadUrl(url);
//                return super.shouldOverrideUrlLoading(view, url);
//            }
//
//
//
//        });
//        webView.setWebChromeClient(new WebChromeClient() {
//
//        });
//
//        webView.loadUrl("http://www.baidu.com");
    }

    private void initData(){

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
//            case R.id.uia_contact_toplayout:{
//                break;
//            }
        }
    }

//    @Override
//    public void onJs(String data) {
//        webView.loadUrl("javascript:sendAllContact("+data+")");
//    }
}
