package com.glooory.flatreader.ui.settings;

import com.glooory.flatreader.base.BasePresenter;
import com.glooory.flatreader.base.BaseView;
import com.glooory.flatreader.entity.VersionInfoBean;

/**
 * Created by Glooory on 2016/11/1 0001 10:13.
 */

public interface SettingsContact {

    interface View extends BaseView<Presenter> {
        void ShowUpdateDiolog(VersionInfoBean bean);
    }

    interface Presenter extends BasePresenter {
        void checkUpdate();

        void startDownload(String downloadUrl);
    }
}

