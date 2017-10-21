package com.coocaa.liteimageloader.core.loader;

import android.util.Log;

import com.coocaa.liteimageloader.ImageLoader;
import com.coocaa.liteimageloader.core.BitmapParams;
import com.coocaa.liteimageloader.core.ExecutorSupplier;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by luwei on 17-10-20.
 */

public class HttpLoader implements ILoadByte{
    private OkHttpClient mHttpClient = null;
    public HttpLoader(){
        mHttpClient = new OkHttpClient();
    }
    @Override
    public void loadImage(final BitmapParams params, final LoadByteCallback callback) {
        ExecutorSupplier.forBackgroundTasks().execute(new Runnable() {
            @Override
            public void run() {
                final Request request = new Request.Builder().get()
                        .url(params.mUrl)
                        .build();
                try {
                    Response response = mHttpClient.newCall(request).execute();
                    if (response.code() == 200){
                        byte[] bytes = response.body().bytes();
                        callback.loadByte(params,bytes);
                        Log.i(ImageLoader.TAG,"load " + params.mUrl + " from internet success");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (callback != null)
                        callback.loadFailed(params);
                }
            }
        });
    }

    @Override
    public void destroy() {

    }
}
