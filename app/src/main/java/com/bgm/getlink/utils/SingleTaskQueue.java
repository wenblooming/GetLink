package com.bgm.getlink.utils;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class SingleTaskQueue {
    private static final ExecutorService POOL = new ThreadPoolExecutor(0, 1, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());

    //在线程中直接调用Thread的interrupt方法退出
    protected static <T> T runTaskAndWait(Callable<T> c, long timeout) {
        Future<T> future = POOL.submit(c);
        try {
            return future.get(timeout, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            return null;
        }
    }
}
