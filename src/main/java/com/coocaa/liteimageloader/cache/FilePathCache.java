package com.coocaa.liteimageloader.cache;

/**
 * Created by luwei on 17-10-19.
 */

public class FilePathCache extends Cache<String,String>{
    public FilePathCache(long size) {
        super(size);
    }

    @Override
    public boolean put(String key, String path) {
        mCache.put(key,path);
        return true;
    }

    @Override
    public void remove(String key) {
        mCache.remove(key);
    }

    @Override
    public String get(String key) {
        return mCache.get(key);
    }

    @Override
    public long recycle() {
        mCache.clear();
        return 0;
    }

    @Override
    public void destroy() {

    }
}
