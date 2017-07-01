package com.voctex.fragment.holder;

import android.view.View;
import android.widget.TextView;

import com.voctex.R;
import com.voctex.base.BaseRecyclerAdapter;
import com.voctex.base.BaseRecyclerHolder;
import com.voctex.fragment.bean.FirstBean;

/**
 * Created by mac_xihao on 17/6/30.
 */
public class FirstHolder extends BaseRecyclerHolder<FirstBean> {

    private final TextView titleView;

    public FirstHolder(View itemView) {
        super(itemView);
        titleView = ((TextView) itemView.findViewById(R.id.holder_first_title));
    }

    @Override
    public void setData(FirstBean bean) {
        super.setData(bean);
        titleView.setText(bean.getTitle());
    }
}
