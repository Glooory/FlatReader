package com.glooory.flatreader.ui.ithomecontent;

import android.content.Context;

import com.glooory.flatreader.base.BasePresenterImpl;
import com.glooory.flatreader.entity.ithome.ITHomeContentBean;
import com.glooory.flatreader.net.ITHomeRequest;
import com.glooory.flatreader.net.SimpleSubscriber;
import com.glooory.flatreader.util.ITHomeUtils;
import com.orhanobut.logger.Logger;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Glooory on 2016/10/13 0013 10:30.
 */

public class ITHomeContentPresenter extends BasePresenterImpl implements ITContentContract.Presenter {
    private Context mContext;
    private ITContentContract.View mView;

    public ITHomeContentPresenter(Context context, ITContentContract.View view) {
        this.mContext = context;
        this.mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void loadITHomeContent(String newsId) {
        Subscription s = ITHomeRequest.getITHomeApi()
                .getNewsContent(ITHomeUtils.getSplitNewsId(newsId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SimpleSubscriber<ITHomeContentBean>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        Logger.d(e.getMessage());
                        mView.showLoadFailed();
                    }

                    @Override
                    public void onNext(ITHomeContentBean itHomeContentBean) {
                        mView.showITHomeContent(itHomeContentBean);
                    }
                });
        addSubscription(s);
    }
}
