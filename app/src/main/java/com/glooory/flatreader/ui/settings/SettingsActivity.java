package com.glooory.flatreader.ui.settings;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;

import com.glooory.flatreader.R;
import com.glooory.flatreader.base.BaseActivity;
import com.glooory.flatreader.callback.PermissionCallback;
import com.glooory.flatreader.util.ToastUtils;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Glooory on 2016/10/25 0025 13:44.
 */

public class SettingsActivity extends BaseActivity implements PermissionCallback {
    public static final int EXTERNAL_REQUEST_CODE = 409;

    @BindView(R.id.toolbar_settings)
    Toolbar mToolbar;

    private SettingsFragment mFragment;
    private RationaleListener mRationaleListener = new RationaleListener() {
        @Override
        public void showRequestPermissionRationale(int requestCode, final Rationale rationale) {
            new AlertDialog.Builder(SettingsActivity.this)
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
    private PermissionListener mPermissionListener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode) {
            if (requestCode == EXTERNAL_REQUEST_CODE) {
                mFragment.actionDownload();
            }
        }

        @Override
        public void onFailed(int requestCode) {
            ToastUtils.showToastLong(R.string.external_permission_failed);
        }
    };

    public static void launch(Activity activity) {
        Intent intent = new Intent(activity, SettingsActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        initView();
        mFragment = SettingsFragment.newInstance();
        getFragmentManager().beginTransaction().replace(R.id.container_settings, mFragment).commit();
    }

    private void initView() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.action_settings);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        AndPermission.onRequestPermissionsResult(SettingsActivity.this, requestCode, permissions, grantResults, mPermissionListener);
    }

    @Override
    public void requestPermission() {
        AndPermission.with(SettingsActivity.this)
                .requestCode(EXTERNAL_REQUEST_CODE)
                .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .rationale(mRationaleListener)
                .send();
    }
}
