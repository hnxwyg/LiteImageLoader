package com.coocaa.liteimageloader;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.FrameLayout;
import android.widget.ImageView;

/**
 * Created by luwei on 17-10-17.
 */

public class TestActivity extends Activity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ConfigParams params = new ConfigParams();
        params.mMemorySize = 40 * 1024 * 1024;
        ImageLoader.init(getApplicationContext(),params);
        FrameLayout layout = new FrameLayout(this);
        setContentView(layout);
        for (int i = 0; i < 10; i++) {
            ImageView iv = new ImageView(this);
            FrameLayout.LayoutParams p = new FrameLayout.LayoutParams(80,80);
            p.leftMargin = i * 90;
            layout.addView(iv,p);
            ImageLoader.getLoader().load(new LoadParams.Builder().with(this).load("http://img.taopic.com/uploads/allimg/120727/201995-120HG1030762.jpg").resize(80,80).into(iv).build());
        }
    }
}
