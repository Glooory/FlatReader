package com.glooory.flatreader.ui.storydetail;

import com.glooory.flatreader.base.BasePresenter;
import com.glooory.flatreader.base.BaseView;
import com.glooory.flatreader.entity.ribao.RibaoStoryContentBean;

/**
 * Created by Glooory on 2016/10/3 0003 10:27.
 */

public interface StoryDetailContract {

    interface StoryDetailView extends BaseView<StoryDetailPresenter> {
        void showStoryContent(RibaoStoryContentBean bean);
    }


    interface StoryDetailPresenter extends BasePresenter {

        void loadStory(String storyId);

    }
}

