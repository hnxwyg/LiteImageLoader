package com.coocaa.liteimageloader.cache;

import java.util.HashMap;

/**
 * Created by luwei on 17-10-18.
 */

public abstract class Cache<K,V> implements ICache<K,V>{
    protected long mTotalSize = 0;
    protected long mCurrentSize = 0;
    protected HashMap<K,V> mCache = new HashMap<>();
    public Cache(long size){
        this.mTotalSize = size;
    }
}
