package com.example.Base;

import com.example.impl.IServer;

import sun.rmi.runtime.Log;

/**
 * Created by voctex on 2016/7/16.
 */

public abstract class Server implements IServer{


    @Override
    public void connect(String company) {
        System.out.print("连接了"+company+"服务器");
    }
}
