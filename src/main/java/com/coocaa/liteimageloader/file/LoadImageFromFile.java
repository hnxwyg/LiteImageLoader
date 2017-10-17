package com.coocaa.liteimageloader.file;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.coocaa.liteimageloader.ImageLoader;
import com.coocaa.liteimageloader.core.BitmapParams;
import com.coocaa.liteimageloader.core.ExecutorSupplier;
import com.coocaa.liteimageloader.core.ILoadImage;
import com.coocaa.liteimageloader.core.LoadCallback;
import com.coocaa.liteimageloader.utils.BitmapUtils;

import java.io.File;
import java.io.FileInputStream;

/**
 * Created by luwei on 17-10-17.
 */

public class LoadImageFromFile implements ILoadImage{
    private static final String FILE_EX = "file://";
    @Override
    public void loadImage(final BitmapParams params, final LoadCallback callback) {
        ExecutorSupplier.forLocalStorageRead().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String path = params.mUrl.replace(FILE_EX,"");
                    FileInputStream in = new FileInputStream(new File(path));
                    BitmapFactory.Options options = null;
                    if (params.mWidth != 0 && params.mHeight != 0){
                        options = new BitmapFactory.Options();
                        options.inJustDecodeBounds = true;
                        BitmapFactory.decodeFile(path,options);
                        int inSampleSzie = BitmapUtils.computeSampleSize(options,-1,params.mWidth * params.mHeight);
                        options.inSampleSize = inSampleSzie;
                        options.inJustDecodeBounds = false;
                    }
                    Bitmap bitmap = null;
                    try {
                        bitmap = BitmapFactory.decodeFileDescriptor(in.getFD(),null,options);
                        Log.i(ImageLoader.TAG,"has load success " + path);
                    } catch (OutOfMemoryError e) {
                        e.printStackTrace();
                    }
                    if (callback != null)
                        callback.loadSuccess(params.mUrl,bitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                    if (callback != null)
                        callback.loadFailed(params.mUrl);
                }
            }
        });
    }
}
