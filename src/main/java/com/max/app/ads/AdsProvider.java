package com.max.app.ads;

import io.reactivex.Single;

public interface AdsProvider {

    Single<String> getAdvertisement();
}
