package com.coocaa.liteimageloader.core;

import android.graphics.Bitmap;

/**
 * Created by luwei on 17-10-17.
 */

public interface LoadCallback {
    public void loadSuccess(String url,Bitmap bitmap);
    public void loadFailed(String url);
}
