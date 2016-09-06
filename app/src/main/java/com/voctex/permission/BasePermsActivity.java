package com.voctex.permission;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.voctex.R;
import com.voctex.base.BaseActivity;

import java.util.List;

/**
 * Created by Voctex on 2016/9/1.
 * 涉及到运行时权限的Activity
 */

public class BasePermsActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EasyPermissions.SETTINGS_REQ_CODE) {
            // Do something after user returned from app settings screen
            // Let's show Toast for example
//            Toast.makeText(this, "回调回来的结果", Toast.LENGTH_SHORT)
//                    .show();

            permsResult();
        }
    }

    protected void permsResult(){

    }


    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Log.d("tag_voctex", "onPermissionsDenied:" + requestCode + ":" + perms.size());

        // (Optional) Check whether the user denied permissions and checked NEVER ASK AGAIN.
        // This will display a dialog directing them to enable the permission in app settings.
        EasyPermissions.checkDeniedPermissionsNeverAskAgain(this, "授权啊大哥，不授权没法用啊",
                R.string.permission_set, R.string.permission_cancel, null, perms);
    }
}
