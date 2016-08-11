package com.voctex.tools;

/**
 * Created by Voctex on 2016/8/5.
 */

public abstract class SafeThread extends Thread {

    private boolean isFinish = false;

    @Override
    public void run() {
        super.run();
//        while (!isInterrupted()&&!isFinish){ //非阻塞过程中通过判断中断标志来退出
//                startRun();
//            try{
//                Thread.sleep(300);//阻塞过程捕获中断异常来退出
//            }catch(InterruptedException e){
//                e.printStackTrace();
//                stopRun();
//                return;//捕获到异常之后，执行break跳出循环。
//            }
//            isFinish=true;
//        }

        try {
            startRun();
        }catch (Exception e){
            e.printStackTrace();
            stopRun();
        }
    }

    public abstract void startRun();

    public abstract void stopRun();
}
