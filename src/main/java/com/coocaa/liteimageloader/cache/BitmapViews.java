package com.coocaa.liteimageloader.cache;

import android.graphics.Bitmap;
import android.widget.ImageView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by luwei on 17-10-17.
 */

public class BitmapViews {
    private Bitmap mBitmap = null;
    private List<WeakReference<ImageView>> mViews = new ArrayList<>();

    public BitmapViews(Bitmap bitmap){
        this.mBitmap = bitmap;
    }

    public int recycle(){
        int size = mViews.size();
        for (int i = 0; i < size; i++) {
            WeakReference<ImageView> view = mViews.get(0);
            if (view.get() != null)
                return 0;
        }
        int memory = mBitmap.getByteCount();
        if (mBitmap != null && !mBitmap.isRecycled()) {
            mBitmap.recycle();
            mBitmap = null;
            mViews.clear();
        }
        return memory;
    }

    public Bitmap get(){
        return mBitmap;
    }

    public void addView(ImageView view){
        int size = mViews.size();
        for (int i = 0; i < size; i++) {
            if (view == mViews.get(i).get())
                return;
        }
        WeakReference<ImageView> v = new WeakReference<ImageView>(view);
        mViews.add(v);
    }

    public int forceRecycle(){
        int size = mViews.size();
        for (int i = 0; i < size; i++) {
            ImageView view = mViews.get(i).get();
            if (view.getDrawable() != null)
                view.getDrawable().setCallback(null);
            view.setImageDrawable(null);
        }
        mViews.clear();
        int memory = mBitmap.getByteCount();
        mBitmap.recycle();
        return memory;
    }
}
