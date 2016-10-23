package com.glooory.flatreader.ui.storydetail;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.transition.Fade;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

import com.glooory.flatreader.R;
import com.glooory.flatreader.base.BaseActivity;
import com.glooory.flatreader.constants.Constants;
import com.glooory.flatreader.entity.ribao.RibaoStoryContentBean;
import com.glooory.flatreader.net.ImageLoader;
import com.glooory.flatreader.util.WebUtils;

import net.opacapp.multilinecollapsingtoolbar.CollapsingToolbarLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Glooory on 2016/10/1 0001 16:49.
 */

public class StoryDetailActivity extends BaseActivity implements StoryDetailContract.StoryDetailView {
    @BindView(R.id.img_story_pic)
    ImageView mStoryHeadImg;
    @BindView(R.id.toolbar_story)
    Toolbar mToolbar;
    @BindView(R.id.webview_story)
    WebView mWebview;
    @BindView(R.id.coordinator_story)
    CoordinatorLayout mCoordinator;
    @BindView(R.id.collapsing_toolbar_story)
    CollapsingToolbarLayout mCollapsingToolbar;

    private String mStoryId;
    private StoryDetailContract.StoryDetailPresenter mPresenter;

    public static void launch(Activity activity, String storyId, ImageView tranImg) {
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
        setupWindowAnimations();
        ButterKnife.bind(this);
        new StoryDetailPresenter(mContext, this);
        mStoryId = getIntent().getStringExtra(Constants.STORY_ID);
        initView();
        checkNetwork();
        mPresenter.loadStory(mStoryId);
    }

    private void setupWindowAnimations() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Fade fade = new Fade();
            fade.setDuration(200);
            getWindow().setAllowEnterTransitionOverlap(true);
            getWindow().setAllowReturnTransitionOverlap(true);
            getWindow().setEnterTransition(fade);
        }
    }

    @Override
    protected void setStatusBar() {
    }

    private void initView() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        //配置Webview
//        if (Build.VERSION.SDK_INT >= 19) {
//            mWebview.setLayerType(View.LAYER_TYPE_HARDWARE, null);
//        }
//        else {
//            mWebview.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
//        }
        WebSettings webSettings = mWebview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setAppCachePath(getCacheDir().getAbsolutePath() + "/webviewcache");
        webSettings.setAppCacheEnabled(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWebview.setWebChromeClient(new WebChromeClient());
    }

    private void loadStoryContent(RibaoStoryContentBean bean) {
        ImageLoader.load(StoryDetailActivity.this, mStoryHeadImg, bean.getImage());
        mCollapsingToolbar.setTitle(bean.getTitle());
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
    public void showLoadFailed() {
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
