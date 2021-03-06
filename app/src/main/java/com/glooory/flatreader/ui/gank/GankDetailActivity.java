package com.glooory.flatreader.ui.gank;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.glooory.flatreader.R;
import com.glooory.flatreader.base.BaseActivity;
import com.glooory.flatreader.util.SnackbarUtils;
import com.glooory.flatreader.util.StringUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;

/**
 * Created by Glooory on 2016/10/6 0006 16:53.
 */

public class GankDetailActivity extends BaseActivity {
    public static final String KEY_GANK_TITLE = "key_gank_title";
    public static final String KEY_GANK_URL = "key_gank_url";
    @BindView(R.id.coordinator_gank_detail)
    CoordinatorLayout mCoordinator;
    @BindView(R.id.progressbar_gank_detail)
    MaterialProgressBar mProgressBar;
    private String mGankTitle;
    private String mGankUrl;

    @BindView(R.id.toolbar_gank_detail)
    Toolbar mToolbar;
    @BindView(R.id.webview_gank_detail)
    WebView mWebview;

    public static void launch(Activity activity, String gankTitle, String gankUrl) {
        Intent intent = new Intent(activity, GankDetailActivity.class);
        intent.putExtra(KEY_GANK_TITLE, gankTitle);
        intent.putExtra(KEY_GANK_URL, gankUrl);
        activity.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gank_detail);
        ButterKnife.bind(this);
        mGankTitle = getIntent().getStringExtra(KEY_GANK_TITLE);
        mGankUrl = getIntent().getStringExtra(KEY_GANK_URL);
        initView();
        mWebview.loadUrl(mGankUrl);
    }

    private void initView() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(mGankTitle);

        WebSettings settings = mWebview.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setAppCacheEnabled(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setSupportZoom(true);
        mWebview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress >= 100) {
                    mProgressBar.setVisibility(View.INVISIBLE);
                } else {
                    mProgressBar.setVisibility(View.VISIBLE);
                }
                mProgressBar.setProgress(newProgress);
            }
        });
        mWebview.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                //handler.cancel(); //默认的处理方式，WebView变成空白页
                handler.proceed(); //接受证书
                //handleMessage(Message msg); //其他处理
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_gank_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (mWebview.canGoBack()) {
                    mWebview.goBack();
                } else {
                    finishSelf();
                }
                return true;
            case R.id.menu_browser_action:
                Uri uri = Uri.parse(mGankUrl);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                } else {
                    SnackbarUtils.makeLong(mCoordinator, "没有找到浏览器").error();
                }
                break;
            case R.id.menu_clip_action:
                StringUtils.copyToClipBoardSnackbar(mContext, mCoordinator, mGankUrl, "成功复制到粘贴板");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (mWebview.canGoBack()) {
                        mWebview.goBack();
                    } else {
                        finishSelf();
                    }
                    return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
