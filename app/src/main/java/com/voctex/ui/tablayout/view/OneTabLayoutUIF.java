package com.voctex.ui.tablayout.view;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.voctex.R;
import com.voctex.base.BaseFragment;
import com.voctex.tools.VtToast;
import com.voctex.ui.tablayout.adapter.TabLayoutAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac_xihao on 17/6/28.
 * 线性列表
 */
public class OneTabLayoutUIF extends PaletteUIF {
    @Override
    protected int getLayout() {
        return R.layout.uif_tablayout_one_main;
    }

    @Override
    protected void initView() {
        RecyclerView recyclerView = ((RecyclerView) mViewGroup.findViewById(R.id.tablayout1_recyclerview));

        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        //设置一个垂直方向的layout manager
        int orientation = LinearLayoutManager.VERTICAL;
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, orientation, false));

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
    public static OneTabLayoutUIF newInstance() {
        OneTabLayoutUIF f = new OneTabLayoutUIF();
        Bundle args = new Bundle();
        f.setArguments(args);
        return f;
    }
}
