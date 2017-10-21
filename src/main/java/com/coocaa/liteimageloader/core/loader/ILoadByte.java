package com.coocaa.liteimageloader.core.loader;

import com.coocaa.liteimageloader.core.BitmapParams;

/**
 * Created by luwei on 17-10-20.
 */

public interface ILoadByte {
    public void loadImage(BitmapParams params, LoadByteCallback callback);
    public void destroy();
}
