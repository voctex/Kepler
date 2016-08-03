package com.voctex.view.classify;

import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.voctex.R;
import com.voctex.view.LineGridView;
import com.voctex.view.spanner.VtSpinnerBaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by voctex on 2016/7/18.
 */
public class VtClassifyView extends LinearLayout implements View.OnTouchListener {

    private LineGridView gridView;

    public VtClassifyView(Context context) {
        super(context);
        initSpinnerView(null);
        initView();
    }

    public VtClassifyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initSpinnerView(attrs);
        initView();
    }

    public VtClassifyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initSpinnerView(attrs);
        initView();
    }

    @TargetApi(21)
    public VtClassifyView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initSpinnerView(attrs);
        initView();
    }

    private static final int MAX_LEVEL = 10000;
    private static final int DEFAULT_ELEVATION = 16;
    private static final String INSTANCE_STATE = "instance_state";
    private static final String SELECTED_INDEX = "selected_index";
    private static final String IS_POPUP_SHOWING = "is_popup_showing";

    private int selectedIndex;
    private int textColor;
    private int backgroundSelector;
    private int textNum = 4;
    private int[] displays = new int[2];

    private boolean isArrowHide;
    private Drawable drawable;
    private PopupWindow popupWindow;
    private VtClassifyBaseAdapter adapter;

    private List<String> mList = new ArrayList<>();
    private SparseArray<View> textVArr = new SparseArray<>();


    private AdapterView.OnItemClickListener onItemClickListener;
    private AdapterView.OnItemSelectedListener onItemSelectedListener;

    private void initView() {
        setOrientation(LinearLayout.HORIZONTAL);
        setBackgroundColor(Color.WHITE);

        LinearLayout rightLayout = new LinearLayout(getContext());
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        layoutParams.weight = 1;
//        layoutParams.leftMargin=getResources().getDimensionPixelOffset(R.dimen.vt_item_l_pd)/2;
        rightLayout.setLayoutParams(layoutParams);
        rightLayout.setPadding(getResources().getDimensionPixelOffset(R.dimen.vt_item_l_pd) / 2, 0, 0, 0);


        for (int i = 0; i < textNum; i++) {
            TextView textView = new TextView(getContext());
            LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            params.weight = 1;
            textView.setGravity(Gravity.CENTER);
            textView.setLayoutParams(params);
            textView.setTextColor(textColor);
//            textView.setText("哈哈" + i);
//            textView.setBackgroundResource(backgroundSelector);
//            textView.setOnClickListener(new OnClick(i));
            rightLayout.addView(textView);
            textVArr.put(i, textView);
        }
        addView(rightLayout);

    }
    /**设置右边的textview*/
    private void setRightLayoutTextView(List<String> mList) {
        int count = mList.size();
        for (int i = 0; i < textVArr.size(); i++) {
            TextView textView = (TextView) textVArr.get(i);
            if (i < count) {
                textView.setOnClickListener(new OnClick(i));
                textView.setText(mList.get(i));
                textView.setBackgroundResource(backgroundSelector);
            } else {
                textView.setOnClickListener(null);
                textView.setText("");
                textView.setBackgroundResource(0);
            }
        }
    }


    private void initSpinnerView(AttributeSet attrs) {
        getCurrentDisplaySize();

        TextView spinner = new TextView(getContext());
        spinner.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        spinner.setText("全部分类");
        spinner.setOnTouchListener(this);

        addView(spinner);

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.vt_classify);
        Resources resources = getResources();
        int tbPadding = resources.getDimensionPixelSize(R.dimen.vt_item_tb_pd);
        int lrPadding = resources.getDimensionPixelSize(R.dimen.vt_item_l_pd);

        spinner.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
        //设置内边距
        spinner.setPadding(lrPadding, tbPadding, lrPadding, tbPadding);
        spinner.setClickable(true);

        backgroundSelector = typedArray.getResourceId(R.styleable.vt_classify_text_bg, R.drawable.vt_selector_item);
        spinner.setBackgroundResource(backgroundSelector);
        textColor = typedArray.getColor(R.styleable.vt_classify_text_color, -1);
        spinner.setTextColor(textColor);


        gridView = new LineGridView(getContext());
        gridView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        gridView.setId(getId());
        gridView.setNumColumns(3);
//        gridView.setItemsCanFocus(true);
        //hide vertical and horizontal scrollbars
        gridView.setVerticalScrollBarEnabled(false);
        gridView.setHorizontalScrollBarEnabled(false);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // Need to set selected index before calling listeners or getSelectedIndex() can be
                // reported incorrectly due to race conditions.
                selectedIndex = position;

                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(parent, view, position, id);
                }

                if (onItemSelectedListener != null) {
                    onItemSelectedListener.onItemSelected(parent, view, position, id);
                }

                adapter.notifyItemSelected(position);
