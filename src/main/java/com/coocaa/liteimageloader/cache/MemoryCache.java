package com.coocaa.liteimageloader.cache;

import android.util.Log;

import com.coocaa.liteimageloader.ImageLoader;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by luwei on 17-10-17.
 */

public class MemoryCache extends Cache<Key,BitmapViews>{

    public MemoryCache(long size) {
        super(size);
    }

    @Override
    public synchronized boolean put(Key key, BitmapViews bitmapViews) {
        if (mCurrentSize + bitmapViews.get().getByteCount() < mTotalSize) {
            mCache.put(key, bitmapViews);
            mCurrentSize += bitmapViews.get().getByteCount();
            Log.i(ImageLoader.TAG,"add bitmap and current cache size is " + mCurrentSize);
            return true;
        }else{
            recycle();
            if (mCurrentSize + bitmapViews.get().getByteCount() < mTotalSize) {
                mCache.put(key, bitmapViews);
                mCurrentSize += bitmapViews.get().getByteCount();
                Log.i(ImageLoader.TAG,"add bitmap and current cache size is " + mCurrentSize);
                return true;
            }else{
                Log.e(ImageLoader.TAG,"the config memory cache has full,please check memory leak");
            }
        }
        return false;
    }

    @Override
    public synchronized void remove(Key key) {
        BitmapViews bitmapViews = get(key);
        if (bitmapViews != null){
            int memory = bitmapViews.forceRecycle();
            mCurrentSize -= memory;
            Log.i(ImageLoader.TAG,"recycle bitmap size " + memory);
        }
        mCache.remove(key);
    }


    @Override
    public synchronized BitmapViews get(Key key) {
        return mCache.get(key);
    }

    @Override
    public synchronized long recycle() {
        System.gc();
        Iterator<Map.Entry<Key,BitmapViews>> iterator = mCache.entrySet().iterator();
        long tempSize = mCurrentSize;
        while (iterator.hasNext()){
            int recycle = iterator.next().getValue().recycle();
            if (recycle > 0){
                mCurrentSize -= recycle;
                iterator.remove();
            }
        }
        Log.i(ImageLoader.TAG,"recycle bitmap size " + (tempSize - mCurrentSize) + " current cache size is " + mCurrentSize);
        return tempSize - mCurrentSize;
    }

    @Override
    public void destroy() {
        Set<Map.Entry<Key,BitmapViews>> entrySet = mCache.entrySet();
        for (Map.Entry<Key, BitmapViews> keyBitmapViewsEntry : entrySet) {
            keyBitmapViewsEntry.getValue().forceRecycle();
        }
        mCache.clear();
    }
}
