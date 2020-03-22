package com.max.app;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public final class Main {

    public static void main(String[] args) throws Exception {

//        createDeadlock();

        long i = 0;
        while( true ) {
            SoftReference ref = new SoftReference<>(new CachedData(i));
            ++i;
        }


//        System.out.printf("java: %s%n", System.getProperty("java.version"));
    }

    private static final class CachedData {

        private final long index;


        public CachedData(long index) {
            this.index = index;
        }

        @Override
        public void finalize() throws Throwable{
            try {
                System.out.println("Cached data deleted: " + index);
            }
            finally {
                super.finalize();
            }
        }

    }

    public static void createDeadlock() {

        CountDownLatch reached = new CountDownLatch(1);

        Lock lock2 = new ReentrantLock();
        Lock lock1 = new ReentrantLock();

        try (CloseableLock notUsed = new CloseableLock(lock1)) {
            new Thread(() -> {

                try (CloseableLock notUsed2 = new CloseableLock(lock2)) {

                    reached.countDown();

                    try(CloseableLock notUsed4 = new CloseableLock(lock1)){
                        System.out.printf("%s done%n", Thread.currentThread().getName());
                    }

                }
            }).start();

            System.out.println("Waiting");
            reached.await();

            try (CloseableLock notUsed3 = new CloseableLock(lock2)) {
                System.out.printf("%s done%n", Thread.currentThread().getName());
            }

        }
        catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    private static final class CloseableLock implements AutoCloseable {

        private final Lock impl;

        public CloseableLock(Lock impl) {
            this.impl = Objects.requireNonNull(impl);
            this.impl.lock();
        }

        @Override
        public void close() {
            impl.unlock();
        }
    }

}
