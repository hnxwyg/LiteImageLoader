package com.coocaa.liteimageloader.core;

/**
 * Created by luwei on 17-10-17.
 */

public class BitmapParams {
    public String mUrl = "";
    public int mWidth = 0;
    public int mHeight = 0;

    public static class Builder{
        private String mUrl = "";
        private int mWidth = 0;
        private int mHeight = 0;

        public Builder setUrl(String url){
            this.mUrl = url;
            return this;
        }

        public Builder setWidth(int width){
            this.mWidth = width;
            return this;
        }

        public Builder setHeight(int height){
            this.mHeight = height;
            return this;
        }

        public BitmapParams build(){
            BitmapParams params = new BitmapParams();
            params.mUrl = mUrl;
            params.mWidth = mWidth;
            params.mHeight = mHeight;
            return params;
        }
    }
}
