package com.coocaa.liteimageloader;

import android.content.Context;
import android.widget.ImageView;

/**
 * Created by luwei on 17-10-17.
 */

public class LoadParams {
    public Context mContext;
    public String mUrl;
    public ImageView mIv;
    public int mHeight = 0;
    public int mWidth = 0;

    public static class Builder{
        private Context context;
        private String url;
        private ImageView iv;
        private int height = 0;
        private int width = 0;

        public Builder with(Context context){
            this.context = context;
            return this;
        }

        public Builder load(String uri){
            this.url = uri;
            return this;
        }

        public Builder into(ImageView iv){
            this.iv = iv;
            return this;
        }

        public Builder resize(int width,int height){
            this.width = width;
            this.height = height;
            return this;
        }

        public LoadParams build(){
            LoadParams params = new LoadParams();
            params.mContext = context;
            params.mUrl = url;
            params.mIv = iv;
            params.mWidth = width;
            params.mHeight = height;
            return params;
        }
    }
}
