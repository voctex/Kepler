package com.voctex.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.voctex.R;
import com.voctex.base.BaseFragment;
import com.voctex.tools.SPUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class WebFragment extends BaseFragment {


    private ViewGroup view;

    public WebFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = (ViewGroup) inflater.inflate(R.layout.fragment_web, container, false);
        WebView webView= (WebView) view.findViewById(R.id.web_webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("http://www.html5tricks.com/demo/html5-svg-shanche-animation/index.html");
//        webView.loadUrl("file:///android_asset/ac/index.html");

        SPUtil.put(mContext,"text","key",123);
        SPUtil.get(mContext,"text","","");
        return view;
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_web;
    }

    @Override
    protected void initView() {

    }

}
