package com.coocaa.liteimageloader;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import com.coocaa.liteimageloader.cache.BitmapViews;
import com.coocaa.liteimageloader.cache.ByteCache;
import com.coocaa.liteimageloader.cache.Key;
import com.coocaa.liteimageloader.cache.MemoryCache;
import com.coocaa.liteimageloader.core.BitmapParams;
import com.coocaa.liteimageloader.core.LoadCallback;
import com.coocaa.liteimageloader.core.WrapperDrawable;
import com.coocaa.liteimageloader.core.loader.ByteLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by luwei on 17-10-17.
 */

public class ImageLoaderImpl implements IImageLoader{
    private Handler mMainThread = new Handler(Looper.getMainLooper());
    private MemoryCache mMemoryCache = null;
    private HashMap<Key,List<LoadParams>> mTaskMap = new HashMap<>();
    private ByteLoader mByteLoader = null;

    public void setCacheSize(long size){
        mMemoryCache = new MemoryCache(size);
    }

    public void setByteCacheSize(long size){
        mByteLoader = new ByteLoader(new ByteCache(size));
    }

    @Override
    public void load(final LoadParams params) {
        if (TextUtils.isEmpty(params.mUrl))
            return;
        if (!params.mUrl.startsWith("http"))
            return;
        Uri uri = Uri.parse(params.mUrl);
        String scheme = uri.getScheme();
        if (!TextUtils.isEmpty(scheme)){
            Key key = new Key(params.mUrl,params.mWidth,params.mHeight);
            final BitmapViews bitmapViews;
            synchronized (mMemoryCache){
                bitmapViews = mMemoryCache.get(key);
            }
            if (bitmapViews != null){
                bitmapViews.addView(params.mIv);
                mMainThread.post(new Runnable() {
                    @Override
                    public void run() {
                        params.mIv.setImageBitmap(bitmapViews.get());
                    }
                });
                return;
            }
            Drawable d = params.mIv.getDrawable();
            WrapperDrawable drawable = null;
            if (d instanceof BitmapDrawable){
                drawable = new WrapperDrawable(((BitmapDrawable) d).getBitmap());
            }else{
                params.mIv.setImageResource(R.drawable.bg);
                drawable = new WrapperDrawable(((BitmapDrawable) params.mIv.getDrawable()).getBitmap());
            }
            params.mIv.setImageDrawable(drawable);
            drawable.setLoadParams(params);
        }
    }

    public void realLoadImage(final LoadParams params){
        Key key = new Key(params.mUrl,params.mWidth,params.mHeight);
        synchronized (mTaskMap){
            if (mTaskMap.containsKey(key)){
                mTaskMap.get(key).add(params);
                return;
            }else{
                List<LoadParams> tastList = new ArrayList<>();
                tastList.add(params);
                mTaskMap.put(key, tastList);
            }
        }
        LoadCallback callback = new LoadCallback() {
            @Override
            public void loadSuccess(BitmapParams url, final Bitmap bitmap) {

                mMainThread.post(new Runnable() {
                    @Override
                    public void run() {
                        final Key key = new Key(params.mUrl,params.mWidth,params.mHeight);
                        BitmapViews bitmapViews = new BitmapViews(bitmap);
                        boolean success;
                        synchronized (mMemoryCache) {
                            success = mMemoryCache.put(key,bitmapViews);
                        }
                        synchronized (mTaskMap){
                            if (!success){
                                mTaskMap.remove(key);
                                return;
                            }
                            List<LoadParams> taskList = mTaskMap.get(key);
                            for (int i = 0; i < taskList.size(); i++) {
                                final LoadParams p = taskList.get(i);
                                if (p.mContext instanceof Activity &&
                                        (!((Activity) p.mContext).isFinishing()
                                                || !((Activity) p.mContext).isDestroyed())){
                                    p.mIv.setImageBitmap(bitmap);
                                    synchronized (mMemoryCache) {
                                        mMemoryCache.get(key).addView(p.mIv);
                                    }
                                }
                            }
                            mTaskMap.remove(key);
                        }
                    }
                });
            }

            @Override
            public void loadFailed(BitmapParams p) {

            }
        };
        BitmapParams bitmapParams = new BitmapParams.Builder()
                .setHeight(params.mHeight)
                .setWidth(params.mWidth)
                .setUrl(params.mUrl)
                .build();
        mByteLoader.loadImage(bitmapParams,callback);
    }

    @Override
    public void clearMemoryCache(Key key) {
        synchronized (mMemoryCache){
            mMemoryCache.remove(key);
        }
    }

    public void destroy(){
        mMemoryCache.destroy();
    }
}
