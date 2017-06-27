package com.voctex.view.slidetab;

import android.support.v4.app.Fragment;

import java.io.Serializable;

/**
 * Created by voctex on 2016/07/15
 * */
public class SlideTabBean implements Serializable {


    private String text;
    private Fragment fragment;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

}
