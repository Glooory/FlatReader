package com.glooory.flatreader.ui.ithomecontent;

import com.glooory.flatreader.base.BasePresenter;
import com.glooory.flatreader.base.BaseView;
import com.glooory.flatreader.entity.ithome.ITHomeContentBean;

/**
 * Created by Glooory on 2016/10/13 0013 10:24.
 */

public interface ITContentContract {

    interface View extends BaseView<ITContentContract.Presenter>{

        void showITHomeContent(ITHomeContentBean itHomeContentBean);

    }

    interface Presenter extends BasePresenter{

        void loadITHomeContent(String newsId);

    }

}
