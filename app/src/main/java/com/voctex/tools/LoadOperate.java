package com.voctex.tools;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.voctex.R;

/**
 * Created by voctex on 2016/8/11.
 * 该操作类可以实现在一个View上进行转圈处理，当然也可以显示其他状态（例如没有数据，没有网络，加载失败）
 * 注意：传入的View的父布局暂时不能是RelativeLayout，而且传入的View最好不要设置内边距
 */
public class LoadOperate implements Runnable, View.OnClickListener {

    private Params params;
    private LinearLayout loadingLayout;
    private RotateAnimation rotateAnimation;

    private final static int TAG_NORMAL = 0;
    private final static int TAG_LOADING = TAG_NORMAL + 1;
    private final static int TAG_NODATA = TAG_LOADING + 1;
    private final static int TAG_NONET = TAG_NODATA + 1;
    private final static int TAG_ERROR = TAG_NONET + 1;
    private int currentState = TAG_NORMAL;
    private boolean isPost = false;

    private LoadOperate(Params params) {
        this.params = params;
        initView();
    }

    public LoadOperate(Builder builder){
        this.params=builder.params;
        initView();
    }

    /**
     * 在View被绘制出来后才可以进行一系列操作
     */
    private void initView() {
        params.view.post(this);
    }


    @Override
    public void run() {
        ViewGroup viewParent = (ViewGroup) params.view.getParent();
        int position = viewParent.indexOfChild(viewParent);

        ViewGroup.MarginLayoutParams layoutP = (ViewGroup.MarginLayoutParams) params.view.getLayoutParams();
        //获得之前的View的宽高和在父布局当中的外边距
        int width = layoutP.width;
        int height = layoutP.height;
        int leftMargin = layoutP.leftMargin;
        int rightMargin = layoutP.rightMargin;
        int topMargin = layoutP.topMargin;
        int bottomMargin = layoutP.bottomMargin;

        VtLog.i("w==" + width + "--h==" + height);

        FrameLayout frameLayout = new FrameLayout(params.mContext);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(width,
                height);
        layoutParams.gravity = Gravity.CENTER;
        frameLayout.setLayoutParams(layoutParams);

        //获得在父布局的位置
        int top = params.view.getTop();
        int left = params.view.getLeft();

        Log.i(StrValues.Tags.VOCTEX, "top=" + top + "--left=" + left);

        //之前的ViewGroup的父布局移除掉ViewGroup，并加入新建的帧布局，然后帧布局再加入之前的ViewGroup
        viewParent.removeView(params.view);
        viewParent.addView(frameLayout, position, layoutParams);
        frameLayout.addView(params.view);
        //设置帧布局在父布局的外边距
        ViewGroup.MarginLayoutParams newLayoutP = (ViewGroup.MarginLayoutParams) frameLayout.getLayoutParams();
        newLayoutP.setMargins(leftMargin, topMargin, rightMargin, bottomMargin);
        frameLayout.setLayoutParams(newLayoutP);

        addLoadingView(frameLayout);

    }


    private View addLoadingView(FrameLayout frameLayout) {
        //转圈图片的父布局，用来装载转圈控件和覆盖原先View的可视范围
        loadingLayout = new LinearLayout(params.mContext);
        loadingLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams loadingLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        loadingLayout.setGravity(Gravity.CENTER);
        loadingLayout.setLayoutParams(loadingLayoutParams);
        frameLayout.addView(loadingLayout);
        loadingLayout.setOnClickListener(this);
        loadingLayout.setBackgroundColor(Color.WHITE);

        //转圈控件，实现转圈动画
        ImageView loadView = new ImageView(params.mContext);
        LinearLayout.LayoutParams loadViewLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        loadView.setLayoutParams(loadViewLayoutParams);
        loadingLayout.addView(loadView);
        //转圈动画
        rotateAnimation = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(1000);
        rotateAnimation.setRepeatCount(-1);

        isPost = true;

        switch (currentState) {
            case TAG_LOADING: {
                showLoading();
                break;
            }
            case TAG_NODATA: {
                showNoData();
                break;
            }
            case TAG_NONET: {
                showNoNet();
                break;
            }
            case TAG_ERROR: {
                showError();
                break;
            }
            case TAG_NORMAL: {
                loadingLayout.setVisibility(View.GONE);
                break;
            }
        }

        return loadingLayout;
    }

    /**
     * 没有数据的显示
     */
    public void showNoData() {
        currentState = TAG_NODATA;
        if (!isPost) {
            return;
        }
        ImageView loadView = getLoadView(TAG_NODATA);
        loadView.setImageResource(this.params.noDataPic == 0 ? R.mipmap.ic_launcher : this.params.noDataPic);
        loadView.clearAnimation();
    }

