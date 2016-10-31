package com.glooory.flatreader.ui.main;

import com.glooory.flatreader.base.BasePresenter;
import com.glooory.flatreader.base.BaseView;
import com.glooory.flatreader.entity.VersionInfoBean;

/**
 * Created by Glooory on 2016/10/31 0031 19:49.
 */

public interface MainContract {

    interface View extends BaseView<Presenter> {
        void showUpdateDialog(VersionInfoBean bean);
    }

    interface Presenter extends BasePresenter {
        void checkUpdate();

        void startDownload();
    }
}
