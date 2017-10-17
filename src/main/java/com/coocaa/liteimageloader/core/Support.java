package com.coocaa.liteimageloader.core;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by luwei on 17-10-17.
 */

public class Support {
    public static final String HTTP = "http";
    public static final String HTTPS = "https";
    public static final String FILE = "file";
    public static final String ASSETS = "assets";
    public static final String RESOURCE = "resource";
    @StringDef({HTTP, HTTPS,FILE,ASSETS,RESOURCE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface SupportUri {}
}
