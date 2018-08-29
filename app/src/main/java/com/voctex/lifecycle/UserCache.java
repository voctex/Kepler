package com.voctex.lifecycle;

import android.util.LruCache;

/**
 * @author Voctex
 * on 2018/08/17 10:02
 */
public class UserCache extends LruCache {


    public UserCache(int maxSize) {
        super(maxSize);
    }
}
