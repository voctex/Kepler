package com.voctex.view.slidetab;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.voctex.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by voctex 2016/07/15
 * This view is contain the function of the sort and viewpager
 */
public class SlideTabView extends LinearLayout {

    private List<SlideTabBean> mList = new ArrayList<SlideTabBean>();

    /**
     * 存头部文本和排序小图标的layout视图
     */
    private SparseArray<View> titleVArr = new SparseArray<>();
    /**
     * 存头部分割线
     */
    private SparseArray<View> lineVArr = new SparseArray<>();
    /**
     * Tab的那个引导线
     */
    private ImageView mTopLine/*, mTopBottomDivide*/;
    private NoScrollViewPager vPager;

    public NoScrollViewPager getvPager() {
        return vPager;
    }

    /**
     * ViewPager的当前选中页
     */
    private int currentIndex;
    /**
     * 屏幕的宽度
     */
    private int screenWidth;
    /**
     * 设置滑动到指定的页面或者点击text选中的页面，title的text颜色
     */
    private int seleteTextColor = getResources()
            .getColor(R.color.st_title_text_select);
    /**
     * title所有的text颜色
     */
    private int titleTextColor = getResources().getColor(
            R.color.st_title_text_normal);
    /**
     * 判断是否有走过onPageScrolled这个方法
     */
    private boolean isPassPageScrolled = false;


    /**
     * 标线是否可以跟着手势滑动
     */
    private boolean canSlideForTabLine = false;

    public void setCanSlideForTabLine(boolean canSlideForTabLine) {
        this.canSlideForTabLine = canSlideForTabLine;
    }

    /**
     * @param mContext
     * @param mList    在java文件里面new出该控件的走这里
     */
    public SlideTabView(Context mContext, List<SlideTabBean> mList,
                        FragmentManager fragmentManager) {
        super(mContext);

        initAll(mList, fragmentManager);

    }

    public SlideTabView(Context context) {
        super(context);
        init();
    }

    /**
     * @param mContext
     * @param attr     xml文件走这里
     */
    public SlideTabView(Context mContext, AttributeSet attr) {
        super(mContext, attr);

        init();
    }

    public SlideTabView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(21)
    public SlideTabView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {

    }

