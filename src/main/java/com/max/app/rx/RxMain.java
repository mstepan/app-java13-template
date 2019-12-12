package com.max.app.rx;

import io.reactivex.Observable;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.schedulers.Schedulers;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public final class RxMain {

    private static final Logger LOG = LoggerFactory.getLogger(RxMain.class);

    public static void main(String[] args) throws Exception {

        final SecureRandom rand = new SecureRandom();

        ConnectableObservable<Integer> obs = Observable.range(1, 5).
                map(unused -> rand.nextInt(100)).
                publish();

        obs.subscribe(value -> {
            LOG.info("obs1: {}", value);
        });

        obs.reduce(0, Integer::sum).
                subscribe(value -> {
                    LOG.info("obs2: {}", value);
                });

        obs.reduce(1, (total, cur) -> total * cur).
                subscribe(value -> {
                    LOG.info("obs3: {}", value);
                });

        obs.connect();

        TimeUnit.SECONDS.sleep(3);
    }

    private static void fetchUrlsContent() throws Exception {
        String[] urls = {
                "https://jsoup.org/",
                "https://stackoverflow.com/questions/12035316/reading-entire-html-file-to-string",
                "https://mvnrepository.com/artifact/org.jsoup/jsoup",
                "https://learning.oreilly.com/library/view/learning-rxjava/" +
                        "9781787120426/964f5943-b955-49f7-b53e-801754d06c3c.xhtml",
                "https://dou.ua/",
                "https://google.com"
        };

        final CountDownLatch completed = new CountDownLatch(1);

        final long startTime = System.currentTimeMillis();

        final Observable<String> titlesStream = Observable.fromArray(urls).
                flatMap(baseUrl -> Observable.just(baseUrl).
                        subscribeOn(Schedulers.io()).
                        map(RxMain::readTitle));

        final Observable<String> locationsStream = Observable.fromArray(urls).
                flatMap(baseUrl -> Observable.just(baseUrl).
                        subscribeOn(Schedulers.io()).
                        map(RxMain::readLocation));

        //----------------------------------------------------------------------------------------
        // zip replacement (should be thread safe)
        //----------------------------------------------------------------------------------------
//        SynchronousQueue<String> titleQueue = new SynchronousQueue<>();
//
//        titlesStream.subscribe(value -> {
//                                   titleQueue.put(value);
//                               },
//                               Throwable::printStackTrace);
//
//        locationsStream.doFinally(completed::countDown).
//                subscribe(location -> {
//                    String title = titleQueue.take();
//                    System.out.printf(" %s <==> %s (thread: %s)%n", title, location, Thread.currentThread().getName());
//                }, Throwable::printStackTrace);

        //----------------------------------------------------------------------------------------


        // typical zip combiner
        titlesStream.zipWith(locationsStream, (first, second) -> first + " <==> " + second).
                doFinally(completed::countDown).
                subscribe(System.out::println, Throwable::printStackTrace);

        completed.await();

        final long endTime = System.currentTimeMillis();

        LOG.info("time: {}", (endTime - startTime));
    }

    private static String readTitle(String url) {
        try {
            Document document = Jsoup.connect(url).get();
            return document.title();
        }
        catch (IOException ioEx) {
            LOG.error("Read title error", ioEx);
            return null;
        }
    }

    private static String readLocation(String url) {
        try {
            Document document = Jsoup.connect(url).get();
            return document.location();
        }
        catch (IOException ioEx) {
            LOG.error("Read location error", ioEx);
            return null;
        }
    }
}

