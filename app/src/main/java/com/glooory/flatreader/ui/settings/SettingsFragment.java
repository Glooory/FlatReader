package com.glooory.flatreader.ui.settings;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import com.glooory.flatreader.BuildConfig;
import com.glooory.flatreader.R;
import com.glooory.flatreader.base.MyApplication;
import com.glooory.flatreader.util.CacheUtils;

/**
 * Created by Glooory on 2016/10/25 0025 13:47.
 */

public class SettingsFragment extends PreferenceFragment {

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
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
        // TODO: 2016/10/25 0025 check if there was a new version
    }

    private void changeMode() {
        // TODO: 2016/10/25 0025 change to night mode or else
    }
}
