package com.coocaa.liteimageloader;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.concurrent.CountDownLatch;

/**
 * Created by luwei on 17-10-16.
 */

public class ImageLoaderTest {
    @Test
    public void getInstance(){
        final HashSet<IImageLoader> set = new HashSet<>();
        final CountDownLatch latch = new CountDownLatch(60);
        for (int i = 0; i < 60; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    set.add(ImageLoader.getLoader());
                    latch.countDown();
                }
            }).start();
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(set.size(),1);
    }
}