    /**
     * @param mList 由传入的list来决定整个数量
     *              （头部text，viewpager个数）
     */
    public void initAll(@Nullable List<SlideTabBean> mList,
                        FragmentManager fragmentManager) {

        this.mList = mList;

        initView();

        LinearLayout topLayout = getTitleView();
        initTabLineWidth();
        initViewPager(fragmentManager);


        addView(topLayout, new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));
        this.addView(vPager);


//        this.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT));
        this.setOrientation(LinearLayout.VERTICAL);
    }

    /**
     * 设置头部视图与滑动视图中间的视图，称之为脖子视图
     */
    public void setNeckView(View v) {
        addView(v, 1);
        addView(getLineView(), 2);
    }


    /**
     * 初始化所有控件
     */
    private void initView() {
        LayoutParams mmwParams = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        mmwParams.weight = 1;
        LayoutParams lineParams = new LayoutParams(
                LayoutParams.MATCH_PARENT, getResources()
                .getDimensionPixelSize(R.dimen.st_title_cursor));

        vPager = new NoScrollViewPager(getContext());
        vPager.setLayoutParams(mmwParams);
        vPager.setId(R.id.st_viewpager);


        mTopLine = new ImageView(getContext());
        mTopLine.setScaleType(ScaleType.FIT_XY);
        mTopLine.setLayoutParams(lineParams);
        mTopLine.setBackgroundColor(seleteTextColor);

    }

    /**
     * 初始化头部的所有text，并加入toplayout
     */
    private LinearLayout getTitleView() {

        LinearLayout titleView = new LinearLayout(getContext());
        titleView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));
        titleView.setGravity(Gravity.CENTER_VERTICAL);

        LayoutParams params = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        // params.gravity = Gravity.CENTER;
        params.weight = 1;
        for (int i = 0; i < mList.size() * 2 - 1; i++) {

            if (i % 2 == 1) {
                ImageView img = new ImageView(getContext());
                img.setImageResource(R.mipmap.st_title_divide);
                img.setScaleType(ScaleType.FIT_XY);
                titleView.addView(img, LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT);
                lineVArr.put(i / 2 - 1, img);
            } else {
                LinearLayout titleItemLayout = new LinearLayout(getContext());
                titleItemLayout.setLayoutParams(params);
                titleItemLayout.setOrientation(LinearLayout.HORIZONTAL);
                titleItemLayout.setGravity(Gravity.CENTER);

                TextView textView = new TextView(getContext());
                textView.setText(mList.get(i / 2).getText());
                textView.setTextColor(getResources().getColor(
                        R.color.st_title_text_normal));
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                textView.setSingleLine();
                textView.setGravity(Gravity.CENTER);
                int textPadding = (int) getResources().getDimension(
                        R.dimen.st_title_padding);
                textView.setPadding(0, textPadding, 0, textPadding);
                textView.setEllipsize(TruncateAt.END);

                ImageView imageView = new ImageView(getContext());
                imageView.setScaleType(ScaleType.FIT_XY);
                imageView.setVisibility(View.GONE);

                if (i == 0) {
                    imageView.setBackgroundResource(R.mipmap.st_shelves_sort_up);
                    textView.setTextColor(seleteTextColor);
                    imageView.setTag(OnOrderChangeListener.ORDER_STATUS_ASC);
                } else {
                    imageView
                            .setBackgroundResource(R.mipmap.st_shelves_sort_default);
                    textView.setTextColor(titleTextColor);
                    imageView.setTag(OnOrderChangeListener.ORDER_STATUS_NO);
                }
                titleItemLayout.addView(textView,
                        LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT);
                titleVArr.put(i / 2, titleItemLayout);

                titleItemLayout.addView(imageView,
                        LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT);
                titleItemLayout.setOnClickListener(new OnClick(i / 2));

                titleView.addView(titleItemLayout);
            }
        }
        LinearLayout topLayout = new LinearLayout(getContext());
        topLayout.setOrientation(LinearLayout.VERTICAL);

        topLayout.addView(titleView, LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        topLayout.addView(mTopLine);

        topLayout.addView(getLineView(), LayoutParams.MATCH_PARENT,
                (int) getResources().getDimension(R.dimen.st_divide_line));

        return topLayout;
    }

    /**
     * 获得一个水平横线的视图
     */
    private View getLineView() {
        View tbLine = new View(getContext());
        tbLine.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, getResources().getDimensionPixelSize(R.dimen.st_divide_line)));
        tbLine.setBackgroundColor(getResources().getColor(R.color.st_line));
        return tbLine;
    }

    /**
     * @author Voctex 点击事件
     */
    private class OnClick implements OnClickListener {
        int index;

        public OnClick(int index) {
            // TODO Auto-generated method stub
            this.index = index;
        }

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            int currentItem = vPager.getCurrentItem();

            // ====
            if (getOrderViewVisiable()) {
                ImageView beforeView = (ImageView) ((LinearLayout) titleVArr.get(currentItem))
                        .getChildAt(1);
                ImageView afterView = (ImageView) ((LinearLayout) titleVArr.get(index)).getChildAt(1);
                int beforeStatus = (Integer) afterView.getTag();

                if (index == currentItem) {
                    // 不切换片段的情况下，点击头部
                    switch (beforeStatus) {
                        // case OnOrderChangeListener.ORDER_STATUS_NO:
                        case OnOrderChangeListener.ORDER_STATUS_ASC:// 升序情况下，改为降序
                            afterView
                                    .setBackgroundResource(R.mipmap.st_shelves_sort_down);
                            afterView
                                    .setTag(OnOrderChangeListener.ORDER_STATUS_DESC);
                            onOrderChange(this.index,
                                    OnOrderChangeListener.ORDER_STATUS_DESC);
                            break;
                        case OnOrderChangeListener.ORDER_STATUS_DESC:// 降序情况下，改为升序
                            afterView
                                    .setBackgroundResource(R.mipmap.st_shelves_sort_up);
                            afterView
                                    .setTag(OnOrderChangeListener.ORDER_STATUS_ASC);
                            onOrderChange(this.index,
                                    OnOrderChangeListener.ORDER_STATUS_ASC);
                            break;

                    }
                } else {
                    beforeView
                            .setBackgroundResource(R.mipmap.st_shelves_sort_default);
                    // 点击头部切换片段的情况下
                    switch (beforeStatus) {
                        case OnOrderChangeListener.ORDER_STATUS_ASC:
                            afterView
                                    .setBackgroundResource(R.mipmap.st_shelves_sort_up);
                            afterView
                                    .setTag(OnOrderChangeListener.ORDER_STATUS_ASC);
                            break;
                        case OnOrderChangeListener.ORDER_STATUS_DESC:
                            afterView
                                    .setBackgroundResource(R.mipmap.st_shelves_sort_down);
                            afterView
                                    .setTag(OnOrderChangeListener.ORDER_STATUS_DESC);
                            break;
                        case OnOrderChangeListener.ORDER_STATUS_NO:
                            afterView
                                    .setBackgroundResource(R.mipmap.st_shelves_sort_up);
                            afterView
                                    .setTag(OnOrderChangeListener.ORDER_STATUS_ASC);
                            break;
                    }
                }
            }
            vPager.setCurrentItem(index);

        }

        private void onOrderChange(int index, int orderStatus) {

            if (onOrderChangeListener != null) {
                onOrderChangeListener.onOrderChange(index, orderStatus);
            }
        }
    }

    /**
     * viewpager控件的处理
     */
    private void initViewPager(FragmentManager fragmentManager) {

        FragmentPagerAdapter mfAdapter = new FragmentPagerAdapter(
                fragmentManager) {

            @Override
            public int getCount() {
                // TODO Auto-generated method stub
                return mList.size();
            }

            @Override
            public Fragment getItem(int position) {
                // TODO Auto-generated method stub
                return mList.get(position).getFragment();
            }
        };

        vPager.setAdapter(mfAdapter);
        vPager.setCurrentItem(vPager.getCurrentItem());
        vPager.addOnPageChangeListener(new MyOnPageChange(mList.size()));


    }

    public class MyOnPageChange implements OnPageChangeListener {

        private int titleSize;

        public MyOnPageChange(int titleSize) {
            this.titleSize = titleSize;
        }

        /**
         * state滑动中的状态 有三种状态（0，1，2） 1：正在滑动 2：滑动完毕 0：什么都没做。
         */
        @Override
        public void onPageScrollStateChanged(int state) {
            if (fragAcPagerChange != null) {
                fragAcPagerChange.onPageScrollStateChanged(state);
            }
        }

        /**
         * position :当前页面，及你点击滑动的页面 offset:当前页面偏移的百分比 offsetPixels:当前页面偏移的像素位置
         */
        @Override
        public void onPageScrolled(int position, float offset, int offsetPixels) {
            isPassPageScrolled = true;
//            LayoutParams lp = (LayoutParams) mTopLine
//                    .getLayoutParams();

            if (canSlideForTabLine) {


                int cursor = (int) mTopLine.getTranslationX();

                /**
                 * 这个position，往左方向会减一，往右方向不会加一，而是会等于currentIndex
                 * 利用currentIndex(当前所在页面)和position(下一个页面)以及offset来 设置mTabLineIv的左边距
                 * 滑动场景： 记3个页面, 从左到右分别为0,1,2 0->1; 1->2; 2->1; 1->0
                 */
                if (currentIndex == position && currentIndex < this.titleSize) {
                    cursor = (int) (offset * (screenWidth * 1.0 / this.titleSize) + currentIndex
                            * (screenWidth / this.titleSize))
                    ;
//				cursor = (int) (offset * (screenWidth * 1.0 / this.titleSize) + currentIndex
//						* (screenWidth / this.titleSize))
//						+ lp.width / 2;
                } else if (currentIndex - position == 1
                        && currentIndex <= mList.size()) {
                    cursor = (int) (-(1 - offset)
                            * (screenWidth * 1.0 / this.titleSize) + currentIndex
                            * (screenWidth / this.titleSize))
                    ;
//				cursor = (int) (-(1 - offset)
//						* (screenWidth * 1.0 / this.titleSize) + currentIndex
//						* (screenWidth / this.titleSize))
//						+ lp.width / 2;
                }

                SlideTabView.this.mTopLine.setTranslationX(cursor);

            }
            if (fragAcPagerChange != null) {
                fragAcPagerChange
                        .onPageScrolled(position, offset, offsetPixels);
            }
        }

        @Override
        public void onPageSelected(int position) {
            resetTextView();
            LinearLayout linearLayout = (LinearLayout) titleVArr.get(position);
            TextView textView = (TextView) linearLayout.getChildAt(0);
            textView.setTextColor(seleteTextColor);

            if (!isPassPageScrolled) {
                isPassPageScrolled = true;
//                LayoutParams lp = (LayoutParams) mTopLine
//                        .getLayoutParams();
//                lp.leftMargin = lp.width * position;
//                mTopLine.setLayoutParams(lp);
                int positionForLine=position*getTabLineWidth();
                SlideTabView.this.mTopLine.setTranslationX(positionForLine);
            }

            if (!canSlideForTabLine) {
                int positionForLine=position*getTabLineWidth();
                SlideTabView.this.mTopLine.setTranslationX(positionForLine);
            }

            // ====
            if (getOrderViewVisiable()) {
                ImageView beforeView = (ImageView) ((LinearLayout) titleVArr.get(currentIndex)).getChildAt(1);
                ImageView afterView = (ImageView) ((LinearLayout) titleVArr.get(position)).getChildAt(1);
                int beforeStatus = (Integer) afterView.getTag();

                beforeView
                        .setBackgroundResource(R.mipmap.st_shelves_sort_default);
                switch (beforeStatus) {
                    case OnOrderChangeListener.ORDER_STATUS_ASC:
                        afterView.setBackgroundResource(R.mipmap.st_shelves_sort_up);
                        afterView.setTag(OnOrderChangeListener.ORDER_STATUS_ASC);
                        break;
                    case OnOrderChangeListener.ORDER_STATUS_DESC:
                        afterView
                                .setBackgroundResource(R.mipmap.st_shelves_sort_down);
                        afterView.setTag(OnOrderChangeListener.ORDER_STATUS_DESC);
                        break;
                    case OnOrderChangeListener.ORDER_STATUS_NO:
                        afterView.setBackgroundResource(R.mipmap.st_shelves_sort_up);
                        afterView.setTag(OnOrderChangeListener.ORDER_STATUS_ASC);
                        break;
                }
            }

            if (fragAcPagerChange != null) {
                fragAcPagerChange.onPageSelected(position);
            }
            if (onItemChangeListener != null) {
                onItemChangeListener.currentItem(position);
            }

            currentIndex = position;
        }
    }

    public void setOnPageChangeListener(MyOnPageChange myOnPageChange) {
        vPager.addOnPageChangeListener(myOnPageChange);
    }

    /**
     * 设置滑动条的宽度为屏幕的长度除以list的长度(根据Tab的个数而定)
     */
    private void initTabLineWidth() {

        LayoutParams lp = (LayoutParams) mTopLine
                .getLayoutParams();
        lp.width = getTabLineWidth();
        mTopLine.setLayoutParams(lp);


    }

    /**
     * 获得滑动标线的宽度
     */
    private int getTabLineWidth() {
        screenWidth = getWindowWidth((Activity) getContext());

        Log.i("", "screenWidth==" + screenWidth);

        int width = screenWidth / mList.size();

        return width;
    }

    /**
     * 重置头部所有textview的textcolor
     */
    private void resetTextView() {
        for (int i = 0; i < titleVArr.size(); i++) {
            LinearLayout linearLayout = (LinearLayout) titleVArr.get(i);
            TextView textView = (TextView) linearLayout.getChildAt(0);
            textView.setTextColor(titleTextColor);
        }
    }

    /**
     * 显示滑动头部排序小图标的方法
     */
    public void setOrderViewVisiable() {
        for (int i = 0; i < titleVArr.size(); i++) {
            LinearLayout linearLayout = (LinearLayout) titleVArr.get(i);
            ImageView imageView = (ImageView) linearLayout.getChildAt(1);
            imageView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 设置当前控件是否可操作
     */
    public void setOperateStatus(boolean flag) {
        for (int i = 0; i < titleVArr.size(); i++) {
            LinearLayout linearLayout = (LinearLayout) titleVArr.get(i);
            if (flag) {
                linearLayout.setOnClickListener(new OnClick(i / 2));
            } else {
                linearLayout.setOnClickListener(null);
            }
        }
        vPager.setNoScroll(flag);
    }

    /**
     * @return 获得排序的头部小图标是否显示
     */
    private boolean getOrderViewVisiable() {
        LinearLayout linearLayout = (LinearLayout) titleVArr.get(0);
        ImageView imageView = (ImageView) linearLayout.getChildAt(1);
        if (imageView.getVisibility() == View.VISIBLE) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 重置头部所有textview的textcolor
     */
    public void setTitleText(int position, String text) {
        LinearLayout linearLayout = (LinearLayout) titleVArr.get(position);
        TextView textView = (TextView) linearLayout.getChildAt(0);
        textView.setText(text);
    }

    /**
     * @param color 设置滑动条的颜色
     */
    public void setLineColor(int color) {
        mTopLine.setBackgroundColor(color);
    }

    /**
     * 隐藏头部title的文本之间的分隔线
     */
    public void setTopDivideHide() {
        for (int i = 0; i < lineVArr.size(); i++) {
            ImageView imageView = (ImageView) lineVArr.get(i);
            imageView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * @param height 设置title的高度
     */
    public void setTopLayoutHeight(int height) {

        LinearLayout topLayout = (LinearLayout) getChildAt(0);

        LinearLayout titleLayout = (LinearLayout) topLayout.getChildAt(0);

        LayoutParams params = (LayoutParams) titleLayout
                .getLayoutParams();
        params.height = dip2px(getContext(), height);
        titleLayout.setLayoutParams(params);
    }

    /**
     * 设置头部文本内边距
     */
    public void setTitlePadding(int l, int t, int r, int b) {
        for (int i = 0; i < titleVArr.size(); i++) {
            LinearLayout linearLayout = (LinearLayout) titleVArr.get(i);
            TextView textView = (TextView) linearLayout.getChildAt(0);
            textView.setPadding(dip2px(getContext(), l), dip2px(getContext(), t), dip2px(getContext(), r), dip2px(getContext(), b));
        }
    }


    /**
     * @param color 设置选中的text的颜色
     */
    public void setSeleteTextColor(int color) {
        this.seleteTextColor = color;
        for (int i = 0; i < titleVArr.size(); i++) {
            if (i == currentIndex) {
                LinearLayout linearLayout = (LinearLayout) titleVArr.get(i);
                TextView textView = (TextView) linearLayout.getChildAt(0);
                textView.setTextColor(seleteTextColor);
            }
        }
    }

    /**
     * @param color 设置头部所有的text的颜色
     */
    public void setTitleTextColor(int color) {
        this.titleTextColor = color;
        for (int i = 0; i < titleVArr.size(); i++) {
            LinearLayout linearLayout = (LinearLayout) titleVArr.get(i);
            TextView textView = (TextView) linearLayout.getChildAt(0);
            if (i == 0) {
                textView.setTextColor(seleteTextColor);
            } else {
                textView.setTextColor(titleTextColor);
            }
        }
    }

    /**
     * @param size 设置头部所有text的size,default--size=18
     */
    public void setTitleTextSize(int size) {
        for (int i = 0; i < titleVArr.size(); i++) {
            LinearLayout linearLayout = (LinearLayout) titleVArr.get(i);
            TextView textView = (TextView) linearLayout.getChildAt(0);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
        }

    }

    /**
     * 设置头部layout是否显示
     * ，默认为true显示，false为不显示
     */
    public void setTopLayoutShow(boolean flag) {
        if (flag) {
            getChildAt(0).setVisibility(View.VISIBLE);
        } else {
            getChildAt(0).setVisibility(View.GONE);
        }
    }

    /**
     * 设置头部top背景颜色
     */
    public void setTopBackgroud(int color) {
        getChildAt(0).setBackgroundResource(color);
    }

    /**
     * @author cxh
     *         回调有关viewpager的OnPageChangeListener的方法
     */
    public interface FragAcPagerChange {
        void onPageScrollStateChanged(int state);

        void onPageScrolled(int position, float offset, int offsetPixels);

        void onPageSelected(int position);
    }

    private FragAcPagerChange fragAcPagerChange;

    public void setFragAcPagerChange(FragAcPagerChange fragAcPagerChange) {
        this.fragAcPagerChange = fragAcPagerChange;
    }

    /**
     * 头部升降序监听接口
     */
    public interface OnOrderChangeListener {

        /***
         * 升序
         */
        int ORDER_STATUS_ASC = 1234567;
        /***
         * 降序
         */
        int ORDER_STATUS_DESC = 7654321;
        /***
         * 切换过来的，不操作
         */
        int ORDER_STATUS_NO = 3333333;

        void onOrderChange(int clickItem, int orderStatus);
    }

    private OnOrderChangeListener onOrderChangeListener;

    public void setOnOrderChangeListener(
            OnOrderChangeListener onOrderChangeListener) {
        this.onOrderChangeListener = onOrderChangeListener;
    }

    /**
     * 返回当前片段的位置（即第几个片段）
     */
    public interface OnItemChangeListener {
        void currentItem(int index);
    }

    private OnItemChangeListener onItemChangeListener;

    public void setOnItemChangeListener(
            OnItemChangeListener onItemChangeListener) {
        this.onItemChangeListener = onItemChangeListener;
    }

    @SuppressLint("ClickableViewAccessibility")
    public class NoScrollViewPager extends ViewPager {

        /**
         * 默认是打开的，可以滑动
         */
        private boolean noScroll = true;

        public NoScrollViewPager(Context context, AttributeSet attrs) {
            super(context, attrs);
            // TODO Auto-generated constructor stub
        }

        public NoScrollViewPager(Context context) {
            super(context);
        }

        // 设置viewpager是否可以滑动
        public void setNoScroll(boolean noScroll) {
            this.noScroll = noScroll;
        }

        @Override
        public void scrollTo(int x, int y) {
            super.scrollTo(x, y);
        }

        @Override
        public boolean onTouchEvent(MotionEvent arg0) {
            /* return false;//super.onTouchEvent(arg0); */
            if (!noScroll)
                return false;
            else
                return super.onTouchEvent(arg0);
        }

        @Override
        public boolean onInterceptTouchEvent(MotionEvent arg0) {
            if (!noScroll)
                return false;
            else
                return super.onInterceptTouchEvent(arg0);
        }

        @Override
        public void setCurrentItem(int item, boolean smoothScroll) {
            super.setCurrentItem(item, smoothScroll);
        }

        @Override
        public void setCurrentItem(int item) {
            super.setCurrentItem(item);
        }

    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     *
     * @param dipValue scale
     *                 （DisplayMetrics类中属性density）
     * @return
     */
    private int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 屏幕宽度（像素）
     */
    private int getWindowWidth(Activity context) {
        DisplayMetrics metric = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(metric);
        return metric.widthPixels;
    }

}
