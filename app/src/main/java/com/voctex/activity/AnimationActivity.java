package com.voctex.activity;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.voctex.R;
import com.voctex.base.BaseActivity;

/**
 * 显示动画的界面
 * */
public class AnimationActivity extends BaseActivity {

    private ImageView arcView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        initView();

        setAnimation();
    }

    private void initView(){
        arcView = (ImageView) findViewById(R.id.anim_arc);
    }
    private void setAnimation(){
        RotateAnimation rotateAnimation=new RotateAnimation(0f,360f, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF,0.5f);
        rotateAnimation.setDuration(1000);
        rotateAnimation.setRepeatCount(-1);
//        rotateAnimation.setInterpolator(new LinearInterpolator());//设置匀速
//        rotate.startAnimation(rotateAnimation);
        arcView.startAnimation(rotateAnimation);
    }
}
