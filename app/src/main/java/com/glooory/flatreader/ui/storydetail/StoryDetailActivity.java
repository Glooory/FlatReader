package com.glooory.flatreader.ui.storydetail;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.glooory.flatreader.R;
import com.glooory.flatreader.base.BaseActivity;
import com.glooory.flatreader.constants.Constants;
import com.glooory.flatreader.entity.ribao.RibaoStoryContentBean;
import com.glooory.flatreader.net.FrescoLoader;
import com.glooory.flatreader.util.WebUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Glooory on 2016/10/1 0001 16:49.
 */

public class StoryDetailActivity extends BaseActivity implements StoryDetailContract.StoryDetailView {
    @BindView(R.id.img_story_pic)
    SimpleDraweeView mStoryHeadImg;
    @BindView(R.id.tv_story_detail_title)
    TextView mStoryTitleTv;
    @BindView(R.id.tv_story_detail_pic_author)
    TextView mPicAuthorTv;
    @BindView(R.id.toolbar_story)
    Toolbar mToolbar;
    @BindView(R.id.webview_story)
    WebView mWebview;
    @BindView(R.id.coordinator_story)
    CoordinatorLayout mCoordinator;

    private String mStoryId;
    private StoryDetailContract.StoryDetailPresenter mPresenter;

    public static void launch(Activity activity, String storyId, SimpleDraweeView tranImg) {
        Intent intent = new Intent(activity, StoryDetailActivity.class);
        intent.putExtra(Constants.STORY_ID, storyId);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tranImg.setTransitionName(activity.getString(R.string.shared_imga_transition_name));
            activity.startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(
                    activity, tranImg, activity.getString(R.string.shared_imga_transition_name)
            ).toBundle());
        } else {
            activity.startActivity(intent);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);
        ButterKnife.bind(this);
        new StoryDetailPresenter(mContext, this);
        mStoryId = getIntent().getStringExtra(Constants.STORY_ID);
        initView();
        mPresenter.loadStory(mStoryId);
    }

    private void initView() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        //配置Webview
        WebSettings webSettings = mWebview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setAppCachePath(getCacheDir().getAbsolutePath() + "/webviewcache");
        webSettings.setAppCacheEnabled(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWebview.setWebChromeClient(new WebChromeClient());
    }

    private void loadStoryContent(RibaoStoryContentBean bean) {
        new FrescoLoader.Builder(mContext, mStoryHeadImg, bean.getImage())
                .build();
        mStoryTitleTv.setText(bean.getTitle());
        mPicAuthorTv.setText(bean.getImage_source());
        String url = bean.getShare_url();
        boolean isEmpty = TextUtils.isEmpty(bean.getBody());
        String storyBody = bean.getBody();
        List<String> cssList = bean.getCss();
        if (isEmpty) {
            mWebview.loadUrl(url);
        } else {
            String data = WebUtils.buildHtmlWithCss(storyBody, cssList, false);
            mWebview.loadDataWithBaseURL(WebUtils.BASE_URL, data, WebUtils.MIME_TYPE, WebUtils.ENCODING, WebUtils.FAIL_URL);
        }
    }

    @Override
    public void showStoryContent(RibaoStoryContentBean bean) {
        loadStoryContent(bean);
    }

    @Override
    public void setPresenter(StoryDetailContract.StoryDetailPresenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void dismissProgress() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mWebview != null && mWebview.getParent() != null) {
            ((ViewGroup) mWebview.getParent()).removeView(mWebview);
            mWebview.destroy();
            mWebview = null;
        }
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }
}
