package com.glooory.flatreader.net;

import com.glooory.flatreader.util.ErrorInfoUtils;
import com.glooory.flatreader.util.ToastUtils;

import rx.Subscriber;

/**
 * Created by Glooory on 2016/10/1 0001 10:31.
 */

public class SimpleSubscriber<T> extends Subscriber<T> {

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        //同一处理错误回调， 显示Toast
        ToastUtils.showToastLong(ErrorInfoUtils.parseHttpErrorInfo(e));
    }

    @Override
    public void onNext(T t) {

    }

}
