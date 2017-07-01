package com.voctex.ui.tablayout.holder;

import android.view.View;
import android.widget.TextView;

import com.voctex.R;
import com.voctex.base.BaseRecyclerHolder;

/**
 * Created by mac_xihao on 17/6/28.
 */
public class OneTabLayoutHolder extends BaseRecyclerHolder<String> {

    private final TextView textView;

    public OneTabLayoutHolder(View itemView) {
        super(itemView);

        textView = ((TextView) itemView.findViewById(R.id.holder_tablayout_ont_text));

    }

    @Override
    public void setData(String bean) {
        super.setData(bean);

        textView.setText(bean);
    }
}
