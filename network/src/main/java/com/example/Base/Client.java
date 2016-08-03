package com.example.Base;

import com.example.impl.IClient;

/**
 * Created by voctex on 2016/7/16.
 */

public abstract class Client implements IClient {
    @Override
    public void toNet() {
        System.out.print("用户会上网");
    }
}
