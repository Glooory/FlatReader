package com.glooory.flatreader.ui.ribao;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.glooory.flatreader.R;
import com.glooory.flatreader.adapter.RibaoAdapter;
import com.glooory.flatreader.base.BaseFragment;
import com.glooory.flatreader.entity.ribao.RibaoStoryBean;
import com.orhanobut.logger.Logger;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by Glooory on 2016/9/29 0029 17:38.
 */

public class RibaoFragment extends BaseFragment implements RibaoContract.View,
        SwipeRefreshLayout.OnRefreshListener{
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeLayout;
    private RibaoContract.Presenter mPresenter;
    private RibaoAdapter mAdapter;

    public static RibaoFragment newInstance() {
        return new RibaoFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mSwipeLayout = (SwipeRefreshLayout) inflater.inflate(R.layout.view_swipe_recycler, container, false);
        mSwipeLayout.setOnRefreshListener(this);
        mRecyclerView = ButterKnife.findById(mSwipeLayout, R.id.recycler);
        initAdapter();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(mAdapter);
        mPresenter.getStoriesFirst();
        return mSwipeLayout;
    }

    private void initAdapter() {
        mAdapter = new RibaoAdapter(mContext);


    }

    @Override
    public void setPresenter(RibaoContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void showProgressDialog() {
        mSwipeLayout.setRefreshing(true);
    }

    @Override
    public void hideProgressDialog() {
        mSwipeLayout.setRefreshing(false);
    }

    @Override
    public void showError(String errorMessage) {
        Logger.d(errorMessage);
    }

    @Override
    public void setNewStoryData(List<RibaoStoryBean> storyList) {
        mAdapter.setNewData(storyList);
    }

    @Override
    public void onRefresh() {
        mPresenter.getStoriesFirst();
    }
}
