package com.coocaa.liteimageloader;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import com.coocaa.liteimageloader.cache.BitmapViews;
import com.coocaa.liteimageloader.cache.ICache;
import com.coocaa.liteimageloader.cache.Key;
import com.coocaa.liteimageloader.cache.MemoryCache;
import com.coocaa.liteimageloader.core.BitmapParams;
import com.coocaa.liteimageloader.core.LoadCallback;
import com.coocaa.liteimageloader.file.LoadImageFromFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.coocaa.liteimageloader.core.Support.FILE;
import static com.coocaa.liteimageloader.core.Support.HTTP;

/**
 * Created by luwei on 17-10-17.
 */

public class ImageLoaderImpl implements IImageLoader{
    private Handler mMainThread = new Handler(Looper.getMainLooper());
    private ICache mMemoryCache = null;
    private HashMap<Key,List<LoadParams>> mTaskMap = new HashMap<>();

    public void setCacheSize(long size){
        mMemoryCache = new MemoryCache(size);
    }

    @Override
    public void load(final LoadParams params) {
        if (TextUtils.isEmpty(params.mUrl))
            return;
        Uri uri = Uri.parse(params.mUrl);
        String scheme = uri.getScheme();
        if (!TextUtils.isEmpty(scheme)){
            Key key = new Key(params.mUrl,params.mWidth,params.mHeight);
            final BitmapViews bitmapViews = mMemoryCache.get(key);
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
                public void loadSuccess(String url, final Bitmap bitmap) {
                    final Key key = new Key(params.mUrl,params.mWidth,params.mHeight);
                    BitmapViews bitmapViews = new BitmapViews(bitmap);
                    mMemoryCache.put(key,bitmapViews);
                    synchronized (mTaskMap){
                        List<LoadParams> taskList = mTaskMap.get(key);
                        for (int i = 0; i < taskList.size(); i++) {
                            final LoadParams p = taskList.get(i);
                            mMainThread.post(new Runnable() {
                                @Override
                                public void run() {
                                    if (p.mContext instanceof Activity &&
                                            (!((Activity) p.mContext).isFinishing()
                                                    || !((Activity) p.mContext).isDestroyed())){
                                        p.mIv.setImageBitmap(bitmap);
                                        mMemoryCache.get(key).addView(p.mIv);
                                    }
                                }
                            });
                        }
                    }
                }
                @Override
                public void loadFailed(String url) {

                }
            };
            BitmapParams bitmapParams = new BitmapParams.Builder()
                    .setHeight(params.mHeight)
                    .setWidth(params.mWidth)
                    .setUrl(params.mUrl)
                    .build();
            switch (scheme){
                case HTTP:
                    break;
                case FILE:
                    new LoadImageFromFile().loadImage(bitmapParams,callback);
                    break;
            }
        }
    }

    @Override
    public void clearMemoryCache(Key key) {
        BitmapViews bitmapViews = mMemoryCache.get(key);
        if (bitmapViews != null){
            int memory = bitmapViews.forceRecycle();
            Log.i(ImageLoader.TAG,"recycle bitmap size " + memory);
            mMemoryCache.remove(key);
        }
    }
}
