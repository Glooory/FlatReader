package com.glooory.flatreader.ui.ribao;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.glooory.flatreader.R;
import com.glooory.flatreader.adapter.RibaoSectionAdapter;
import com.glooory.flatreader.base.BaseFragment;
import com.glooory.flatreader.base.MyApplication;
import com.glooory.flatreader.entity.ribao.RibaoStoryBean;
import com.glooory.flatreader.greendao.DaoSession;
import com.glooory.flatreader.greendao.RibaoStoryBeanDao;
import com.glooory.flatreader.listener.OnSectionChangeListener;
import com.glooory.flatreader.ui.MainActivity;
import com.glooory.flatreader.ui.storydetail.StoryDetailActivity;
import com.glooory.flatreader.util.GreenDaoUtils;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by Glooory on 2016/9/29 0029 17:38.
 */

public class RibaoFragment extends BaseFragment implements RibaoContract.View,
        SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.RequestLoadMoreListener {
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeLayout;
    private RibaoContract.Presenter mPresenter;
    private RibaoSectionAdapter mAdapter;
    private int mPageSize = 30; //用来触发Adapter上拉自动加载的关键字
    private LinearLayoutManager mLinearLayoutManager;
    private String mCurrentTitle;
    private OnSectionChangeListener mSectionListener;
    private RibaoStoryBeanDao mRibaoDao;

    public static RibaoFragment newInstance() {
        return new RibaoFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity) {
            mSectionListener = (OnSectionChangeListener) context;
        }
        mCurrentTitle = context.getString(R.string.title_ribao_latest);
        DaoSession daoSession = ((MyApplication) context.getApplicationContext()).getDaoSession();
        mRibaoDao = daoSession.getRibaoStoryBeanDao();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new RibaoPresenter(mContext, this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mSwipeLayout = (SwipeRefreshLayout) inflater.inflate(R.layout.view_swipe_recycler, container, false);
        initView();
        mPresenter.getLatestStories();
        return mSwipeLayout;
    }

    private void initView() {
        mSwipeLayout.setColorSchemeColors(mContext.getResources().getColor(R.color.red_g_i), mContext.getResources().getColor(R.color.green_g_i),
                mContext.getResources().getColor(R.color.blue_g_i), mContext.getResources().getColor(R.color.yellow_g_i));
        mSwipeLayout.setOnRefreshListener(this);
        mRecyclerView = ButterKnife.findById(mSwipeLayout, R.id.recycler);
        mLinearLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        initAdapter();
        mRecyclerView.setAdapter(mAdapter);

    }

    private void initAdapter() {
        mAdapter = new RibaoSectionAdapter(mContext);

        //正在加载的footer
        View loadingFooter = LayoutInflater.from(mContext).inflate(R.layout.view_loading_footer, mRecyclerView, false);
        mAdapter.setLoadingView(loadingFooter);

        mRecyclerView.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void SimpleOnItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                if (!GreenDaoUtils.isEntityExists(mRibaoDao,
                        RibaoStoryBeanDao.Properties.Id.eq(((RibaoStoryBean) mAdapter.getItem(i)).getId()))) {
                    ((TextView) view.findViewById(R.id.tv_ribao_item_title))
                            .setTextColor(getResources().getColor(R.color.colorSecondaryText));
                    GreenDaoUtils.insert(mRibaoDao, mAdapter.getItem(i), RibaoStoryBeanDao.Properties.IdPrimary);
                }
                StoryDetailActivity.launch(getActivity(),
                        String.valueOf(((RibaoStoryBean) mAdapter.getItem(i)).getId()),
                        (ImageView) view.findViewById(R.id.img_card_ribao_item));
            }
        });
    }

    @Override
    public void setPresenter(RibaoContract.Presenter presenter) {
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
    public void showLoadFailed() {
        mAdapter.showLoadMoreFailedView();
    }

    @Override
    public void setNewStoryData(final List<RibaoStoryBean> storyList) {
        mPageSize = storyList.size();
        mAdapter.openLoadMore(mPageSize);
        mAdapter.setNewData(storyList);
        updateAdpaterParameters();

    }

    private void updateAdpaterParameters() {
        mAdapter.setOnLoadMoreListener(this);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                //改变Toolbar的title为当前内容所属于的日期
                int firstVisiblePosition = mLinearLayoutManager.findFirstVisibleItemPosition();
                if (!mCurrentTitle.equals(((RibaoStoryBean) mAdapter.getItem(firstVisiblePosition)).getDate())) {
                    mCurrentTitle = ((RibaoStoryBean) mAdapter.getItem(firstVisiblePosition)).getDate();
                    if (mSectionListener != null) {
                        mSectionListener.onSectionChange(mCurrentTitle);
                    }
                }
            }
        });
    }

    @Override
    public void addStoryData(List<RibaoStoryBean> storyList) {
        mPageSize = storyList.size() - 1;
        mAdapter.openLoadMore(mPageSize);
        mAdapter.addData(storyList);
    }

    @Override
    public void onRefresh() {
        mPresenter.getLatestStories();
    }

    @Override
    public void onLoadMoreRequested() {
        mPresenter.getPastStories();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }
}
