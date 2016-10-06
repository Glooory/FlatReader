package com.glooory.flatreader.ui.gank;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.glooory.flatreader.R;
import com.glooory.flatreader.adapter.GankAdapter;
import com.glooory.flatreader.base.BaseFragment;
import com.glooory.flatreader.constants.Constants;
import com.glooory.flatreader.entity.gank.GankBean;

import java.util.List;

/**
 * Created by Glooory on 2016/10/6 0006 12:53.
 */

public class GankFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener,
        GankContrast.GankView, BaseQuickAdapter.RequestLoadMoreListener{
    private GankContrast.GankPresenter mPresenter;
    private SwipeRefreshLayout mSwipeLayout;
    private RecyclerView mRecyclerView;
    private GankAdapter mAdapter;

    public static GankFragment newInstance() {
        return new GankFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new GankPresenter(mContext, this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mSwipeLayout = (SwipeRefreshLayout) inflater.inflate(R.layout.view_swipe_recycler, container, false);
        initView();
        mPresenter.loadGankDataFirstTime();
        return mSwipeLayout;
    }

    private void initView() {
        mRecyclerView = (RecyclerView) mSwipeLayout.findViewById(R.id.recycler);
        mSwipeLayout.setColorSchemeColors(mContext.getResources().getColor(R.color.red_g_i), mContext.getResources().getColor(R.color.green_g_i),
                mContext.getResources().getColor(R.color.blue_g_i), mContext.getResources().getColor(R.color.yellow_g_i));
        mSwipeLayout.setOnRefreshListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        initAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initAdapter() {
        mAdapter = new GankAdapter(mContext);

        //正在加载的footer
        View loadingFooter = LayoutInflater.from(mContext).inflate(R.layout.view_loading_footer, mRecyclerView, false);
        mAdapter.setLoadingView(loadingFooter);
        mAdapter.setOnLoadMoreListener(this);
        mAdapter.openLoadMore(Constants.PAGE_SIZE);

        mRecyclerView.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void SimpleOnItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                // TODO: 2016/10/6 0006 launch gank detail activity
                GankDetailActivity.launch(getActivity(),
                        ((GankBean) baseQuickAdapter.getItem(i)).getDesc(),
                        ((GankBean) baseQuickAdapter.getItem(i)).getUrl());
            }
        });
    }

    @Override
    public void onRefresh() {
        mPresenter.loadGankDataFirstTime();
    }

    @Override
    public void setNewGankData(List<GankBean> gankList) {
        mAdapter.setNewData(gankList);
    }

    @Override
    public void addGankData(List<GankBean> gankList) {
        mAdapter.addData(gankList);
    }

    @Override
    public void setPresenter(GankContrast.GankPresenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void showProgress() {
        mSwipeLayout.setRefreshing(true);
    }

    @Override
    public void dismissProgress() {
        mSwipeLayout.setRefreshing(false);
    }

    @Override
    public void onLoadMoreRequested() {
        mPresenter.loadMoreGankData();
    }
}
