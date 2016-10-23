package com.glooory.flatreader.base;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.glooory.flatreader.R;
import com.glooory.flatreader.util.NetworkUtils;
import com.glooory.flatreader.util.ToastUtils;
import com.jaeger.library.StatusBarUtil;

/**
 * Created by Glooory on 2016/10/1 0001 16:49.
 */

public class BaseActivity extends AppCompatActivity {
    protected Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = this;
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        setStatusBar();
    }

    protected void setStatusBar() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimaryDark), 0);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finishSelf();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void finishSelf() {
        if (Build.VERSION.SDK_INT >= 21) {
            finishAfterTransition();
        } else {
            finish();
        }
    }

    public void checkNetwork() {
        if (!NetworkUtils.isNetworkAvaliable(MyApplication.getInstance())) {
            ToastUtils.showToastLong(R.string.network_unavailable);
        }
    }
}