    /**
     * 加载中的显示
     */
    public void showLoading() {
        currentState = TAG_LOADING;
        if (!isPost) {
            return;
        }
        //判断是否有网，然后显示不同的图片
        if (isNetworkAvailable(params.mContext)) {
            ImageView loadView = getLoadView(TAG_LOADING);
            loadingLayout.setTag(TAG_LOADING);
            loadView.setImageResource(this.params.loadingPic == 0 ? R.mipmap.ic_circle_gray : this.params.loadingPic);
            loadView.startAnimation(rotateAnimation);
        } else {
            ImageView loadView = getLoadView(TAG_NONET);
            loadingLayout.setTag(TAG_NONET);
            loadView.setImageResource(this.params.noDataPic == 0 ? R.mipmap.ic_launcher : this.params.noNetPic);
            loadView.clearAnimation();
        }
    }

    /**
     * 加载失败的显示
     */
    public void showError() {
        currentState = TAG_ERROR;
        if (!isPost) {
            return;
        }
        ImageView loadView = getLoadView(TAG_ERROR);
        loadView.setImageResource(this.params.errorPic == 0 ? R.mipmap.ic_launcher : this.params.errorPic);
    }

    /**
     * 没有网络的显示
     */
    public void showNoNet() {
        currentState = TAG_NONET;
        if (!isPost) {
            return;
        }
        ImageView loadView = getLoadView(TAG_NONET);
        loadView.setImageResource(this.params.noDataPic == 0 ? R.mipmap.ic_launcher : this.params.noNetPic);
        loadView.clearAnimation();
    }

    /**
     * 加载完成的显示
     */
    public void finish() {
        if (!isPost) {
            return;
        }
        this.loadingLayout.setVisibility(View.GONE);
    }

    /**
     * 获得转圈的控件，可以设置其他图片，并设置转圈控件的父布局的点击事件的标签
     */
    private ImageView getLoadView(int tag) {
        loadingLayout.setVisibility(View.VISIBLE);
        loadingLayout.setBackgroundColor(Color.WHITE);
        ImageView loadView = (ImageView) loadingLayout.getChildAt(0);
        loadingLayout.setTag(tag);
        return loadView;
    }

    @Override
    public void onClick(View v) {
        switch ((int) v.getTag()) {
            case TAG_LOADING: {
                //空事件，只是为了拦截点击事件，避免用户在加载的时候可以点击下层的控件
                VtToast.s(params.mContext, "正在加载中");
                if (onLoadListener != null) {
                    onLoadListener.loading();
                }
                break;
            }
            case TAG_NONET: {
                VtToast.s(params.mContext, "没有网络，点击重试");
                if (onLoadListener != null) {
                    onLoadListener.noNet();
                }
                break;
            }
            case TAG_NODATA: {
                VtToast.s(params.mContext, "没有数据");
                if (onLoadListener != null) {
                    onLoadListener.noData();
                }
                break;
            }
            case TAG_ERROR: {
                VtToast.s(params.mContext, "加载失败");
                if (onLoadListener != null) {
                    onLoadListener.error();
                }
                break;
            }
        }
    }

    /**
     * 参数容器类
     */
    private static class Params {
        private Context mContext;
        private View view;
        private int loadingPic = 0;
        private int noDataPic = 0;
        private int noNetPic = 0;
        private int errorPic = 0;
    }

    public static class Builder {

        private Params params;

        public Builder(Context mContext, View view) {
            this.params = new Params();
            this.params.mContext = mContext;
            this.params.view = view;

        }

        public Builder setNoNetPic(int pic) {
            this.params.noNetPic = pic;
            return this;
        }

        public Builder setNoData(int pic) {
            this.params.noDataPic = pic;
            return this;
        }

        public Builder setLoadingPic(int pic) {
            this.params.loadingPic = pic;
            return this;
        }

        public Builder setErrorPic(int pic) {
            this.params.errorPic = pic;
            return this;
        }

        public LoadOperate build() {
            LoadOperate loadTool = new LoadOperate(params);
            return loadTool;
        }
    }


    /**
     * 检查当前网络是否可用
     * <p>
     * context
     *
     * @return
     */
    public boolean isNetworkAvailable(Context mContext) {
        ConnectivityManager connectivity = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                // 当前网络是连接的
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    // 当前所连接的网络可用
                    return true;
                }
            }
        }
        return false;
    }

    public interface OnLoadListener {
        void loading();

        void error();

        void noData();

        void noNet();
    }

    private OnLoadListener onLoadListener;

    public void setOnLoadListener(OnLoadListener onLoadListener) {
        this.onLoadListener = onLoadListener;
    }
}