//                setText(adapter.getItemInDataset(position).toString());
                dismissDropDown();
            }
        });


        popupWindow = new PopupWindow(getContext());
        popupWindow.setContentView(gridView);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            popupWindow.setElevation(DEFAULT_ELEVATION);
            popupWindow.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.vt_spinner_drawable));
        } else {
            popupWindow.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.vt_spinner_drawable));
        }

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                if (!isArrowHide) {
                    animateArrow(false);
                }
            }
        });


        isArrowHide = typedArray.getBoolean(R.styleable.vt_classify_triangle_hide, false);
        if (!isArrowHide) {
            Drawable basicDrawable = ContextCompat.getDrawable(getContext(), R.drawable.vt_arrow);
            int resId = typedArray.getColor(R.styleable.vt_classify_triangle_color, -1);
            if (basicDrawable != null) {
                drawable = DrawableCompat.wrap(basicDrawable);
                if (resId != -1) {
                    DrawableCompat.setTint(drawable, resId);
                }
            }
            spinner.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
        }
        typedArray.recycle();
    }

    /**
     * Get the display size
     */
    @SuppressWarnings({"deprecation"})
    private void getCurrentDisplaySize() {
        WindowManager window = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);

        displays[0] = window.getDefaultDisplay().getWidth();
        displays[1] = window.getDefaultDisplay().getHeight();

    }

    /**
     * 箭头的动画
     */
    private void animateArrow(boolean shouldRotateUp) {
        int start = shouldRotateUp ? 0 : MAX_LEVEL;
        int end = shouldRotateUp ? MAX_LEVEL : 0;
        ObjectAnimator animator = ObjectAnimator.ofInt(drawable, "level", start, end);
        animator.setInterpolator(new LinearOutSlowInInterpolator());
        animator.start();
    }


    @Override
    public Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(INSTANCE_STATE, super.onSaveInstanceState());
        bundle.putInt(SELECTED_INDEX, selectedIndex);
        if (popupWindow != null) {
            bundle.putBoolean(IS_POPUP_SHOWING, popupWindow.isShowing());
            dismissDropDown();
        }
        return bundle;
    }

    /**传入表格数据源*/
    public <T> void attachDataSource(@NonNull List<T> dataset) {
        adapter = new VtClassifyAdapter<>(getContext(), dataset, textColor, backgroundSelector);
        setAdapterInternal(adapter);
    }
    /**传入关键数据源*/
    public void attachHotDataSource(List<String> list){
        setRightLayoutTextView(list);
    }

    private void setAdapterInternal(@NonNull VtClassifyBaseAdapter adapter) {
        // If the adapter needs to be settled again, ensure to reset the selected index as well
        setGridViewHeight();
        selectedIndex = 0;
        gridView.setAdapter(adapter);
//        setText(adapter.getItemInDataset(selectedIndex).toString());
//        setGridViewHeight();
    }


    private void setGridViewHeight(){
//        try {
//            ViewTreeObserver vto = gridView.getViewTreeObserver();
//            vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//                @Override
//                public void onGlobalLayout() {
//
//                    int height=gridView.getHeight();
//                    if (height>displays[1]/5){
//                        height=displays[1]/5;
//                        gridView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,height));
//                        popupWindow.setHeight(height);
////                        gridView.postInvalidate();
//                    }
//                    gridView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
//                }
//            });
//        }catch (Exception e){
//            e.printStackTrace();
//        }



//        int gheight=gridView.getMeasuredHeight();
//        ViewGroup.LayoutParams params=gridView.getLayoutParams();
//        int pheight=popupWindow.getHeight();
//        if (params.height>displays[1]/5){
//            params.height=displays[1]/5;
//            gridView.setLayoutParams(params);
//        }
    }

    @Override
    public void onRestoreInstanceState(Parcelable savedState) {
        if (savedState instanceof Bundle) {
            Bundle bundle = (Bundle) savedState;
            selectedIndex = bundle.getInt(SELECTED_INDEX);

            if (adapter != null) {
//                setText(adapter.getItemInDataset(selectedIndex).toString());
                adapter.notifyItemSelected(selectedIndex);
            }

            if (bundle.getBoolean(IS_POPUP_SHOWING)) {
                if (popupWindow != null) {
                    // Post the show request into the looper to avoid bad token exception
                    post(new Runnable() {
                        @Override
                        public void run() {
                            showDropDown();
                        }
                    });
                }
            }
            savedState = bundle.getParcelable(INSTANCE_STATE);
        }
        super.onRestoreInstanceState(savedState);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        popupWindow.setWidth(MeasureSpec.getSize(widthMeasureSpec));
//        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(displays[1]/4);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    public void dismissDropDown() {
        if (!isArrowHide) {
            animateArrow(false);
        }
        popupWindow.dismiss();
    }

    public void showDropDown() {
        if (!isArrowHide) {
            animateArrow(true);
        }
        popupWindow.showAsDropDown(this);
//        setGridViewHeight();
    }



    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (!popupWindow.isShowing()) {
                showDropDown();
            } else {
                dismissDropDown();
            }
        }
        return false;
    }

    private class OnClick implements OnClickListener {
        private int index;

        public OnClick(int index) {
            this.index = index;
        }

        @Override
        public void onClick(View v) {

            if (onHotItemClickListener != null) {
                onHotItemClickListener.onItemClick(v,index);
            }

        }
    }

    public interface OnHotItemClickListener{
        void onItemClick(View view,int position);
    }
    private OnHotItemClickListener onHotItemClickListener;

    public void setOnHotItemClickListener(OnHotItemClickListener onHotItemClickListener) {
        this.onHotItemClickListener = onHotItemClickListener;
    }

    public void addOnItemClickListener(@NonNull AdapterView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemSelectedListener(@NonNull AdapterView.OnItemSelectedListener onItemSelectedListener) {
        this.onItemSelectedListener = onItemSelectedListener;
    }
}
