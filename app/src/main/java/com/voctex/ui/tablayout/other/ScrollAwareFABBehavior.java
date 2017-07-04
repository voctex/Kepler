package com.voctex.ui.tablayout.other;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by mac_xihao on 17/7/3.
 * (～￣▽￣)～ 嘛哩嘛哩哄
 */
public class ScrollAwareFABBehavior extends FloatingActionButton.Behavior {


    /**
     * 因为是在XML中使用app:layout_behavior定义静态的这种行为,
     * 必须实现一个构造函数使布局的效果能够正常工作。
     * 否则 Could not inflate Behavior subclass error messages.
     *
     * @param context
     * @param attrs
     */
    public ScrollAwareFABBehavior(Context context, AttributeSet attrs) {
        super();
    }

    /**
     * 处理垂直方向上的滚动事件
     *
     * @param coordinatorLayout
     * @param child
     * @param directTargetChild
     * @param target
     * @param nestedScrollAxes
     * @return
     */
    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout,
                                       FloatingActionButton child, View directTargetChild, View target, int nestedScrollAxes) {

        // Ensure we react to vertical scrolling
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL ||
                super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target,
                        nestedScrollAxes);
    }

    /**
     * 检查Y的位置，并决定按钮是否动画进入或退出
     *
     * @param coordinatorLayout
     * @param child
     * @param target
     * @param dxConsumed
     * @param dyConsumed
     * @param dxUnconsumed
     * @param dyUnconsumed
     */
    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child,
                               View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed,
                dyUnconsumed);

        if (dyConsumed > 10 && child.getVisibility() == View.VISIBLE) {
            // User scrolled down and the FAB is currently visible -> hide the FAB
            //执行隐藏的动画
            hide(child);
        } else if (dyConsumed < -10 && child.getVisibility() != View.VISIBLE) {
            // User scrolled up and the FAB is currently not visible -> show the FAB
            //执行显示的动画
            show(child);
        }

    }

    /**
     * 显示的动画
     */
    private void show(final View view) {
        view.animate().cancel();

        // If the view isn't visible currently, we'll animate it from a single pixel
        view.setAlpha(0f);
        view.setScaleY(0f);
        view.setScaleX(0f);

        view.animate()
                .scaleX(1f)
                .scaleY(1f)
                .alpha(1f)
                .setDuration(200)
                .setInterpolator(new LinearOutSlowInInterpolator())
                .setListener(new AnimatorListenerAdapter() {

                    @Override
                    public void onAnimationStart(Animator animation) {
                        view.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {

                    }
                });
    }

    /**
     * 隐藏的动画
     */
    private void hide(final View view) {
        view.animate().cancel();
        view.animate()
                .scaleX(0f)
                .scaleY(0f)
                .alpha(0f)
                .setDuration(200)
                .setInterpolator(new FastOutLinearInInterpolator())
                .setListener(new AnimatorListenerAdapter() {
                    private boolean mCancelled;

                    @Override
                    public void onAnimationStart(Animator animation) {
                        view.setVisibility(View.VISIBLE);
                        mCancelled = false;
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        mCancelled = true;
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (!mCancelled) {
                            view.setVisibility(View.INVISIBLE);
                        }
                    }
                });
    }
}