package com.voctex.ui.tablayout.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.voctex.R;
import com.voctex.base.BaseRecyclerAdapter;
import com.voctex.base.BaseRecyclerHolder;
import com.voctex.ui.tablayout.holder.OneTabLayoutHolder;

import java.util.Collection;

/**
 * Created by mac_xihao on 17/6/28.
 */
public class TabLayoutAdapter extends BaseRecyclerAdapter<String> {

    public TabLayoutAdapter(RecyclerView recyclerView, Collection<String> datas) {
        super(recyclerView, datas);
    }

    @Override
    public void convert(BaseRecyclerHolder holder, String item, int position, boolean isScrolling) {
        holder.setData(item);
    }

    @Override
    public BaseRecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new OneTabLayoutHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_tablayout_one, parent, false));
    }
}
