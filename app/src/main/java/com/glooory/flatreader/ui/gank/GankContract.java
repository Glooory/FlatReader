package com.glooory.flatreader.ui.gank;

import com.glooory.flatreader.base.BasePresenter;
import com.glooory.flatreader.base.BaseView;
import com.glooory.flatreader.entity.gank.GankBean;

import java.util.List;

/**
 * Created by Glooory on 2016/10/6 0006 12:53.
 */

public interface GankContract {

    interface View extends BaseView<Presenter> {

        void setNewGankData(List<GankBean> gankList);

        void addGankData(List<GankBean> gankList);

    }

    interface Presenter extends BasePresenter {

        void loadGankDataFirstTime();

        void loadMoreGankData();

    }


}
