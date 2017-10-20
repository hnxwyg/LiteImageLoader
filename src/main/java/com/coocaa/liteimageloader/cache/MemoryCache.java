package com.coocaa.liteimageloader.cache;

import android.util.Log;

import com.coocaa.liteimageloader.ImageLoader;

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
        }else{
            recycle();
            if (mCurrentSize + bitmapViews.get().getByteCount() < mTotalSize) {
                mCache.put(key, bitmapViews);
                mCurrentSize += bitmapViews.get().getByteCount();
                Log.i(ImageLoader.TAG,"add bitmap and current cache size is " + mCurrentSize);
            }else{
                Log.e(ImageLoader.TAG,"the config memory cache has full,please check memory leak");
            }
        }
        return false;
    }

    @Override
    public synchronized void remove(Key key) {
        mCache.remove(key);
    }


    @Override
    public synchronized BitmapViews get(Key key) {
        return mCache.get(key);
    }

    @Override
    public synchronized long recycle() {
        System.gc();
        Set<Map.Entry<Key,BitmapViews>> entrySet = mCache.entrySet();
        long tempSize = mCurrentSize;
        for (Map.Entry<Key, BitmapViews> keyBitmapViewsEntry : entrySet) {
            int recycle = keyBitmapViewsEntry.getValue().recycle();
            if (recycle > 0){
                mCurrentSize -= recycle;
                mCache.remove(keyBitmapViewsEntry.getKey());
            }
        }
        Log.i(ImageLoader.TAG,"recycle bitmap size " + (tempSize - mCurrentSize) + " current cache size is " + mCurrentSize);
        return tempSize - mCurrentSize;
    }
}
