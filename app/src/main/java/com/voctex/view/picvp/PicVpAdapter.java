package com.voctex.view.picvp;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.voctex.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by voctex on 2016/7/22.
 */

public class PicVpAdapter extends PagerAdapter {

    private Context mContext;
    /**
     * 装ImageView数组
     */
    private List<ImageView> imgList = new ArrayList<>();
    private OnVpItemClickListener onVpItemClickListener;

    public PicVpAdapter(Context mContext,final List<String> mlist) {
        imgList.clear();
        for (int i = 0; i < mlist.size(); i++) {
            final int position = i;
            ImageView img = new ImageView(mContext);
            img.setScaleType(ImageView.ScaleType.CENTER_CROP);
            img.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onVpItemClickListener != null) {
                        onVpItemClickListener.onItemClick(position-1, mlist.size()-2);
                    }
                }
            });
            Glide.with(mContext)
                    .load(mlist.get(i))
                    .fitCenter()
                    .crossFade()
                    .placeholder(R.mipmap.banner_loading)
                    .error(R.mipmap.banner_loading)
                    .into(img)
                    ;
            imgList.add(img);
        }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(imgList.get(position));

    }

    /**
     * 载入图片进去，用当前的position 除以 图片数组长度取余数是关键
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView img = imgList.get(position);
        container.addView(img);
        return imgList.get(position);
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
    }


    @Override
    public int getCount() {
        return imgList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public void setOnVpItemClickListener(OnVpItemClickListener onVpItemClickListener) {
        this.onVpItemClickListener = onVpItemClickListener;
    }
}
