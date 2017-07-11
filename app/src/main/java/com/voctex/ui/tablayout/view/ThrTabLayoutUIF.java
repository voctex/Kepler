package com.voctex.ui.tablayout.view;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.voctex.R;
import com.voctex.base.BaseFragment;
import com.voctex.ui.tablayout.adapter.TabLayoutAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac_xihao on 17/6/28.
 * 瀑布流
 */
public class ThrTabLayoutUIF extends PaletteUIF {
    @Override
    protected int getLayout() {
        return R.layout.uif_tablayout_thr_main;
    }

    @Override
    protected void initView() {
        RecyclerView recyclerView = ((RecyclerView) mViewGroup.findViewById(R.id.tablayout3_recyclerview));

        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        //设置一个垂直方向的瀑布流 manager
        int orientation = StaggeredGridLayoutManager.VERTICAL;
        StaggeredGridLayoutManager layout = new StaggeredGridLayoutManager(2, orientation);
        layout.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        recyclerView.setLayoutManager(layout);

        List<String> mList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            if (i % 2 == 1) {
                mList.add("位置为：" + i + "----那天我从你的门前过");
            } else {
                mList.add("位置为：" + i);
            }
        }

        TabLayoutAdapter tabLayoutAdapter = new TabLayoutAdapter(recyclerView, mList);

        recyclerView.setAdapter(tabLayoutAdapter);

    }

    /**
     * 静态工厂方法需要一个int型的值来初始化fragment的参数，
     * 然后返回新的fragment到调用者
     */
    public static ThrTabLayoutUIF newInstance() {
        ThrTabLayoutUIF f = new ThrTabLayoutUIF();
        Bundle args = new Bundle();
        f.setArguments(args);
        return f;
    }
}
