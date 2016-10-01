package com.glooory.flatreader.net;

import android.content.Context;

import com.glooory.flatreader.util.ErrorInfoUtils;
import com.glooory.flatreader.util.ToastUtils;

import rx.Subscriber;

/**
 * Created by Glooory on 2016/10/1 0001 10:31.
 */

public class SimpleSubscriber<T> extends Subscriber<T> {
    private Context mContext;

    public SimpleSubscriber(Context context) {
        this.mContext = context;
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        //同一处理错误回调， 显示Toast
        ToastUtils.showToast(mContext, ErrorInfoUtils.parseHttpErrorInfo(e));
    }

    @Override
    public void onNext(T t) {

    }

}
