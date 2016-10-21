package com.glooory.flatreader.base;

import android.app.Application;

import com.glooory.flatreader.greendao.DaoMaster;
import com.glooory.flatreader.greendao.DaoSession;
import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.LeakCanary;

import org.greenrobot.greendao.database.Database;

/**
 * Created by Glooory on 2016/9/28 0028 13:25.
 */

public class MyApplication extends Application {
    public static MyApplication sInstance;
    private DaoSession mDaoSession;

    public static MyApplication getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        Logger.init();

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        // Normal app init code..

        //init greendao
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "IsRead.db");
        Database db = helper.getWritableDb();
        mDaoSession = new DaoMaster(db).newSession();
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }
}
