package com.voctex.fragment;


import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.voctex.R;
import com.voctex.activity.DataActivity;
import com.voctex.activity.ShowActivity;
import com.voctex.activity.SpannerActivity;
import com.voctex.banner.BannerLayout;
import com.voctex.banner.bean.BannerEntity;
import com.voctex.banner.interfac.OnBannerImgShowListener;
import com.voctex.base.BaseFragment;
import com.voctex.base.BaseRecyclerAdapter;
import com.voctex.contacts.ContactActivity;
import com.voctex.fragment.adapter.FirstAdapter;
import com.voctex.fragment.bean.FirstBean;
import com.voctex.rx.uia.RxJavaActivity;
import com.voctex.tools.SPUtil;
import com.voctex.tools.VtToast;
import com.voctex.ui.tablayout.view.CollapsingToolbarUIA;
import com.voctex.ui.tablayout.view.TabLayoutUIA;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Created by voctex on 2016/08/12.
 */
public class FirstFragment extends BaseFragment implements OnBannerImgShowListener,BaseRecyclerAdapter.OnItemClickListener<FirstBean>{


    private String[] imgs = {"http://www.005.tv/uploads/allimg/161208/1J0124292-3.jpg",
            "http://wapfile.desktx.com/pc/161122/bigpic/5832b76c05a7e.jpg",
            "http://www.005.tv/uploads/allimg/161208/1J0124292-3.jpg",
            "http://2t.5068.com/uploads/allimg/161205/68-1612051H459-50.jpg",
            "http://www.005.tv/uploads/allimg/161208/1J012I47-12.jpg",
            "http://2t.5068.com/uploads/allimg/161205/68-1612051H501-50.jpg"};

    @Override
    protected int getLayout() {
        return R.layout.fragment_first;
    }

    protected void initView(){

        List<BannerEntity> mList = new ArrayList<>();
        for (int i = 0; i < imgs.length; i++) {
            BannerEntity bannerEntity = new BannerEntity();
            bannerEntity.setAdImg(imgs[i]);
            mList.add(bannerEntity);
        }

        BannerLayout bannerLayout= ((BannerLayout) mViewGroup.findViewById(R.id.first_banner));
        bannerLayout.setEntities(mList,this);
        //这里设置点的颜色和位置需要设置了数据之后才能设置，因为点的数量需要数据的条数来确定
        bannerLayout.setPointColor(Color.BLUE, Color.RED);
        bannerLayout.setPointPotision(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
        //设置无限轮播的起始停顿时间和轮播的间隔时间
        bannerLayout.schedule(2000, 4500);
        //设置轮播的点击事件
//        bannerLayout.setOnBannerClickListener(this);


        RecyclerView recyclerView= ((RecyclerView) mViewGroup.findViewById(R.id.first_recyclerview));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        //设置一个垂直方向的layout manager
        int orientation = LinearLayoutManager.VERTICAL;
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, orientation, false));

        String[] titleArr=getResources().getStringArray(R.array.first_recycler_list);
        List<FirstBean> firstBeans=new ArrayList<>();
        for (String title :
                titleArr) {
            FirstBean firstBean=new FirstBean();
            firstBean.setTitle(title);
            firstBeans.add(firstBean);
        }

        FirstAdapter firstAdapter=new FirstAdapter(recyclerView,firstBeans);
        firstAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(firstAdapter);


    }

    @Override
    public void onBannerShow(String s, ImageView imageView) {
        Glide.with(this)
                .load(s)
                .fitCenter()
//              .transform(new GlideCircleTransform((Context) obj))
                .priority(Priority.NORMAL)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(R.mipmap.pic_banner_load)
                .error(R.mipmap.pic_banner_load)
                .crossFade()
                .into(imageView);
    }

    @Override
    public void onItemClick(View view, FirstBean data, int position) {
        VtToast.s(mContext,"view:"+view+"--data:"+data.getTitle()+"--position:"+position);
        switch (position){
            case 0:{
                boolean isNight = ((boolean) SPUtil.get(mContext, SPUtil.FileName.SYSTEM, "isNight", false));
                if (isNight){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }
                SPUtil.put(mContext, SPUtil.FileName.SYSTEM,"isNight",
                        !isNight);

                ((AppCompatActivity)mContext).recreate();
                VtToast.s(mContext,"更换主题");
                break;
            }
            case 1:{
                startActivity(new Intent(mContext, ShowActivity.class));
                break;
            }
            case 2:{
                startActivity(new Intent(mContext, DataActivity.class));
                break;
            }
            case 3:{
                startActivity(new Intent(mContext, TabLayoutUIA.class));
                break;
            }
            case 4:{
                startActivity(new Intent(mContext, CollapsingToolbarUIA.class));
                break;
            }
            case 5:{
                startActivity(new Intent(mContext, ContactActivity.class));
                break;
            }

        }

    }
}
