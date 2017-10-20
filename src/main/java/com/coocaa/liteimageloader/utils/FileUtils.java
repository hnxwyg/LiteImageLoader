package com.coocaa.liteimageloader.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by luwei on 17-10-18.
 */

public class FileUtils {
    public static final void saveBytes(byte[] bytes,String path) throws IOException{
        File file  = new File(path);
        if (!file.exists())
            file.createNewFile();
        else{
            file.delete();
            file.createNewFile();
        }
        OutputStream out = new BufferedOutputStream(new FileOutputStream(file));
        out.write(bytes,0,bytes.length);
        out.flush();
        out.close();
    }
}
