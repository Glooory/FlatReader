package com.glooory.flatreader.ui.main;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;

import com.glooory.flatreader.R;
import com.glooory.flatreader.base.BaseActivity;
import com.glooory.flatreader.callback.OnSectionChangeListener;
import com.glooory.flatreader.constants.Constants;
import com.glooory.flatreader.entity.VersionInfoBean;
import com.glooory.flatreader.service.DownloadService;
import com.glooory.flatreader.ui.gank.GankFragment;
import com.glooory.flatreader.ui.ithome.ITHomeFragment;
import com.glooory.flatreader.ui.ribao.RibaoFragment;
import com.glooory.flatreader.ui.settings.SettingsActivity;
import com.glooory.flatreader.util.SPUtils;
import com.glooory.flatreader.util.ToastUtils;
import com.jaeger.library.StatusBarUtil;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionNo;
import com.yanzhenjie.permission.PermissionYes;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        OnSectionChangeListener, MainContract.View {
    public static final int EXTERNAL_REQUEST_CODE = 409;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.nav_view)
    NavigationView mNavView;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawer;

    private MainContract.Presenter mPresenter;
    private VersionInfoBean mVersionInfoBean;
    private long exitTime;

    private RationaleListener mRationaleListener = new RationaleListener() {
        @Override
        public void showRequestPermissionRationale(int requestCode, final Rationale rationale) {
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle(R.string.external_permission_tip)
                    .setMessage(R.string.external_permission_des)
                    .setPositiveButton("好的", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            rationale.resume();
                        }
                    })
                    .setNegativeButton("我拒绝", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            rationale.cancel();
                        }
                    }).show();
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupWindowAnimations();
        ButterKnife.bind(this);
        new MainPresenter(this);
        initView();
        initRibaoUI();
        mPresenter.checkUpdate();
    }

    private void setupWindowAnimations() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            Slide slide = new Slide(Gravity.RIGHT);
            slide.setDuration(200);
            getWindow().setExitTransition(slide);
        }
    }

    private void initView() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.title_ribao_latest);

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.setDrawerListener(toggle);
        toggle.syncState();

        mNavView.setNavigationItemSelectedListener(this);
        mNavView.getMenu().getItem(0).setChecked(true); //默认选中第一个选项
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setColorForDrawerLayout(this,
                (DrawerLayout) findViewById(R.id.drawer_layout),
                getResources().getColor(R.color.colorPrimaryDark),
                0);
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_ribao:
                initRibaoUI();
                break;
            case R.id.nav_gank:
                getSupportActionBar().setTitle(getString(R.string.nav_gank_title));
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_main, GankFragment.newInstance()).commit();
                break;
            case R.id.nav_ithome:
                getSupportActionBar().setTitle(getString(R.string.nav_it_title));
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_main, ITHomeFragment.newInstance()).commit();
                break;
            case R.id.nav_settings:
                SettingsActivity.launch(MainActivity.this);
                break;
        }

        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void initRibaoUI() {
        getSupportActionBar().setTitle(getString(R.string.title_ribao_latest));
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_main, RibaoFragment.newInstance()).commit();
    }

    @Override
    public void onSectionChange(String sectionTitle) {
        getSupportActionBar().setTitle(sectionTitle);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (mDrawer.isDrawerOpen(GravityCompat.START)) {
                mDrawer.closeDrawer(GravityCompat.START);
            } else {
                if (System.currentTimeMillis() - exitTime > 2000) {
                    ToastUtils.showToastShort(R.string.exit_hint);
                    exitTime = System.currentTimeMillis();
                } else {
                    finishSelf();
                }
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void showUpdateDialog(final VersionInfoBean bean) {
        this.mVersionInfoBean = bean;
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(R.string.new_version_available)
                .setMessage(String.format(getString(R.string.new_version_des), bean.getVersionname(), bean.getReleaseinfo(), bean.getSize()))
                .setCancelable(false)
                .setPositiveButton(R.string.download_new_version, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AndPermission.with(MainActivity.this)
                                .requestCode(EXTERNAL_REQUEST_CODE)
                                .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                .rationale(mRationaleListener)
                                .send();
                    }
                })
                .setNegativeButton(R.string.cancle, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences.Editor editor = getSharedPreferences(SPUtils.SP_NAME, Context.MODE_PRIVATE).edit();
                        editor.putBoolean(Constants.DONNOT_REMIND_ANYMORE, true);
                        editor.putInt(Constants.NEWEST_VERSION_CODE, bean.getVersioncode());
                        editor.apply();
                    }
                });
        builder.create().show();
    }

    private void actionDownload(VersionInfoBean bean) {
        DownloadService.launch(MainActivity.this,
                bean.getFilename());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        AndPermission.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    /**
     * 请求获取 External 权限成功的回调
     */
    @PermissionYes(EXTERNAL_REQUEST_CODE)
    private void getWriteExternalYes() {
        actionDownload(mVersionInfoBean);
    }

    /**
     * 请求读写 External 权限失败的回调
     */
    @PermissionNo(EXTERNAL_REQUEST_CODE)
    private void getWriteExternalNo() {
        ToastUtils.showToastShort(R.string.external_permission_failed);
    }


    @Override
    public void setPresenter(MainContract.Presenter presenter) {
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
}
