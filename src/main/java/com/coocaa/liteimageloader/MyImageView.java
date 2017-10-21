package com.coocaa.liteimageloader;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.widget.ImageView;

/**
 * Created by luwei on 17-10-21.
 */

public class MyImageView extends ImageView{
    public MyImageView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.i(ImageLoader.TAG,"the drawable " + getDrawable());
        super.onDraw(canvas);
    }
}
