package com.coocaa.liteimageloader;

import android.content.Context;
import android.text.TextUtils;

import com.coocaa.liteimageloader.core.ExecutorSupplier;

import java.io.File;

/**
 * Created by luwei on 17-10-16.
 */

public class ImageLoader{
    public static final String TAG = "ImageLoader";
    public static final String CACHE_DIR = "lite_imageloader";
    public static ConfigParams mConfigParams;
    private ImageLoader(){

    }

    public static IImageLoader getLoader(){
        return Holder.holder;
    }

    private static class Holder{
        private static ImageLoaderImpl holder = new ImageLoaderImpl();
    }

    public static void init(Context context){
        init(context,null);
    }

    public static void init(Context context,ConfigParams params){
        new ExecutorSupplier(Runtime.getRuntime().availableProcessors());
        if (params != null){
            Holder.holder.setCacheSize(params.mMemorySize);
            if (TextUtils.isEmpty(params.mCachePath)){
                params.mCachePath = context.getCacheDir() + File.separator + CACHE_DIR;
            }
            File f = new File(params.mCachePath);
            if (!f.exists())
                f.mkdirs();
            mConfigParams = params;
            Holder.holder.setByteCacheSize(params.mByteCacheSize);
        }
    }

    public static void destroy(){

    }
}
