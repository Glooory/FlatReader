package com.glooory.flatreader.ui.main;

import com.glooory.flatreader.BuildConfig;
import com.glooory.flatreader.base.BasePresenterImpl;
import com.glooory.flatreader.entity.VersionInfoBean;
import com.glooory.flatreader.net.SimpleSubscriber;
import com.glooory.flatreader.net.UpdateRequest;
import com.orhanobut.logger.Logger;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Glooory on 2016/10/31 0031 19:52.
 */

public class MainPresenter extends BasePresenterImpl implements MainContract.Presenter {
    private MainContract.View mView;

    public MainPresenter(MainContract.View view) {
        this.mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void checkUpdate() {
        Subscription s = UpdateRequest.getUpdateApi()
                .checkUpdateInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SimpleSubscriber<VersionInfoBean>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d(e.getMessage());
//                        super.onError(e);
                    }

                    @Override
                    public void onNext(VersionInfoBean bean) {
                        Logger.d(bean);
                        if (isNewVersion(bean)) {
                            mView.showUpdateDialog(bean);
                        }
                    }
                });
        addSubscription(s);
    }

    private boolean isNewVersion(VersionInfoBean bean) {
        int currentVersion = BuildConfig.VERSION_CODE;
        if (bean.getVersioncode() > currentVersion) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void startDownload() {

    }
}
