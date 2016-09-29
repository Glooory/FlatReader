package com.glooory.flatreader.ui.ribao;

import com.glooory.flatreader.entity.ribao.RibaoIStoriesBean;
import com.glooory.flatreader.entity.ribao.RibaoStoryBean;
import com.glooory.flatreader.net.RetrofitHelpler;
import com.orhanobut.logger.Logger;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Glooory on 2016/9/29 0029 17:39.
 */

public class RibaoPresenter implements RibaoContract.Presenter {
    private final RibaoContract.View mView;

    public RibaoPresenter(RibaoContract.View view) {
        this.mView = view;
        this.mView.setPresenter(this);
    }

    @Override
    public void getStoriesFirst() {
        mView.showProgressDialog();
        Subscription s = RetrofitHelpler.getInstance()
                .getRibaoService()
                .getLatest()
                .map(new Func1<RibaoIStoriesBean, List<RibaoStoryBean>>() {
                    @Override
                    public List<RibaoStoryBean> call(RibaoIStoriesBean ribaoIStoriesBean) {
                        return ribaoIStoriesBean.getStories();
                    }
                })
                .filter(new Func1<List<RibaoStoryBean>, Boolean>() {
                    @Override
                    public Boolean call(List<RibaoStoryBean> ribaoStoryBeen) {
                        return ribaoStoryBeen.size() > 0;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<RibaoStoryBean>>() {
                    @Override
                    public void onCompleted() {
                        mView.hideProgressDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showError(e.getMessage());
                        mView.hideProgressDialog();
                    }

                    @Override
                    public void onNext(List<RibaoStoryBean> ribaoStoryBeen) {
                        Logger.d(ribaoStoryBeen.size());
                        mView.setNewStoryData(ribaoStoryBeen);
                    }
                });
        // TODO: 2016/9/29 0029 add subscription
    }

    @Override
    public void detachView() {

    }
}
