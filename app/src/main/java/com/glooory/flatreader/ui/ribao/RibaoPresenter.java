package com.glooory.flatreader.ui.ribao;

import com.glooory.flatreader.entity.ribao.RibaoIStoriesBean;
import com.glooory.flatreader.entity.ribao.RibaoStoryBean;
import com.glooory.flatreader.net.RetrofitHelpler;
import com.glooory.flatreader.util.DateUtil;

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
    private String mTargetDate;
    private StringBuilder mSectionTitle;

    public RibaoPresenter(RibaoContract.View view) {
        this.mView = view;
        this.mView.setPresenter(this);
        mSectionTitle = new StringBuilder();
    }

    @Override
    public void getLatestStories() {
        mView.showProgressDialog();
        Subscription s = RetrofitHelpler.getInstance()
                .getRibaoService()
                .getLatest()
                .map(new Func1<RibaoIStoriesBean, List<RibaoStoryBean>>() {
                    @Override
                    public List<RibaoStoryBean> call(RibaoIStoriesBean ribaoIStoriesBean) {
                        mTargetDate = ribaoIStoriesBean.getDate();
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
                        for (RibaoStoryBean bean : ribaoStoryBeen) {
                            bean.setDate("今日热闻");
                        }
                        mView.setNewStoryData(ribaoStoryBeen);
                    }
                });
        // TODO: 2016/9/29 0029 add subscription
    }

    @Override
    public void getPastStories() {

        Subscription s = RetrofitHelpler.getInstance()
                .getRibaoService()
                .getByDate(mTargetDate)
                .map(new Func1<RibaoIStoriesBean, List<RibaoStoryBean>>() {
                    @Override
                    public List<RibaoStoryBean> call(RibaoIStoriesBean ribaoIStoriesBean) {
                        mTargetDate = ribaoIStoriesBean.getDate();
                        return ribaoIStoriesBean.getStories();
                    }
                })
                .filter(new Func1<List<RibaoStoryBean>, Boolean>() {
                    @Override
                    public Boolean call(List<RibaoStoryBean> storyBeanList) {
                        return storyBeanList.size() > 0;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<RibaoStoryBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<RibaoStoryBean> storyBeanList) {
                        mSectionTitle.setLength(0);
                        mSectionTitle.append(DateUtil.dateToMd("yyyyMMdd", "MM月dd日", mTargetDate))
                                .append(" ")
                                .append(DateUtil.getWeekOfDate(DateUtil.dateToMillis("yyyyMMdd", mTargetDate)));
                        RibaoStoryBean sectionHeader = new RibaoStoryBean(true, mSectionTitle.toString());
                        storyBeanList.add(0, sectionHeader);
                        for (RibaoStoryBean bean : storyBeanList) {
                            bean.setDate(mSectionTitle.toString());
                        }
                        mView.addStoryData(storyBeanList);
                    }
                });
        // TODO: 2016/9/30 0030 deal with the subscription
    }

    @Override
    public void detachView() {

    }
}
