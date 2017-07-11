package com.voctex.ui.tablayout.view;

import com.voctex.R;
import com.voctex.base.BaseFragment;

/**
 * Created by mac_xihao on 17/7/11.
 */

public abstract class PaletteUIF extends BaseFragment {

    private static final int[] drawables = {R.mipmap.pic_one, R.mipmap.pic_two, R.mipmap.pic_four, R.mipmap
            .pic_three, R.mipmap.pic_five};
    /**
     * 提供当前Fragment的主色调的Bitmap对象,供Palette解析颜色
     *
     * @return
     */
    public static int getBackgroundBitmapPosition(int selectViewPagerItem) {
        return drawables[selectViewPagerItem];
    }
}
