package com.coocaa.liteimageloader.file;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;

/**
 * Created by luwei on 17-10-16.
 */

public class FileTest {
    @Test
    public void saveImage(){
        byte[] bf = new byte[1024];
        String path = "";
        File file = new File(path);
        Assert.assertEquals(file.exists(),true);
        Assert.assertEquals(file.length(),bf.length);
    }

    @Test
    public void deleteImage(){
        String path = "";
        File f = new File(path);
        Assert.assertEquals(f.exists(),false);
    }
}
