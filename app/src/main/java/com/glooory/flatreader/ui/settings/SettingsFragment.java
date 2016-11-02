package com.glooory.flatreader.ui.settings;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v7.app.AlertDialog;

import com.glooory.flatreader.BuildConfig;
import com.glooory.flatreader.R;
import com.glooory.flatreader.base.MyApplication;
import com.glooory.flatreader.constants.Constants;
import com.glooory.flatreader.entity.VersionInfoBean;
import com.glooory.flatreader.service.DownloadService;
import com.glooory.flatreader.util.CacheUtils;
import com.glooory.flatreader.util.SPUtils;

/**
 * Created by Glooory on 2016/10/25 0025 13:47.
 */

public class SettingsFragment extends PreferenceFragment implements SettingsContact.View {
    private SettingsContact.Presenter mPresenter;

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        new SettingsPresenter(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        findPreference(getString(R.string.key_pref_night_mode))
//                .setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
//                    @Override
//                    public boolean onPreferenceClick(Preference preference) {
//                        changeMode();
//                        return true;
//                    }
//                });
        findPreference(getString(R.string.key_pref_cache))
                .setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                    @Override
                    public boolean onPreferenceClick(Preference preference) {
                        CacheUtils.deleteDir(MyApplication.getInstance().getCacheDir());
                        showCacheSize(findPreference(getString(R.string.key_pref_cache)));
                        return true;
                    }
                });
        findPreference(getString(R.string.key_pref_suggestion))
                .setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                    @Override
                    public boolean onPreferenceClick(Preference preference) {
                        try {
                            Intent intent = new Intent(Intent.ACTION_SENDTO,
                                    Uri.fromParts("mailto", "glooorypu@gmail.com", null));
                            startActivity(Intent.createChooser(intent, "请选择邮件客户端"));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return true;
                    }
                });
        findPreference(getString(R.string.key_pref_upgrade))
                .setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                    @Override
                    public boolean onPreferenceClick(Preference preference) {
                        checkUpgradeInfo();
                        return true;
                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        showCacheSize(findPreference(getString(R.string.key_pref_cache)));
        showVersionInfo();
    }

    private void showCacheSize(Preference preference) {
        preference.setSummary(
                String.format(getString(R.string.cache_size), CacheUtils.getCacheSize(MyApplication.getInstance().getCacheDir())));

    }

    private void showVersionInfo() {
        findPreference(getString(R.string.key_pref_upgrade))
                .setSummary(String.format(getString(R.string.version_format), BuildConfig.VERSION_NAME));
    }

    private void checkUpgradeInfo() {
        mPresenter.checkUpdate();
    }

    private void changeMode() {
        // TODO: 2016/10/25 0025 change to night mode or else
    }

    @Override
    public void ShowUpdateDialog(final VersionInfoBean bean) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.new_version_available)
                .setMessage(String.format(getString(R.string.new_version_des), bean.getVersionname(), bean.getReleaseinfo(), bean.getSize()))
                .setCancelable(false)
                .setPositiveButton(R.string.download_new_version, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        actionDownload(bean);
                    }
                })
                .setNegativeButton(R.string.cancle, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences.Editor editor = MyApplication.getInstance().getSharedPreferences(SPUtils.SP_NAME, Context.MODE_PRIVATE).edit();
                        editor.putBoolean(Constants.DONNOT_REMIND_ANYMORE, true);
                        editor.putInt(Constants.NEWEST_VERSION_CODE, bean.getVersioncode());
                        editor.apply();
                    }
                });
        builder.create().show();
    }

    /**
     * 开启服务进行下载
     * @param bean
     */
    private void actionDownload(VersionInfoBean bean) {
        DownloadService.launch(getActivity(),
                bean.getFilename());
    }

    @Override
    public void setPresenter(SettingsContact.Presenter presenter) {
        mPresenter = presenter;
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
