package com.voctex.activity;

import android.os.Bundle;
import android.os.PowerManager;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.voctex.R;
import com.voctex.base.BaseActivity;

/**
 * 显示动画的界面
 */
public class AnimationActivity extends BaseActivity {

    private ImageView arcView;
    private PowerManager.WakeLock wakeLock = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        initView();

        setAnimation();

    }

    private void initView() {
        arcView = (ImageView) findViewById(R.id.anim_arc);
    }

    private void setAnimation() {
        RotateAnimation rotateAnimation = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(1000);
        rotateAnimation.setRepeatCount(-1);
//        rotateAnimation.setInterpolator(new LinearInterpolator());//设置匀速
//        rotate.startAnimation(rotateAnimation);
        arcView.startAnimation(rotateAnimation);
    }


    private void acquireWakeLock() {
        if (null == wakeLock) {
            PowerManager powerManager = (PowerManager) this.getSystemService(POWER_SERVICE);
            wakeLock = powerManager.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ON_AFTER_RELEASE, "PlayService");
            if (null != wakeLock) {
                wakeLock.acquire();
            }
        }
    }

    private void releaseWakeLock() {
        if (null != wakeLock) {
            wakeLock.release();
            wakeLock = null;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        acquireWakeLock();
    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseWakeLock();
    }
}
