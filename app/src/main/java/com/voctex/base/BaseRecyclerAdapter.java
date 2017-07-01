package com.voctex.base;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Voctex on 2016/9/18.
 * Recyclerview适配器基类
 */
public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<BaseRecyclerHolder> {

    private boolean isScrolling;
    private List<T> realDatas;
    private SparseArray<View> headerViewList = new SparseArray<>();

    public SparseArray<View> getHeaderViewList() {
        return headerViewList;
    }

    public BaseRecyclerAdapter(RecyclerView recyclerView, Collection<T> datas) {
        initView(recyclerView, datas);
    }

    /**
     * 初始化
     */
    private void initView(RecyclerView recyclerView, Collection<T> datas) {
        if (datas == null) {
            realDatas = new ArrayList<>();
        } else if (datas instanceof List) {
            realDatas = (List<T>) datas;
        } else {
            realDatas = new ArrayList<>(datas);
        }

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                isScrolling = !(newState == RecyclerView.SCROLL_STATE_IDLE);
                if (!isScrolling) {
                    notifyDataSetChanged();
                }
            }
        });

    }

    /**
     * Recycler适配器填充方法
     *
     * @param holder      viewholder
     * @param item        javabean
     * @param isScrolling RecyclerView是否正在滚动
     */
    public abstract void convert(BaseRecyclerHolder holder, T item, int position, boolean isScrolling);


    @Override
    public void onBindViewHolder(BaseRecyclerHolder holder, int position) {
        if (position - headerViewList.size() >= 0) {
            convert(holder, realDatas.get(position - headerViewList.size()), position, isScrolling);
            //注释掉这句的原因是，无法在holder类里面给itemview设置点击事件，所以要注释
            holder.itemView.setOnClickListener(getOnClickListener(position - headerViewList.size()));
        }
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return realDatas == null ? 0 : realDatas.size() + headerViewList.size()/* + footerViewCount*/;
    }

    public BaseRecyclerAdapter<T> refresh(Collection<T> datas) {
        if (datas == null) {
            realDatas = new ArrayList<>();
        } else if (datas instanceof List) {
            realDatas = (List<T>) datas;
        } else {
            realDatas = new ArrayList<>(datas);
        }
        return this;
    }

    private OnItemClickListener listener;

    public interface OnItemClickListener<T> {
        void onItemClick(View view, T data, int position);
    }

    public void setOnItemClickListener(OnItemClickListener l) {
        listener = l;
    }

    public View.OnClickListener getOnClickListener(final int position) {
        return new View.OnClickListener() {
            @Override
            public void onClick(@Nullable View v) {
                if (listener != null && v != null) {
                    listener.onItemClick(v, realDatas.get(position), position);
                }
            }
        };
    }

}
