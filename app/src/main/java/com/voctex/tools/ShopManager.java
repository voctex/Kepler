package com.voctex.tools;



import com.voctex.bean.ShopStatusBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by voctex on 2016/7/19.
 */
public class ShopManager {

    private static ShopManager instance;

    public static ShopManager getInstance(){
        if (instance==null){
            instance=new ShopManager();
        }
        return instance;
    }

    private int currentMode=MODE_NORMAL;

    /**
     * 获得当前的国务状态
     * */
    public int getCurrentMode(){
        return currentMode;
    }


    public List<ShopStatusBean> getDataByMode(int mode) {
        switch (mode) {
            case MODE_NORMAL: {
                List<ShopStatusBean> mList = new ArrayList<>();
                ShopStatusBean bean = new ShopStatusBean();
                bean.setDoing("正在支付");
                bean.setTime("");
                mList.add(bean);
                currentMode=MODE_NORMAL;
                return mList;
            }
            case MODE_PAID:{
                List<ShopStatusBean> mList=getList(0,MODE_NORMAL,"已支付","等待接单");
                currentMode=MODE_PAID;
                return mList;
            }
            case MODE_RECV_ORDER:{
                List<ShopStatusBean> mList=getList(1,MODE_PAID,"已接单","等待配送");
                currentMode=MODE_RECV_ORDER;
                return mList;
            }
            case MODE_BIZ_REJECT: {
                List<ShopStatusBean> mList=getList(1,MODE_PAID,"商家拒单","交易结束");
                currentMode=MODE_BIZ_REJECT;
                return mList;
            }
            case MODE_USER_CANCEL:{
                List<ShopStatusBean> mList=getList(1,MODE_PAID,"用户取消","交易结束");
                currentMode=MODE_USER_CANCEL;
                return mList;
            }
            case MODE_DISPATCH: {
                List<ShopStatusBean> mList=getList(2,MODE_RECV_ORDER,"开始配送","等待送达");
                currentMode=MODE_DISPATCH;
                return mList;
            }
            case MODE_DISPATCH_CANCEL: {
                List<ShopStatusBean> mList=getList(2,MODE_RECV_ORDER,"用户取消","交易结束");
                currentMode= MODE_DISPATCH_CANCEL;
                return mList;
            }
            case MODE_SIGN_FOR:{
                List<ShopStatusBean> mList=getList(3,MODE_DISPATCH,"已签收","待评价");
                currentMode=MODE_SIGN_FOR;
                return mList;
            }
            case MODE_USER_REJECT: {
                List<ShopStatusBean> mList=getList(3,MODE_DISPATCH,"拒绝收货","交易结束");
                currentMode=MODE_USER_REJECT;
                return mList;
            }
            case MODE_EVALUATE: {
                List<ShopStatusBean> mList=getList(4,MODE_PAID,"已评论","交易结束");
                currentMode=MODE_EVALUATE;
                return mList;
            }

        }
        return null;
    }

    private List<ShopStatusBean> getList(int index,int mode,String finish, String doing) {
        List<ShopStatusBean> beans=getDataByMode(mode);
        ShopStatusBean bean = new ShopStatusBean();
        beans.get(index).setFinish(finish);
        bean.setTime("");
        bean.setDoing(doing);
        beans.add(bean);
        return beans;
    }


    /**
     * 正常状态
     */
    public static final int MODE_NORMAL = 0;
    /**
     * 已支付
     */
    public static final int MODE_PAID = MODE_NORMAL + 1;
    /**
     * 已接单
     */
    public static final int MODE_RECV_ORDER = MODE_PAID + 1;
    /**
     * 商家拒单
     */
    public static final int MODE_BIZ_REJECT = MODE_RECV_ORDER + 1;
    /**
     * 用户取消
     */
    public static final int MODE_USER_CANCEL = MODE_BIZ_REJECT + 1;
    /**
     * 开始配送
     */
    public static final int MODE_DISPATCH = MODE_USER_CANCEL + 1;
    /**
     * 配送取消
     */
    public static final int MODE_DISPATCH_CANCEL = MODE_DISPATCH + 1;
    /**
     * 已签收
     */
    public static final int MODE_SIGN_FOR = MODE_DISPATCH_CANCEL + 1;
    /**
     * 拒绝签收
     */
    public static final int MODE_USER_REJECT = MODE_SIGN_FOR + 1;
    /**
     * 已评论
     */
    public static final int MODE_EVALUATE = MODE_USER_REJECT + 1;
}
