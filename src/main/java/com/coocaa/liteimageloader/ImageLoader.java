package com.coocaa.liteimageloader;

/**
 * Created by luwei on 17-10-16.
 */

public class ImageLoader implements IImageLoader{
    private ImageLoader(){

    }

    public static IImageLoader getLoader(){
        return Holder.holder;
    }

    private static class Holder{
        private static ImageLoader holder = new ImageLoader();
    }
}
