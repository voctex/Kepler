package com.voctex.contacts;

import com.voctex.R;
import com.voctex.base.BaseActivity;
import com.voctex.contacts.js.JavaScriptObject;
import com.voctex.tools.VtLog;

import android.os.Bundle;
import android.view.View;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

/**
 * Created by Voctex on 2016/8/29.
 */

public class ContactActivity extends BaseActivity implements View.OnClickListener,JavaScriptObject.OnJsListener {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uia_contact_main);

        initView();
    }

    private void initView() {
        LinearLayout topLayout= (LinearLayout) findViewById(R.id.uia_contact_toplayout);
        topLayout.setOnClickListener(this);

        webView = (WebView) findViewById(R.id.uia_contact_webview);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        JavaScriptObject javaScriptObject=new JavaScriptObject(this);
        javaScriptObject.setOnJsListener(this);

        webSettings.setAppCacheEnabled(false);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webView.addJavascriptInterface(javaScriptObject, "jsObj");


        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                webView.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }



        });
        webView.setWebChromeClient(new WebChromeClient() {

        });

        webView.loadUrl("http://www.baidu.com");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.uia_contact_toplayout:{
                break;
            }
        }
    }

    @Override
    public void onJs(String data) {
        webView.loadUrl("javascript:sendAllContact("+data+")");
    }
}
