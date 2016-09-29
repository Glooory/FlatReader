package com.glooory.flatreader.ui.ribao;

import com.glooory.flatreader.base.BasePresenter;
import com.glooory.flatreader.base.BaseView;
import com.glooory.flatreader.entity.ribao.RibaoStoryBean;

import java.util.List;

/**
 * Created by Glooory on 2016/9/29 0029 16:47.
 */

public interface RibaoContract {

    interface View extends BaseView<Presenter> {

        void setNewStoryData(List<RibaoStoryBean> storyList);

//        void addStoryData(List<RibaoStoryBean> storyList);

    }

    interface  Presenter extends BasePresenter{
        void getStoriesFirst();
//
//        void getLatest();
//
//        void getDataByDate(String date);
//
//        void getDataFromCache();
    }

}
