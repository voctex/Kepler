package com.voctex.rx.uia;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.voctex.R;
import com.voctex.base.BaseActivity;
import com.voctex.tools.VtToast;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

/**
 * Created by Voctex on 2016/8/28.
 */

public class RxJavaActivity extends BaseActivity implements View.OnClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rx_uia_main);


        initView();

    }

    private void initView(){
        TextView click= (TextView) findViewById(R.id.rx_main_click);
        click.setOnClickListener(this);




    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rx_main_click:{
//                Observable.just("弹出消息").subscribe(new Action1<String>() {
//                    @Override
//                    public void call(String s) {
//                        VtToast.s(mContext,s);
//                    }
//                });

                Observable<String> observable=Observable.create(new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> subscriber) {
                        subscriber.onNext("haha");
                    }
                });

                Subscriber<String> subscriber=new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        VtToast.s(mContext,"context is "+s);
                    }
                };
                observable.subscribe(subscriber);
                break;
            }
        }
    }
}
