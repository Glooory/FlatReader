package com.glooory.flatreader.ui.gank;

import android.content.Context;

import com.glooory.flatreader.base.BasePresenterImpl;
import com.glooory.flatreader.constants.Constants;
import com.glooory.flatreader.entity.gank.GankBean;
import com.glooory.flatreader.entity.gank.GankListBean;
import com.glooory.flatreader.net.RetrofitHelpler;
import com.glooory.flatreader.net.SimpleSubscriber;

import java.util.List;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Glooory on 2016/10/6 0006 12:54.
 */

public class GankPresenter extends BasePresenterImpl implements GankContract.GankPresenter {
    private Context mContext;
    private GankContract.GankView mView;
    private int mPage;

    public GankPresenter(Context context, GankContract.GankView view) {
        this.mContext = context;
        this.mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void loadGankDataFirstTime() {
        mView.showProgress();
        mPage = 1;
        Subscription s = RetrofitHelpler.getInstance()
                .getGankService()
                .httpForGankList(Constants.CATEGORY_ANDROID, Constants.PAGE_SIZE, mPage)
                .map(new Func1<GankListBean, List<GankBean>>() {
                    @Override
                    public List<GankBean> call(GankListBean gankListBean) {
                        return gankListBean.getResults();
                    }
                })
                .filter(new Func1<List<GankBean>, Boolean>() {
                    @Override
                    public Boolean call(List<GankBean> gankBeen) {
                        return gankBeen.size() > 0;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SimpleSubscriber<List<GankBean>>(mContext) {
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
                    public void onNext(List<GankBean> gankBeen) {
                        mView.setNewGankData(gankBeen);
                        mPage += mPage;
                    }
                });
        addSubscription(s);
    }

    @Override
    public void loadMoreGankData() {
        Subscription s = RetrofitHelpler.getInstance()
                .getGankService()
                .httpForGankList(Constants.CATEGORY_ANDROID, Constants.PAGE_SIZE, mPage)
                .map(new Func1<GankListBean, List<GankBean>>() {
                    @Override
                    public List<GankBean> call(GankListBean gankListBean) {
                        return gankListBean.getResults();
                    }
                })
                .filter(new Func1<List<GankBean>, Boolean>() {
                    @Override
                    public Boolean call(List<GankBean> gankBeen) {
                        return gankBeen.size() > 0;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SimpleSubscriber<List<GankBean>>(mContext) {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }

                    @Override
                    public void onNext(List<GankBean> gankBeen) {
                        mView.addGankData(gankBeen);
                        mPage += mPage;
                    }
                });
        addSubscription(s);
    }
}