package com.max.app.ads;

import io.reactivex.Single;

import java.util.concurrent.TimeUnit;

public class MicrosoftAdsProvider implements AdsProvider {

    @Override
    public Single<String> getAdvertisement() {
        return Single.create(emitter -> {
                                 new Thread(() -> {
                                     try {
                                         TimeUnit.SECONDS.sleep(10);
                                     }
                                     catch (InterruptedException interEx) {
                                         Thread.currentThread().interrupt();
                                         emitter.onError(new IllegalStateException(interEx));
                                     }

                                     emitter.onSuccess("Microsoft awesome");

                                 }).start();
                             }
        );
    }
}
