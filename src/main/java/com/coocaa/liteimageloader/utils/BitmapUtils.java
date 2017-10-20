package com.coocaa.liteimageloader.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by luwei on 17-10-17.
 */

public class BitmapUtils {
    public static int computeSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength, maxNumOfPixels);
        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }
        return roundedSize;
    }

    private static int computeInitialSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;
        int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(Math.floor(w / minSideLength), Math.floor(h / minSideLength));
        if (upperBound < lowerBound) {
            // return the larger one when there is no overlapping zone.
            return lowerBound;
        }
        if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }


    public static Bitmap decodeByte(byte[] bytes,int width,int height){
        int length = bytes.length;
        BitmapFactory.Options options = null;
        if (bytes != null && length > 10){
            if (width != 0 && height != 0){
                options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeByteArray(bytes,0,length,options);
                int inSampleSzie = BitmapUtils.computeSampleSize(options,-1,width * height);
                options.inSampleSize = inSampleSzie;
                options.inJustDecodeBounds = false;
            }
            try {
                return BitmapFactory.decodeByteArray(bytes,0,length,options);
            } catch (OutOfMemoryError e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
