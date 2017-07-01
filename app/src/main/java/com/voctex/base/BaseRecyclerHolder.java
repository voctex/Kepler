package com.voctex.base;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Voctex on 2016/9/10.
 * Recyclerview的Holder类需要实现的基类
 */
public class BaseRecyclerHolder<B> extends RecyclerView.ViewHolder {
    private final SparseArray<View> mViews;
    protected boolean isFinish = false;
    protected B data;

    public BaseRecyclerHolder(View itemView) {
        super(itemView);
        //一般不会超过8个吧
        this.mViews = new SparseArray<>(8);
    }

    /**
     * 通过控件的Id获取对于的控件，如果没有则加入views
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    /**
     * 为TextView设置字符串
     */
    public BaseRecyclerHolder setText(int viewId, String text) {
        TextView view = getView(viewId);
        view.setText(text);
        return this;
    }

//    /**
//     * 为ImageView设置图片
//     */
//    public BaseRecyclerHolder setImgByUrl(int viewId, String url) {
//        ImageView view = getView(viewId);
////        ImgUtil.showShop(itemView.getContext(), view, url);
//        return this;
//    }
//
//    /**
//     * 为ImageView设置图片
//     */
//    public BaseRecyclerHolder setIcoByUrl(int viewId, String url) {
//        ImageView view = getView(viewId);
////        ImgUtil.showIco(itemView.getContext(), view, url);
//        return this;
//    }

    public void setData(B bean) {
        this.data = bean;
    }

}
