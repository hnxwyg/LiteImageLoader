package com.coocaa.liteimageloader.core;

import android.graphics.Bitmap;

/**
 * Created by luwei on 17-10-17.
 */

public interface LoadCallback {
    public void loadSuccess(BitmapParams params,Bitmap bitmap);
    public void loadFailed(BitmapParams params);
}
