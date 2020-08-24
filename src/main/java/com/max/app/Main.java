package com.max.app;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public final class Main {

    public static void main(String[] args) throws Exception {


        myRange(10, 20)
                .subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.io())
                .subscribe(value -> System.out.println(value));

        TimeUnit.SECONDS.sleep(30);

        System.out.printf("java version: %s%n", System.getProperty("java.version"));
    }

    static Flowable<Integer> myRange(int lower, int upper) {
        return Flowable.generate(
                () -> new AtomicInteger(lower - 1),
                (state, emitter) -> {
                    if (state.get() == upper) {
                        emitter.onComplete();
                    }
                    else {
                        emitter.onNext(state.incrementAndGet());
                    }
                });

    }

    static Flowable<Long> randomGeneratorFlowable() {
        return Flowable.generate(emitter -> {
            emitter.onNext(ThreadLocalRandom.current().nextLong());

        });
    }


}
