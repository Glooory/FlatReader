package com.glooory.flatreader.ui.ithome;

import android.content.Context;

import com.glooory.flatreader.base.BasePresenterImpl;
import com.glooory.flatreader.entity.ithome.ITHomeItemBean;
import com.glooory.flatreader.entity.ithome.ITResponse;
import com.glooory.flatreader.net.RetrofitHelpler;
import com.glooory.flatreader.net.SimpleSubscriber;
import com.glooory.flatreader.util.ITHomeUtils;
import com.orhanobut.logger.Logger;

import java.util.Iterator;
import java.util.List;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Glooory on 2016/10/12 0012 13:42.
 */

public class ITHomePresenter extends BasePresenterImpl implements ITHomeContract.Presenter {
    private Context mContext;
    private ITHomeContract.View mView;
    private String mLastITDataId;

    public ITHomePresenter(Context context, ITHomeContract.View view) {
        this.mContext = context;
        this.mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void loadNewest() {
        mView.showProgress();
        mLastITDataId = "0";
        Subscription s = RetrofitHelpler.getInstance()
                .getItHomeApi()
                .getITHomeNewest()
                .map(new Func1<ITResponse, List<ITHomeItemBean>>() {
                    @Override
                    public List<ITHomeItemBean> call(ITResponse itResponse) {
                        return itResponse.getChannel().getItems();
                    }
                })
                .map(new Func1<List<ITHomeItemBean>, List<ITHomeItemBean>>() {
                    @Override
                    public List<ITHomeItemBean> call(List<ITHomeItemBean> itHomeItemBeanList) {
                        //过滤掉广告
                        Iterator<ITHomeItemBean> iterator = itHomeItemBeanList.iterator();
                        while (iterator.hasNext()) {
                            ITHomeItemBean bean = iterator.next();
                            if (bean.getUrl().contains("digi")) {
                                iterator.remove();
                            }
                        }
                        return itHomeItemBeanList;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SimpleSubscriber<List<ITHomeItemBean>>(mContext) {
                    @Override
                    public void onCompleted() {
                        mView.dismissProgress();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.dismissProgress();
                        super.onError(e);
                        Logger.d(e.getMessage());
                    }

                    @Override
                    public void onNext(List<ITHomeItemBean> itHomeItemBeanList) {
                        mLastITDataId = itHomeItemBeanList.get(itHomeItemBeanList.size() - 1).getNewsid();
                        mView.setNewITData(itHomeItemBeanList);
                    }
                });
        addSubscription(s);
    }

    @Override
    public void loadMore() {
        Logger.d(mLastITDataId);
        Subscription s = RetrofitHelpler.getInstance()
                .getItHomeApi()
                .getITHomeMore(ITHomeUtils.getMinNewsId(mLastITDataId))
                .map(new Func1<ITResponse, List<ITHomeItemBean>>() {
                    @Override
                    public List<ITHomeItemBean> call(ITResponse itResponse) {
                        return itResponse.getChannel().getItems();
                    }
                })
                .map(new Func1<List<ITHomeItemBean>, List<ITHomeItemBean>>() {
                    @Override
                    public List<ITHomeItemBean> call(List<ITHomeItemBean> itHomeItemBeanList) {
                        //过滤掉广告
                        Iterator<ITHomeItemBean> iterator = itHomeItemBeanList.iterator();
                        while (iterator.hasNext()) {
                            ITHomeItemBean bean = iterator.next();
                            if (bean.getUrl().contains("digi")) {
                                iterator.remove();
                            }
                        }
                        return itHomeItemBeanList;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SimpleSubscriber<List<ITHomeItemBean>>(mContext) {
                    @Override
                    public void onCompleted() {
                        mView.dismissProgress();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.dismissProgress();
                        super.onError(e);
                        Logger.d(e.getMessage());
                    }

                    @Override
                    public void onNext(List<ITHomeItemBean> itHomeItemBeanList) {
                        mLastITDataId = itHomeItemBeanList.get(itHomeItemBeanList.size() - 1).getNewsid();
                        mView.addITData(itHomeItemBeanList);
                    }
                });
        addSubscription(s);
    }
}
