package com.coocaa.liteimageloader.cache;

/**
 * Created by luwei on 17-10-18.
 */

public class ByteCache extends Cache<Key,byte[]>{

    public ByteCache(long size) {
        super(size);
    }

    @Override
    public synchronized boolean put(Key key, byte[] bytes) {
        int length = bytes.length;
        if (mCurrentSize + length < mTotalSize) {
            mCache.put(key, bytes);
            mTotalSize += length;
        }else{
            recycle();
            if (mCurrentSize + length < mTotalSize) {
                mCache.put(key, bytes);
                mTotalSize += length;
            }
        }
        return false;
    }

    @Override
    public synchronized void remove(Key key) {
        byte[] bytes = mCache.get(key);
        int length = bytes.length;
        mCache.remove(key);
        mCurrentSize -= length;
    }

    @Override
    public void destroy() {
        mCache.clear();
    }

    @Override
    public synchronized byte[] get(Key key) {
        return mCache.get(key);
    }

    @Override
    public synchronized long recycle() {
        mCache.clear();
        long temp = mCurrentSize;
        mCurrentSize = 0;
        return temp;
    }
}
