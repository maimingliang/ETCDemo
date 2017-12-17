package com.maiml.etcbase.mvp;

import rx.Subscription;

public interface Presenter {

    void onStart();

    void onDestroy();

    void unSubscribe(Subscription subscription);
}
