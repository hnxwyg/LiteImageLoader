package com.coocaa.liteimageloader.core.loader;

import android.graphics.Bitmap;

import com.coocaa.liteimageloader.cache.ByteCache;
import com.coocaa.liteimageloader.cache.Key;
import com.coocaa.liteimageloader.core.BitmapParams;
import com.coocaa.liteimageloader.core.ExecutorSupplier;
import com.coocaa.liteimageloader.core.LoadCallback;
import com.coocaa.liteimageloader.utils.BitmapUtils;

/**
 * Created by luwei on 17-10-18.
 */

public class ByteLoader implements ILoader{
    private ByteCache mByteCache = null;
    private FileLoader mFileLoader = null;
    public ByteLoader(ByteCache cache){
        this.mByteCache = cache;
        mFileLoader = new FileLoader();
    }
    @Override
    public void loadImage(final BitmapParams params, final LoadCallback callback) {
        final Key key = new Key(params.mUrl,params.mWidth,params.mHeight);
        final byte[] bytes;
        synchronized (mByteCache){
            bytes = mByteCache.get(key);
        }
        if (bytes != null){
            ExecutorSupplier.forDecode().execute(new Runnable() {
                @Override
                public void run() {
                    Bitmap bitmap = BitmapUtils.decodeByte(bytes,params.mWidth,params.mHeight);
                    if (bitmap != null){
                        callback.loadSuccess(params, bitmap);
                    }else{
                        synchronized (mByteCache){
                            mByteCache.remove(key);
                        }
                    }
                }
            });
        }else{
            mFileLoader.loadImage(params, new LoadByteCallback() {
                @Override
                public void loadByte(final BitmapParams p, final byte[] bytes) {
                    ExecutorSupplier.forDecode().execute(new Runnable() {
                        @Override
                        public void run() {
                            Bitmap bitmap = BitmapUtils.decodeByte(bytes,p.mWidth,p.mHeight);
                            if (bitmap != null){
                                callback.loadSuccess(p,bitmap);
                            }
                            synchronized (mByteCache){
                                mByteCache.put(key,bytes);
                            }
                        }
                    });
                }

                @Override
                public void loadFailed(BitmapParams p) {
                    callback.loadFailed(p);
                }
            });
        }
    }

    @Override
    public void destroy() {
        mByteCache.destroy();
        mFileLoader.destroy();
    }
}
