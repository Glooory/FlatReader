package com.glooory.flatreader.ui.main;

import com.glooory.flatreader.BuildConfig;
import com.glooory.flatreader.base.BasePresenterImpl;
import com.glooory.flatreader.base.MyApplication;
import com.glooory.flatreader.entity.VersionInfoBean;
import com.glooory.flatreader.net.UpdateRequest;
import com.glooory.flatreader.rx.SimpleSubscriber;
import com.glooory.flatreader.util.SPUtils;
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
                        if (isShowUpdateDialog(bean)) {
                            mView.showUpdateDialog(bean);
                        }
                    }
                });
        addSubscription(s);
    }

    private boolean isShowUpdateDialog(VersionInfoBean bean) {
        boolean isShow = false;
        int lastShouldUpdateVersion = SPUtils.getLastUpdateVersion(MyApplication.getInstance());
        int currentVersion = BuildConfig.VERSION_CODE;
        if (bean.getVersioncode() > currentVersion) {
            if (lastShouldUpdateVersion < bean.getVersioncode()) {
                isShow = true;
            } else {
                isShow = !SPUtils.isDonnotRemindAnymore(MyApplication.getInstance());
            }
        }
        return isShow;
    }
}
