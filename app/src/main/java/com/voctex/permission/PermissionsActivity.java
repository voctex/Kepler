package com.voctex.permission;

import android.Manifest;
import android.os.Bundle;
import android.view.View;

import com.voctex.R;
import com.voctex.tools.VtLog;
import com.voctex.tools.VtToast;

import java.util.List;

/**
 * Created by Voctex on 2016/9/1.
 */

public class PermissionsActivity extends BasePermsActivity implements View.OnClickListener{

    private static final int RC_CONTACT=23;
    private static final int RC_SMS=24;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uia_permission_main);

        initView();
    }
    private void initView(){
        findViewById(R.id.permission_contact).setOnClickListener(this);
        findViewById(R.id.permission_sms).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.permission_contact:{
                contact();
                break;
            }
            case R.id.permission_sms:{

                sms();
                break;
            }
        }
    }
    @AfterPermissionGranted(value = RC_CONTACT)
    private void contact(){
        if (EasyPermissions.hasPermissions(this, Manifest.permission.READ_CONTACTS)){
            //grant permission
            VtToast.s(this,"哼哼，厉害不，我天生就拥有权限的");
        }else{
            EasyPermissions.requestPermissions(this,"grant",RC_CONTACT, Manifest.permission.READ_CONTACTS);
        }
    }

    @AfterPermissionGranted(value = RC_SMS)
    private void sms(){
        if (EasyPermissions.hasPermissions(this, Manifest.permission.READ_SMS, Manifest.permission.RECORD_AUDIO)){
            VtToast.s(this,"哼哼，厉害不，我天生就拥有两项权限的");
        }else{
            EasyPermissions.requestPermissions(this,"愚蠢的人类，赶紧赋予我权限吧，我能帮助更好的运行程序",RC_SMS,
                    Manifest.permission.READ_SMS, Manifest.permission.RECORD_AUDIO);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        super.onPermissionsGranted(requestCode, perms);
        if (requestCode==RC_SMS) {
            VtToast.s(this, "噢，真是太幸福，居然给我赋予了权限");
            for (String ss: perms){
                VtLog.i("ss"+ss);
            }
        }
    }

    @Override
    protected void permsResult() {
        super.permsResult();
        VtToast.s(this,"回调的结果走这里");
        sms();
    }
}
