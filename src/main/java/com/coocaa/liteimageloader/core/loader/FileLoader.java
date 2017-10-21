package com.coocaa.liteimageloader.core.loader;

import com.coocaa.liteimageloader.ImageLoader;
import com.coocaa.liteimageloader.core.BitmapParams;
import com.coocaa.liteimageloader.core.ExecutorSupplier;
import com.coocaa.liteimageloader.utils.FileUtils;
import com.coocaa.liteimageloader.utils.MD5Utils;

import java.io.File;
import java.io.IOException;

import okio.BufferedSource;
import okio.Okio;

/**
 * Created by luwei on 17-10-19.
 */

public class FileLoader implements ILoadByte{
    private HttpLoader mHttpLoader = null;
    public FileLoader(){
        mHttpLoader = new HttpLoader();
    }
    @Override
    public void loadImage(final BitmapParams params, final LoadByteCallback callback) {
        ExecutorSupplier.forLocalStorageRead().execute(new Runnable() {
            @Override
            public void run() {
                String fileName = MD5Utils.encode(params.mUrl);
                params.mFileName = fileName;
                String path = ImageLoader.mConfigParams.mCachePath + File.separator + fileName;
                File f = new File(path);
                if (f.exists()){
                    try {
                        BufferedSource buffer = Okio.buffer(Okio.source(f));
                        callback.loadByte(params,buffer.readByteArray());
                    } catch (Exception e) {
                        e.printStackTrace();
                        loadFromNet(params,callback);
                    }
                }else{
                    loadFromNet(params,callback);
                }
            }
        });
    }

    @Override
    public void destroy() {
        mHttpLoader.destroy();
    }

    private void loadFromNet(final BitmapParams params, final LoadByteCallback callback){
        mHttpLoader.loadImage(params, new LoadByteCallback() {
            @Override
            public void loadByte(BitmapParams p, byte[] bytes) {
                String path = ImageLoader.mConfigParams.mCachePath + File.separator + p.mFileName;
                try {
                    FileUtils.saveBytes(bytes,path);
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
