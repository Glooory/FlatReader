package com.glooory.flatreader.ui.ribao;

import android.content.Context;

import com.glooory.flatreader.base.BasePresenterImpl;
import com.glooory.flatreader.entity.ribao.RibaoIStoriesBean;
import com.glooory.flatreader.entity.ribao.RibaoStoryBean;
import com.glooory.flatreader.net.RibaoRequest;
import com.glooory.flatreader.rx.SimpleSubscriber;
import com.glooory.flatreader.util.DateUtils;

import java.util.List;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Glooory on 2016/9/29 0029 17:39.
 */

public class RibaoPresenter extends BasePresenterImpl implements RibaoContract.Presenter {
    private final RibaoContract.View mView;
    private String mTargetDate;
    private StringBuilder mSectionTitle;
    private Context mContext;

    public RibaoPresenter(Context context, RibaoContract.View view) {
        this.mContext = context;
        this.mView = view;
        this.mView.setPresenter(this);
        mSectionTitle = new StringBuilder();
    }

    @Override
    public void getLatestStories() {
        checkNetwork();
        mView.showProgress();
        Subscription s = RibaoRequest.getRibaoApi()
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
                .map(new Func1<List<RibaoStoryBean>, List<RibaoStoryBean>>() {
                    @Override
                    public List<RibaoStoryBean> call(List<RibaoStoryBean> storyBeanList) {
                        for (RibaoStoryBean bean : storyBeanList) {
                            bean.setDate("今日热闻");
                        }
                        return storyBeanList;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SimpleSubscriber<List<RibaoStoryBean>>() {
                    @Override
                    public void onCompleted() {
                        mView.dismissProgress();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mView.dismissProgress();
                    }

                    @Override
                    public void onNext(List<RibaoStoryBean> storyBeanList) {
                        mView.setNewStoryData(storyBeanList);
                    }
                });
        addSubscription(s);
    }

    @Override
    public void getPastStories() {
        Subscription s = RibaoRequest.getRibaoApi()
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
                .map(new Func1<List<RibaoStoryBean>, List<RibaoStoryBean>>() {
                    @Override
                    public List<RibaoStoryBean> call(List<RibaoStoryBean> storyBeanList) {
                        mSectionTitle.setLength(0);
                        mSectionTitle.append(DateUtils.dateToPattern("yyyyMMdd", "MM月dd日", mTargetDate))
                                .append(" ")
                                .append(DateUtils.getWeekOfDate(DateUtils.dateToMillis("yyyyMMdd", mTargetDate)));
                        RibaoStoryBean sectionHeader = new RibaoStoryBean(true, mSectionTitle.toString());
                        storyBeanList.add(0, sectionHeader);
                        for (RibaoStoryBean bean : storyBeanList) {
                            bean.setDate(mSectionTitle.toString());
                        }
                        return storyBeanList;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SimpleSubscriber<List<RibaoStoryBean>>() {
                    @Override
                    public void onCompleted() {
                        mView.dismissProgress();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mView.dismissProgress();
                        mView.showLoadFailed();
                    }

                    @Override
                    public void onNext(List<RibaoStoryBean> storyBeanList) {
                        mView.addStoryData(storyBeanList);
                    }
                });
        addSubscription(s);
    }
}
