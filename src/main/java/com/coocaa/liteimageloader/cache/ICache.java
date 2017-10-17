package com.coocaa.liteimageloader.cache;

/**
 * Created by luwei on 17-10-17.
 */

public interface ICache {
    public boolean put(Key key,BitmapViews bitmapViews);
    public void remove(Key key);
    public BitmapViews get(Key key);
    public long recycle();
}
