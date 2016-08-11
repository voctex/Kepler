package com.voctex.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.voctex.R;
import com.voctex.base.BaseActivity;
import com.voctex.tools.LoadOperate;
import com.voctex.tools.SafeThread;
import com.voctex.tools.StrValues;
import com.voctex.tools.VtLog;
import com.voctex.tools.VtToast;

/**
 * Created by voctex 2016/08/01
 * 本来是用来测试读取配置文件里面的数据的
 */
public class DataActivity extends BaseActivity implements View.OnClickListener {

    private SafeThread safeThread;
    private AsyncTask<String, Integer, String> asyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        LinearLayout layout = (LinearLayout) findViewById(R.id.content_layout);
        layout.setOnClickListener(this);

        final LoadOperate builder = new LoadOperate.Builder(mContext, layout)
                .setLoadingPic(R.mipmap.ic_circle_gray2)
                .build();
        builder.showLoading();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                builder.showNoData();
            }
        }, 6000);
    }

    private void newThread() {
        safeThread = new SafeThread() {
            @Override
            public void startRun() {
//                try {
//                    SafeThread.sleep(5000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                    return;
//                }
//                handler.sendEmptyMessage(0);
                for (int i = 0; i < 100000; i++) {
                    Log.i(StrValues.Tags.VOCTEX, "i==" + i);
                }
            }

            @Override
            public void stopRun() {
//                handler.sendEmptyMessage(1);
                Log.i(StrValues.Tags.VOCTEX, "停止线程");
            }
        };
        safeThread.start();
    }


    private void startThread() {
        asyncTask = new AsyncTask<String, Integer, String>() {
            @Override
            protected String doInBackground(String... params) {
                for (int i = 0; i < 100000; i++) {
                    Log.i(StrValues.Tags.VOCTEX, "i==" + params + i);
                }

                return "haha";
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                Log.i(StrValues.Tags.VOCTEX, "开始任务");
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Log.i(StrValues.Tags.VOCTEX, "任务结束");
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);
            }

            @Override
            protected void onCancelled(String s) {
                super.onCancelled(s);
            }

            @Override
            protected void onCancelled() {
                super.onCancelled();
                Log.i(StrValues.Tags.VOCTEX, "取消任务");
            }
        };
        asyncTask.execute("hehe");
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0: {
                    VtToast.s(mContext, "线程完成了线程完成了线程完成了线程完成了线程完成了线程完成了线程完成了线程完成了线程完成了线程完成了");
                    break;
                }
                case 1: {
                    VtToast.s(mContext, "停止线程");
                    break;
                }
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        asyncTask.cancel(true);
//        safeThread.interrupt();
//        try {
//            safeThread.stop();
//        }catch (Exception e){
//            e.printStackTrace();
//            Log.i(StrValues.Tags.VOCTEX,"线程停止--------------------------------------------------------\n" +
//                    "___________________________________________________________\n" +
//                    "___________________________________________________________\n" +
//                    "___________________________________________________________");
//        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.content_layout: {
                VtToast.s(this, "恭喜你，可以点击到我");
                VtLog.i("恭喜你，可以点击到我");
                break;
            }
        }
    }
}
