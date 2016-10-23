package com.glooory.flatreader.base;

import com.glooory.flatreader.R;
import com.glooory.flatreader.util.NetworkUtils;
import com.glooory.flatreader.util.ToastUtils;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Glooory on 2016/9/28 0028 13:33.
 */

public class BasePresenterImpl implements BasePresenter {
    protected CompositeSubscription mCompositeSubscription;

    public void addSubscription(Subscription subscription) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        if (subscription == null) {
            return;
        }
        mCompositeSubscription.add(subscription);
    }

    @Override
    public void detachView() {
        if (mCompositeSubscription != null && !mCompositeSubscription.isUnsubscribed()) {
            mCompositeSubscription.unsubscribe();
        }
    }

    public void checkNetwork() {
        if (!NetworkUtils.isNetworkAvaliable(MyApplication.getInstance())) {
            ToastUtils.showToastLong(R.string.network_unavailable);
        }
    }
}
