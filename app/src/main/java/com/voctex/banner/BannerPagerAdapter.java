package com.voctex.banner;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者： 巴掌 on 16/6/9 17:34
 */
public class BannerPagerAdapter extends PagerAdapter {

    private Context mContext;
    private List<BannerEntity> mEntities = new ArrayList<BannerEntity>();
    private List<BannerLayout> mLayouts = new ArrayList<BannerLayout>();
    private OnBannerClickListener mClickListener;

    public BannerPagerAdapter(Context context, List<BannerEntity> entities) {
        mContext = context;
        this.mEntities = entities;
        setLayouts();
    }

    private void setLayouts() {
        for (BannerEntity entity : mEntities) {
            BannerLayout layout = new BannerLayout(mContext);
//            layout.setEntity(entity);
            layout.setEntity(entity,false);
            mLayouts.add(layout);
        }
    }

    @Override
    public int getCount() {
        return mLayouts.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        BannerLayout bannerLayout = mLayouts.get(position);
        bannerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mClickListener != null) {
                    mClickListener.onClick(position - 1);
                }
            }
        });
        try{
            container.addView(bannerLayout, 0);

        }catch (Exception e){

        }

        return mLayouts.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        try{
        container.removeView(mLayouts.get(position));

        }catch (Exception e){

        }
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
    }

    public void setOnBannerClickListener(OnBannerClickListener clickListener) {
        this.mClickListener = clickListener;
    }

}
