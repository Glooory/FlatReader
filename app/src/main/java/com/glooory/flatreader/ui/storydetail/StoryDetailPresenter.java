package com.glooory.flatreader.ui.storydetail;

import android.content.Context;

import com.glooory.flatreader.base.BasePresenterImpl;
import com.glooory.flatreader.entity.ribao.RibaoStoryContentBean;
import com.glooory.flatreader.net.RetrofitHelpler;
import com.glooory.flatreader.net.SimpleSubscriber;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Glooory on 2016/10/3 0003 10:37.
 */

public class StoryDetailPresenter extends BasePresenterImpl implements StoryDetailContract.StoryDetailPresenter {
    private StoryDetailContract.StoryDetailView mView;
    private Context mContext;

    public StoryDetailPresenter(Context context, StoryDetailContract.StoryDetailView view) {
        this.mContext = context;
        this.mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void loadStory(String storyId) {

        Subscription s = RetrofitHelpler.getInstance()
                .getRibaoService()
                .getStoryContent(storyId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SimpleSubscriber<RibaoStoryContentBean>(mContext) {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(RibaoStoryContentBean bean) {
                        mView.showStoryContent(bean);
                    }
                });
        addSubscription(s);
    }

}
