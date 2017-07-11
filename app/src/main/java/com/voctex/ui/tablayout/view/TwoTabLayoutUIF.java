package com.voctex.ui.tablayout.view;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.voctex.R;
import com.voctex.base.BaseFragment;
import com.voctex.ui.tablayout.adapter.TabLayoutAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac_xihao on 17/6/28.
 * 网格列表
 */
public class TwoTabLayoutUIF extends PaletteUIF {
    @Override
    protected int getLayout() {
        return R.layout.uif_tablayout_two_main;
    }

    @Override
    protected void initView() {
        RecyclerView recyclerView = ((RecyclerView) mViewGroup.findViewById(R.id.tablayout2_recyclerview));

        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        //设置一个垂直方向的网格 manager
        int orientation = GridLayoutManager.VERTICAL;
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 2, orientation, false));

        List<String> mList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            mList.add("位置为：" + i);
        }

        TabLayoutAdapter tabLayoutAdapter = new TabLayoutAdapter(recyclerView, mList);

        recyclerView.setAdapter(tabLayoutAdapter);

    }

    /**
     * 静态工厂方法需要一个int型的值来初始化fragment的参数，
     * 然后返回新的fragment到调用者
     */
    public static TwoTabLayoutUIF newInstance() {
        TwoTabLayoutUIF f = new TwoTabLayoutUIF();
        Bundle args = new Bundle();
        f.setArguments(args);
        return f;
    }
}
