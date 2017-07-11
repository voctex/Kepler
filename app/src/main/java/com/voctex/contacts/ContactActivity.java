package com.voctex.contacts;

import android.Manifest;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.voctex.R;
import com.voctex.base.BaseActivity;
import com.voctex.tools.RCPerms;
import com.voctex.tools.VtToast;
import com.voctex.ui.tablayout.adapter.TabLayoutAdapter;

import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by Voctex on 2016/8/29.
 * 联系人授权并显示，动态权限申请
 */
public class ContactActivity extends BaseActivity implements View.OnClickListener/*,JavaScriptObject.OnJsListener*/ {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uia_contact_main);

        applyContactPms();

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


        FloatingActionButton fab= ((FloatingActionButton) findViewById(R.id.contact_fab));
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

    @AfterPermissionGranted(RCPerms.RC_READ_CONTACTS)
    private void applyContactPms(){
        String[] perms={Manifest.permission.READ_CONTACTS};
        if (EasyPermissions.hasPermissions(this,perms)){
            VtToast.s(this,"已经获得相应权限");
        }else{
            EasyPermissions.requestPermissions(this,"略略略",RCPerms.RC_READ_CONTACTS,perms);
        }
    }

    private void initData(){

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
        }
    }

//    @Override
//    public void onJs(String data) {
//        webView.loadUrl("javascript:sendAllContact("+data+")");
//    }
}
