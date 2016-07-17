package com.voctex.bean;

/**
 * Created by voctex on 2016/7/16.
 */
public class ShopStatusBean {
    private String time;//时间
    private String finish;//已完成
    private String doing;//正在做

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFinish() {
        return finish;
    }

    public void setFinish(String finish) {
        this.finish = finish;
    }

    public String getDoing() {
        return doing;
    }

    public void setDoing(String doing) {
        this.doing = doing;
    }
}
