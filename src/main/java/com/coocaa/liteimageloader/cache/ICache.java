package com.coocaa.liteimageloader.cache;

/**
 * Created by luwei on 17-10-17.
 */

public interface ICache <K,V>{
    public boolean put(K key,V bitmapViews);
    public void remove(K key);
    public V get(K key);
    public long recycle();
}
