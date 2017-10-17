package com.coocaa.liteimageloader;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.coocaa.liteimageloader.core.ExecutorSupplier;

/**
 * Created by luwei on 17-10-16.
 */

public class ImageLoader{
    public static final String TAG = "ImageLoader";
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
        }
    }

    public static void destroy(){

    }


    public static class LoadBuilder{
        public Context context;
        public Uri uri;
        public ImageView iv;
    }
}
