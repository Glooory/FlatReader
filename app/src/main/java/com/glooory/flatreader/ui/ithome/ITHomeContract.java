package com.glooory.flatreader.ui.ithome;

import com.glooory.flatreader.base.BasePresenter;
import com.glooory.flatreader.base.BaseView;
import com.glooory.flatreader.entity.ithome.ITHomeItemBean;

import java.util.List;

/**
 * Created by Glooory on 2016/10/12 0012 12:47.
 */

public interface ITHomeContract {

    interface View extends BaseView<Presenter> {
        void setNewITData(List<ITHomeItemBean> itItemList);

        void addITData(List<ITHomeItemBean> itItemList);
    }

    interface Presenter extends BasePresenter {
        void loadNewest();

        void loadMore();
    }

}
