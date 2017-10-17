package com.coocaa.liteimageloader.core;

import android.os.Process;

import java.util.concurrent.ThreadFactory;

public class PriorityThreadFactory implements ThreadFactory {
    private int mThreadPriority;

    public PriorityThreadFactory(int threadPriority) {
        this.mThreadPriority = threadPriority;
    }

    public Thread newThread(final Runnable runnable) {
        Runnable wrapperRunnable = new Runnable() {
            public void run() {
                try {
                    Process.setThreadPriority(PriorityThreadFactory.this.mThreadPriority);
                } catch (Throwable var2) {
                    ;
                }

                runnable.run();
            }
        };
        return new Thread(wrapperRunnable);
    }

    public void updateThreadPriority(int p){
        this.mThreadPriority = p;
    }
}
