package com.coocaa.liteimageloader;

import com.coocaa.liteimageloader.cache.Key;

/**
 * Created by luwei on 17-10-16.
 */

public interface IImageLoader {
    public void load(LoadParams params);
    public void clearMemoryCache(Key key);
}
