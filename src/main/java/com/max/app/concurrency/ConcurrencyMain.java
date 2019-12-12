package com.max.app.concurrency;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class ConcurrencyMain {

    private static final Logger LOG = LoggerFactory.getLogger(ConcurrencyMain.class);

    public static void main(String[] args) throws Exception {

        final int threadsCount = 200;

        final ExecutorService pool = Executors.newCachedThreadPool();

        final CountDownLatch allCompleted = new CountDownLatch(threadsCount);
        final CountDownLatch allStarted = new CountDownLatch(threadsCount);

        for (int i = 0; i < threadsCount; ++i) {
            pool.execute(() -> {
                try {
                    allStarted.countDown();
                    allStarted.await();
                }
                catch (InterruptedException interEx) {
                    Thread.currentThread().interrupt();
                }
                finally {
                    allCompleted.countDown();
                }
            });
        }

        allCompleted.await();
        pool.shutdownNow();

        LOG.info("ConcurrencyMain done. java version {}", System.getProperty("java.version"));
    }

}
