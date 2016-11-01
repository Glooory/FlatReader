package com.glooory.flatreader.rx;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Created by Glooory on 2016/11/1 0001 10:44.
 */

public class RxBus {
    private static volatile RxBus sInstance;
    private final Subject<Object, Object> bus;

    private RxBus() {
        this.bus = new SerializedSubject<>(PublishSubject.create());
    }

    /**
     *  单例 RxBus
     * @return
     */
    public static RxBus getDefault() {
        if (sInstance == null) {
            synchronized (RxBus.class) {
                if (sInstance == null) {
                    sInstance = new RxBus();
                }
            }
        }
        return sInstance;
    }

    /**
     *  发送一个新事件
     * @param o
     */
    public void post(Object o) {
        bus.onNext(o);
    }

    /**
     *  返回特定类型的被观察者
     * @param eventType
     * @param <T>
     * @return
     */
    public <T> Observable<T> toObservable(Class<T> eventType) {
        return bus.ofType(eventType);
    }
}
