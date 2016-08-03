package com.voctex.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;

import com.voctex.R;
import com.voctex.base.BaseActivity;
import com.voctex.bean.ShopStatusBean;
import com.voctex.tools.ShopManager;
import com.voctex.tools.VtToast;
import com.voctex.view.VtShopStatusView;
import com.voctex.view.classify.VtClassifyView;
import com.voctex.view.picvp.OnVpItemClickListener;
import com.voctex.view.picvp.VtPicVpView;
import com.voctex.view.spanner.VtSpinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

public class SpannerActivity extends BaseActivity implements AdapterView.OnItemClickListener,VtClassifyView.OnHotItemClickListener{

    private VtShopStatusView vtShopStatusView;
    private List<ShopStatusBean> mList = new ArrayList<ShopStatusBean>();
    private String[] time = {"18:15", "18:15", "18:15", "18:15", "18:15"};
    private String[] text = {"已支付", "已接单", "已配送", "已签收", "已评论"};
    private String[] texting = {"正在支付", "正在接单", "正在配送", "正在签收", "正在评论"};

        private String[] urlArr = {"http://img3.3lian.com/2013/s1/30/d/68.jpg",
            "http://img5.imgtn.bdimg.com/it/u=4124642155,3788556392&fm=21&gp=0.jpg",
            "http://img1.3lian.com/2015/a1/120/d/123.jpg",
            "http://img1.imgtn.bdimg.com/it/u=445022991,4288176230&fm=21&gp=0.jpg",
            "http://img3.imgtn.bdimg.com/it/u=521238632,39244381&fm=21&gp=0.jpg"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spanner);

        initView();

    }

    private void initView(){
        VtSpinner vtSpinner = (VtSpinner) findViewById(R.id.nice_spinner);
        List<String> dataset = new LinkedList<>(Arrays.asList("One", "Two", "Three", "Four",
                "Five","One", "Two", "Three", "Four", "Five","One", "Two", "Three", "Four",
                "Five","One", "Two", "Three", "Four", "Five"));
        vtSpinner.attachDataSource(dataset);

        List<String> vList=new ArrayList<>();
        for (String ss: urlArr){
            vList.add(ss);
        }

        VtPicVpView vtPicVp= (VtPicVpView) findViewById(R.id.vt_picvp);
        vtPicVp.attachDataSource(vList);
        vtPicVp.setOnVpItemClickListener(new OnVpItemClickListener() {
            @Override
            public void onItemClick(int position, int total) {
                VtToast.s(SpannerActivity.this,"position="+position+"--total="+total);
            }
        });


        Calendar calendar = Calendar.getInstance();
        calendar.getActualMaximum(Calendar.DATE);
//        calendar.get




        vtShopStatusView = (VtShopStatusView) findViewById(R.id.shop_status);

//        for (int i = 0; i < 3; i++) {
//            ShopStatusBean bean=new ShopStatusBean();
//            try {
//                bean.setTime(time[i]);
//                bean.setFinish(text[i]);
//                bean.setDoing(texting[i]);
//            } catch (Exception e) {
//
//            }
//            mList.add(bean);
//        }
        mList= ShopManager.getInstance().getDataByMode(ShopManager.MODE_SIGN_FOR);

                vtShopStatusView.setCurrentItem(mList);

        final EditText editText= (EditText) findViewById(R.id.editText);
        Button btn= (Button) findViewById(R.id.submit_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String num=editText.getText().toString();
                vtShopStatusView.setCurrentItem(mList);
            }
        });

        VtClassifyView vtClassifyView=(VtClassifyView) findViewById(R.id.vt_classify);
        List<String> dataset1 = new LinkedList<>(Arrays.asList("1111", "222", "333"));
        vtClassifyView.attachDataSource(dataset);
        vtClassifyView.attachHotDataSource(dataset1);
        vtClassifyView.addOnItemClickListener(this);
        vtClassifyView.setOnHotItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        VtToast.s(this,"第"+position+"个");
    }

    @Override
    public void onItemClick(View view, int position) {
        VtToast.s(this,"第"+position+"个");
    }
}
