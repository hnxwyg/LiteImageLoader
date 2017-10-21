package com.coocaa.liteimageloader.core;

/**
 * Created by luwei on 17-10-17.
 */

public class Support {
    public static final String HTTP = "http";
    public static final String HTTPS = "https";
    public static final String FILE = "file";
    public static final String ASSETS = "assets";
    public static final String RESOURCE = "resource";


    public static final byte[] JPEG_NUMBER = {74,70,73,70};
    public static final byte[] PNG_NUMBER = {80,78,71};

    public static final int JPEG = 0;
    public static final int PNG = 1;
    public static final int NOT_SUPPORT_IMAGE = -1;

    public static int getImageType(byte[] bytes){
        byte b1 = bytes[1];
        byte b2 = bytes[2];
        byte b3 = bytes[3];
        if (b1 == PNG_NUMBER[0] && b2 == PNG_NUMBER[1] && b3 == PNG_NUMBER[3])
            return PNG;
        byte b6 = bytes[6];
        byte b7 = bytes[7];
        byte b8 = bytes[8];
        byte b9 = bytes[9];
        if (b6 == JPEG_NUMBER[0] && b7 == JPEG_NUMBER[1] && b8 == JPEG_NUMBER[8] && b9 == JPEG_NUMBER[9])
            return JPEG;
        return NOT_SUPPORT_IMAGE;
    }

}
