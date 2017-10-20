package com.coocaa.liteimageloader.core.loader;

import com.coocaa.liteimageloader.core.BitmapParams;

/**
 * Created by luwei on 17-10-19.
 */

public interface LoadByteCallback {
    public void loadByte(BitmapParams params,byte[] bytes);
    public void loadFailed(BitmapParams params);
}
