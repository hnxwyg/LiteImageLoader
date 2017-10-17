
package com.coocaa.liteimageloader.core;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ExecutorSupplier {
    private static final int NUM_IO_BOUND_THREADS = 2;
    private static final int NUM_LIGHTWEIGHT_BACKGROUND_THREADS = 1;
    private static ThreadPoolExecutor mIoBoundExecutor = null;
    private static Executor mDecodeExecutor;
    private static Executor mBackgroundExecutor;
    private static Executor mLightWeightBackgroundExecutor;
    private static PriorityThreadFactory mPriorityThreadFactory;

    public ExecutorSupplier(int numCpuBoundThreads) {
        this.mPriorityThreadFactory = new PriorityThreadFactory(0);
        this.mIoBoundExecutor = (ThreadPoolExecutor)Executors.newFixedThreadPool(2);
        this.mDecodeExecutor = Executors.newFixedThreadPool(numCpuBoundThreads, mPriorityThreadFactory);
        this.mBackgroundExecutor = Executors.newFixedThreadPool(numCpuBoundThreads, mPriorityThreadFactory);
        this.mLightWeightBackgroundExecutor = Executors.newFixedThreadPool(1, mPriorityThreadFactory);
    }

    public static Executor forLocalStorageRead() {
        return mIoBoundExecutor;
    }

    public static Executor forLocalStorageWrite() {
        return mIoBoundExecutor;
    }

    public static Executor forDecode() {
        return mDecodeExecutor;
    }

    public static Executor forBackgroundTasks() {
        return mBackgroundExecutor;
    }

    public static Executor forLightweightBackgroundTasks() {
        return mLightWeightBackgroundExecutor;
    }
}
