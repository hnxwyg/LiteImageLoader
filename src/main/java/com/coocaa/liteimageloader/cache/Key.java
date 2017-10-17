package com.coocaa.liteimageloader.cache;

/**
 * Created by luwei on 17-10-17.
 */

public class Key {
    public String mUrl = "";
    public int mWidth = 0;
    public int mHeight = 0;

    public Key(String url,int width,int height){
        this.mUrl = url;
        this.mWidth = width;
        this.mHeight = height;
    }

    @Override
    public int hashCode() {
        StringBuilder b = new StringBuilder();
        return b.append(mUrl).append("_").append(mWidth)
                .append("_").append(mHeight)
                .toString().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Key){
            if (mUrl.equals(((Key) obj).mUrl)
                    && mWidth == ((Key) obj).mWidth
                    && mHeight == ((Key) obj).mHeight)
                return true;
        }
        return false;
    }
}
