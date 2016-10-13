package com.glooory.flatreader.ui.ithomecontent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.glooory.flatreader.R;
import com.glooory.flatreader.base.BaseActivity;
import com.glooory.flatreader.entity.ithome.ITHomeContentBean;
import com.glooory.flatreader.entity.ithome.ITHomeItemBean;
import com.glooory.flatreader.util.WebUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Glooory on 2016/10/13 0013 10:23.
 */

public class ITContentActivity extends BaseActivity implements ITContentContract.View {
    private static final String KEY_NEWS_ITEM = "key_news_item";

    @BindView(R.id.tv_it_article_title)
    TextView mTitleTv;
    @BindView(R.id.tv_it_article_author)
    TextView mAuthorTv;
    @BindView(R.id.webview_it_article)
    WebView mWebview;
    @BindView(R.id.swipe_refresh_ithome_content)
    SwipeRefreshLayout mSwipeLayout;

    private ITContentContract.Presenter mPresenter;
    private ITHomeItemBean mITHomeItem;

    public static void launch(Activity activity, ITHomeItemBean bean) {
        Intent intent = new Intent(activity, ITContentActivity.class);
        intent.putExtra(KEY_NEWS_ITEM, bean);
        activity.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itnews_content);
        ButterKnife.bind(this);
        new ITHomeContentPresenter(mContext, this);
        mITHomeItem = getIntent().getParcelableExtra(KEY_NEWS_ITEM);
        mPresenter.loadITHomeContent(mITHomeItem.getNewsid());
        initView();
    }

    private void initView() {
        mSwipeLayout.setColorSchemeColors(mContext.getResources().getColor(R.color.red_g_i), mContext.getResources().getColor(R.color.green_g_i),
                mContext.getResources().getColor(R.color.blue_g_i), mContext.getResources().getColor(R.color.yellow_g_i));
        mSwipeLayout.setOnRefreshListener(null);
        mSwipeLayout.setEnabled(false);
        mSwipeLayout.setRefreshing(true);
        mTitleTv.setText(mITHomeItem.getTitle());
        initWebViweSetting();
    }

    private void initWebViweSetting() {
        WebSettings webSettings = mWebview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setAppCachePath(getCacheDir().getAbsolutePath() + "/webviewcache");
        webSettings.setAppCacheEnabled(true);
        mWebview.setWebChromeClient(new WebChromeClient());
    }

    @Override
    public void showITHomeContent(ITHomeContentBean itHomeContentBean) {
        mAuthorTv.setText(getArticleSourceInfo(itHomeContentBean));
        if (TextUtils.isEmpty(itHomeContentBean.getDetail())) {
            mWebview.loadUrl(mITHomeItem.getUrl());
        } else {
            String data = WebUtils.buildHtmlForIt(itHomeContentBean.getDetail(), false);
            mWebview.loadDataWithBaseURL(WebUtils.BASE_URL, data, WebUtils.MIME_TYPE, WebUtils.ENCODING, mITHomeItem.getUrl());
        }
        dismissProgress();
    }

    private String getArticleSourceInfo(ITHomeContentBean itHomeContentBean) {
        StringBuilder articleInfo = new StringBuilder();
        if (itHomeContentBean.getAuthor().equals("-") ||
                TextUtils.isEmpty(itHomeContentBean.getAuthor())) {
            articleInfo.append(mITHomeItem.getPostdate())
                    .append("    ")
                    .append(itHomeContentBean.getNewssource());
        } else {
            articleInfo.append(mITHomeItem.getPostdate())
                    .append("    ")
                    .append(itHomeContentBean.getNewssource())
                    .append("(")
                    .append(itHomeContentBean.getAuthor())
                    .append(")");
        }
        return articleInfo.toString();
    }

    @Override
    public void setPresenter(ITContentContract.Presenter presenter) {
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebview.canGoBack()) {
            mWebview.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
