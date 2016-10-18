package com.glooory.flatreader.ui;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;

import com.glooory.flatreader.R;
import com.glooory.flatreader.base.BaseActivity;
import com.glooory.flatreader.listener.OnSectionChangeListener;
import com.glooory.flatreader.ui.gank.GankFragment;
import com.glooory.flatreader.ui.ithome.ITHomeFragment;
import com.glooory.flatreader.ui.ribao.RibaoFragment;
import com.jaeger.library.StatusBarUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        OnSectionChangeListener{
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.nav_view)
    NavigationView mNavView;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupWindowAnimations();
        ButterKnife.bind(this);
        initView();
        initRibaoUI();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
            case R.id.nav_share:
                break;
        }

        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void initRibaoUI() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_main, RibaoFragment.newInstance()).commit();
    }

    @Override
    public void onSectionChange(String sectionTitle) {
        getSupportActionBar().setTitle(sectionTitle);
    }
}
