package com.voctex.fragment.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.voctex.R;
import com.voctex.base.BaseRecyclerAdapter;
import com.voctex.base.BaseRecyclerHolder;
import com.voctex.fragment.bean.FirstBean;
import com.voctex.fragment.holder.FirstHolder;

import java.util.Collection;

/**
 * Created by mac_xihao on 17/6/30.
 */
public class FirstAdapter extends BaseRecyclerAdapter<FirstBean> {


    public FirstAdapter(RecyclerView recyclerView, Collection<FirstBean> datas) {
        super(recyclerView, datas);
    }

    @Override
    public void convert(BaseRecyclerHolder holder, FirstBean item, int position, boolean isScrolling) {
        holder.setData(item);
    }

    @Override
    public BaseRecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FirstHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_uif_first,parent,false));
    }
}
