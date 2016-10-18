package com.glooory.flatreader.ui.ithome;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.glooory.flatreader.R;
import com.glooory.flatreader.adapter.ITHomeAdapter;
import com.glooory.flatreader.base.BaseFragment;
import com.glooory.flatreader.constants.Constants;
import com.glooory.flatreader.entity.ithome.ITHomeItemBean;
import com.glooory.flatreader.ui.ithomecontent.ITContentActivity;
import com.glooory.flatreader.util.DBUtils;

import java.util.List;

/**
 * Created by Glooory on 2016/10/12 0012 13:41.
 */

public class ITHomeFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener,
        ITHomeContract.View, BaseQuickAdapter.RequestLoadMoreListener {
    private SwipeRefreshLayout mSwipeLayout;
    private RecyclerView mRecyclerView;
    private ITHomeContract.Presenter mPresenter;
    private ITHomeAdapter mAdapter;
    private int mPageSize = 30;

    public static ITHomeFragment newInstance() {
        ITHomeFragment fragment = new ITHomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new ITHomePresenter(mContext, this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mSwipeLayout = (SwipeRefreshLayout) inflater.inflate(R.layout.view_swipe_recycler, container, false);
        initView();
        mPresenter.loadNewest();
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
        mAdapter = new ITHomeAdapter(mContext);

        //正在加载的footer
        View loadingFooter = LayoutInflater.from(mContext).inflate(R.layout.view_loading_footer, mRecyclerView, false);
        mAdapter.setLoadingView(loadingFooter);
        mAdapter.setOnLoadMoreListener(this);

        mRecyclerView.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void SimpleOnItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                ((TextView) view.findViewById(R.id.tv_ithome_item_title))
                        .setTextColor(getResources().getColor(R.color.colorSecondaryText));
                DBUtils.getDB(mContext)
                        .insertHasRead(Constants.ITHOME, mAdapter.getItem(i).getNewsid(), DBUtils.READ);
                ITContentActivity.launch(getActivity(), mAdapter.getItem(i));
            }
        });
    }

    @Override
    public void onRefresh() {
        mPresenter.loadNewest();
    }

    @Override
    public void setNewITData(List<ITHomeItemBean> itItemList) {
        mPageSize = itItemList.size();
        mAdapter.openLoadMore(mPageSize);
        mAdapter.setNewData(itItemList);
    }

    @Override
    public void addITData(List<ITHomeItemBean> itItemList) {
        mPageSize = itItemList.size();
        mAdapter.openLoadMore(mPageSize);
        mAdapter.addData(itItemList);
    }

    @Override
    public void setPresenter(ITHomeContract.Presenter presenter) {
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
        mPresenter.loadMore();
    }
}
