package com.voctex.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import me.yokeyword.fragmentation.SupportFragment;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by mac_xihao on 17/6/29.
 */
public abstract class UniversalFragment extends SupportFragment implements EasyPermissions.PermissionCallbacks {

    protected ViewGroup mViewGroup;
    protected Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (null != mViewGroup || savedInstanceState != null) {
            if (mViewGroup == null) {
                mViewGroup = baseCreateView(inflater, container, savedInstanceState);
            }else {
                ViewGroup parent = (ViewGroup) mViewGroup.getParent();
                if (null != parent) {
                    parent.removeView(mViewGroup);
                }
            }
        } else {
            mViewGroup = baseCreateView(inflater, container, savedInstanceState);
        }
        return mViewGroup;

    }

    /**
     * 抽象具体的子类界面
     */
    protected abstract ViewGroup baseCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // EasyPermissions handles the request result.
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            // Do something after user returned from app settings screen, like showing a Toast.

            // 申请权限回调回来的结果
            dealPermsResult();
        }
    }


    protected void dealPermsResult() {

    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {



        // (Optional) Check whether the user denied any permissions and checked "NEVER ASK AGAIN."
        // This will display a dialog directing them to enable the permission in app settings.
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }
}
