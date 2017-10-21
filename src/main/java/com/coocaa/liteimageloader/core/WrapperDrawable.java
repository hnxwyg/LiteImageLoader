package com.coocaa.liteimageloader.core;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;

import com.coocaa.liteimageloader.ImageLoader;
import com.coocaa.liteimageloader.ImageLoaderImpl;
import com.coocaa.liteimageloader.LoadParams;

/**
 * Created by luwei on 17-10-20.
 */

public class WrapperDrawable extends BitmapDrawable{
    private LoadParams mLoadParams = null;
    private boolean mHasLoad = true;
    public WrapperDrawable(Bitmap bitmap){
        super(bitmap);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (!mHasLoad){
            ((ImageLoaderImpl)ImageLoader.getLoader()).realLoadImage(mLoadParams);
            Log.i(ImageLoader.TAG,"begin load delay image");
        }
    }

    public void setLoadParams(LoadParams params){
        this.mLoadParams = params;
        mHasLoad = false;
    }
}
