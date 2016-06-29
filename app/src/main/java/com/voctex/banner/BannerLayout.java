package com.voctex.banner;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.voctex.R;

/**
 * 作者： 巴掌 on 16/6/9 17:37
 */
public class BannerLayout extends FrameLayout {

    private ImageView mIvBanner;
    private TextView mTvBannerTitle;

    public BannerLayout(Context context) {
        this(context, null);
    }

    public BannerLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View.inflate(getContext(), R.layout.banner_layout, this);
        mIvBanner = (ImageView) this.findViewById(R.id.iv_banner);
        mIvBanner.setScaleType(ImageView.ScaleType.CENTER_CROP);
        mTvBannerTitle = (TextView) this.findViewById(R.id.tv_banner_title);
    }

    public void setEntity(BannerEntity entity) {
        Glide.with(getContext()).load(entity.imageUrl).centerCrop().crossFade().into(mIvBanner);
        mTvBannerTitle.setText(entity.title);
    }

    public void setEntity(BannerEntity entity, boolean isShowTitle) {
        Glide.with(getContext())
                .load(entity.imageUrl)
                .centerCrop()
                .crossFade()
                .into(mIvBanner)
                .onLoadStarted(getResources().getDrawable(R.mipmap.banner_default_img))
                ;
        if (isShowTitle) {
            mTvBannerTitle.setVisibility(View.VISIBLE);
            mTvBannerTitle.setText(entity.title);
        } else {
            mTvBannerTitle.setVisibility(View.GONE);
        }
    }


}
