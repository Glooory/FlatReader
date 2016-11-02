package com.glooory.flatreader.ui.settings;

import com.glooory.flatreader.BuildConfig;
import com.glooory.flatreader.R;
import com.glooory.flatreader.base.BasePresenterImpl;
import com.glooory.flatreader.entity.VersionInfoBean;
import com.glooory.flatreader.rx.SimpleSubscriber;
import com.glooory.flatreader.net.UpdateRequest;
import com.glooory.flatreader.util.ToastUtils;
import com.orhanobut.logger.Logger;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Glooory on 2016/11/1 0001 10:16.
 */

public class SettingsPresenter extends BasePresenterImpl implements SettingsContact.Presenter {
    private SettingsContact.View mView;

    public SettingsPresenter(SettingsContact.View view) {
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
                        e.printStackTrace();
                        super.onError(e);
                    }

                    @Override
                    public void onNext(VersionInfoBean bean) {
                        if (bean.getVersioncode() > BuildConfig.VERSION_CODE) {
                            mView.ShowUpdateDialog(bean);
                        } else {
                           ToastUtils.showToastShort(R.string.is_newest_version);
                        }
                    }
                });
        addSubscription(s);
    }
}
