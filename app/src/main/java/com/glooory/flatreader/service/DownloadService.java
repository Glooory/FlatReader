package com.glooory.flatreader.service;

import android.app.Activity;
import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;

import com.glooory.flatreader.R;
import com.glooory.flatreader.api.UpdateApi;
import com.glooory.flatreader.callback.FileCallback;
import com.glooory.flatreader.net.FileResponseBody;
import com.glooory.flatreader.util.ToastUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Created by Glooory on 2016/11/1 0001 13:46.
 */

public class DownloadService extends IntentService {
    private static final String DOWNLOAD_URL = "download_url";
    private static final String TARGET_FILE_NAME = "target_name";
    /**
     * 目标文件存储的文件夹路径
     */
    private String destFileDir = Environment.getExternalStorageDirectory().getAbsolutePath()
            + File.separator + "Download";
    private String destFileName;

    private int preProgress = 0;
    private int NOTIFY_ID = 1203;
    private NotificationCompat.Builder builder;
    private NotificationManager notificationManager;
    private Retrofit.Builder retrofit;

    public static void launch(Activity activity, String targetFileName) {
        Intent intent = new Intent(activity, DownloadService.class);
        intent.putExtra(TARGET_FILE_NAME, targetFileName);
        activity.startService(intent);
    }

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     */
    public DownloadService() {
        super("DownloadService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        destFileName = intent.getStringExtra(TARGET_FILE_NAME);
        loadFile();
    }

    /**
     * 下载文件
     */
    private void loadFile() {
        initNotification();
        if (retrofit == null) {
            retrofit = new Retrofit.Builder();
        }
        retrofit.baseUrl("https://raw.githubusercontent.com/Glooory/Glooory.github.io/master/releases/FlatReader/")
                .client(initOkHttpClient())
                .build()
                .create(UpdateApi.class)
                .downloadFile(destFileName)
                .enqueue(new FileCallback(destFileDir, destFileName) {
                    @Override
                    public void onSuccess(File file) {
                        cancelNotification();
                        installApk(file);
                    }

                    @Override
                    public void onLoading(long total, long progress) {
                        updateNotification(progress * 100 / total);
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        cancelNotification();
                        t.printStackTrace();
                        ToastUtils.showToastShort("下载出错");
                    }
                });
    }

    /**
     * 安装 apk 文件
     * @param file
     */
    private void installApk(File file) {
        Uri uri = Uri.fromFile(file);
        Intent install = new Intent(Intent.ACTION_VIEW);
        install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        install.setDataAndType(uri, "application/vnd.android.package-archive");
        startActivity(install);
    }

    /**
     * 初始化自定义配置的 OkHttpClient
     * @return
     */
    private OkHttpClient initOkHttpClient() {
        OkHttpClient.Builder clienBuilder = new OkHttpClient.Builder();
        clienBuilder.connectTimeout(60, TimeUnit.SECONDS);
        clienBuilder.readTimeout(60, TimeUnit.SECONDS);
        clienBuilder.networkInterceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Response originalResponse = chain.proceed(chain.request());
                return originalResponse
                        .newBuilder()
                        .body(new FileResponseBody(originalResponse)) //传入自定义的ResponseBody
                        .build();
            }
        });
        return clienBuilder.build();
    }

    /**
     * 初始化通知
     */
    public void initNotification() {
        builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText("0%")
                .setContentTitle("FlatReader 更新")
                .setProgress(100, 0, false);
        notificationManager = (NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFY_ID, builder.build());
    }

    /**
     * 更新通知
     * @param progress
     */
    public void updateNotification(long progress) {
        int currentProgress = (int) progress;
        if (preProgress < currentProgress) {
            builder.setContentText(progress + "%");
            builder.setProgress(100, currentProgress, false);
            notificationManager.notify(NOTIFY_ID, builder.build());
        }
        preProgress = currentProgress;
    }

    /**
     * 取消通知
     */
    public void cancelNotification() {
        notificationManager.cancel(NOTIFY_ID);
    }
}
