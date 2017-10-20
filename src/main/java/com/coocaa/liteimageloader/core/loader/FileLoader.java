package com.coocaa.liteimageloader.core.loader;

import android.text.TextUtils;

import com.coocaa.liteimageloader.ImageLoader;
import com.coocaa.liteimageloader.cache.FilePathCache;
import com.coocaa.liteimageloader.core.BitmapParams;
import com.coocaa.liteimageloader.core.ExecutorSupplier;
import com.coocaa.liteimageloader.utils.FileUtils;

import java.io.File;
import java.io.IOException;

import okio.BufferedSource;
import okio.Okio;

/**
 * Created by luwei on 17-10-19.
 */

public class FileLoader implements ILoadByte{
    private FilePathCache mPathCache = null;
    private HttpLoader mHttpLoader = null;
    public FileLoader(){
        this.mPathCache = new FilePathCache(0);
        mHttpLoader = new HttpLoader();
    }
    @Override
    public void loadImage(final BitmapParams params, final LoadByteCallback callback) {
        final String path;
        synchronized (mPathCache){
            path = mPathCache.get(params.mUrl);
        }
        if (!TextUtils.isEmpty(path)){
            ExecutorSupplier.forLocalStorageRead().execute(new Runnable() {
                @Override
                public void run() {
                    File f = new File(path);
                    if (f.exists()){
                        try {
                            BufferedSource buffer = Okio.buffer(Okio.source(f));
                            callback.loadByte(params,buffer.readByteArray());
                        } catch (Exception e) {
                            e.printStackTrace();
                            synchronized (mPathCache){
                                mPathCache.remove(params.mUrl);
                            }
                            loadFromNet(params,callback);
                        }
                    }else{
                        synchronized (mPathCache){
                            mPathCache.remove(params.mUrl);
                        }
                        loadFromNet(params,callback);
                    }
                }
            });
        }else{
            loadFromNet(params,callback);
        }
    }

    private void loadFromNet(final BitmapParams params, final LoadByteCallback callback){
        mHttpLoader.loadImage(params, new LoadByteCallback() {
            @Override
            public void loadByte(BitmapParams p, byte[] bytes) {
                String path = ImageLoader.mConfigParams.mCachePath + File.separator + p.mUrl.hashCode();
                try {
                    FileUtils.saveBytes(bytes,path);
                    mPathCache.put(p.mUrl,path);
                    callback.loadByte(p,bytes);
                } catch (IOException e) {
                    e.printStackTrace();
                    callback.loadFailed(p);
                }
            }

            @Override
            public void loadFailed(BitmapParams p) {
                callback.loadFailed(p);
            }
        });
    }
}
