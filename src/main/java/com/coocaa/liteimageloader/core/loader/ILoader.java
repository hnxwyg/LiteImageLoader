package com.coocaa.liteimageloader.core.loader;

import com.coocaa.liteimageloader.core.BitmapParams;
import com.coocaa.liteimageloader.core.LoadCallback;

/**
 * Created by luwei on 17-10-17.
 */

public interface ILoader {
    public void loadImage(BitmapParams params, LoadCallback callback);
    public void destroy();
}
