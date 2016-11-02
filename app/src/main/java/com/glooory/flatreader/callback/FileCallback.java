package com.glooory.flatreader.callback;

import com.glooory.flatreader.entity.FileLoadingBean;
import com.glooory.flatreader.rx.RxBus;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Glooory on 2016/11/1 0001 13:22.
 */

public abstract class FileCallback implements Callback<ResponseBody> {
    private CompositeSubscription mCompositeSubscription = new CompositeSubscription();
    /**
     * 目标文件存储的路径
     */
    private String destFileDir;
    /**
     * 目标文件要存储成的文件名
     */
    private String destFileName;

    public FileCallback(String destFileDir, String destFileName) {
        this.destFileDir = destFileDir;
        this.destFileName = destFileName;
        subscribeLoadProgress(); //订阅下载进度
    }

    /**
     * 成功后的回调
     */
    public abstract void onSuccess(File file);

    /**
     * 下载过程进度的回调
     */
    public abstract void onLoading(long total, long progress);

    /**
     * 请求成功后保存文件
     */
    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        try {
            saveFile(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过 IO 流写入文件
     * @param response
     * @return
     */
    private File saveFile(Response<ResponseBody> response) throws IOException {
        InputStream inputStream = null;
        FileOutputStream fileOutputStream = null;
        byte[] buf = new byte[2048];
        int len;
        try {
            File dir = new File(destFileDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            inputStream = response.body().byteStream();
            File file = new File(dir, destFileName);
            fileOutputStream = new FileOutputStream(file);
            while ((len = inputStream.read(buf)) != -1) {
                fileOutputStream.write(buf, 0, len);
            }
            onSuccess(file);
            unSubscribe(); //取消订阅
            return file;
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
        }
    }

    /**
     * 订阅文件的下载进度
     */
    private void subscribeLoadProgress() {
        Subscription s = RxBus.getDefault()
                .toObservable(FileLoadingBean.class)
                .onBackpressureBuffer()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<FileLoadingBean>() {
                    @Override
                    public void call(FileLoadingBean fileLoadingBean) {
                        onLoading(fileLoadingBean.getTotal(), fileLoadingBean.getProgress());
                    }
                });
        mCompositeSubscription.add(s);
    }

    /**
     * 取消订阅
     */
    private void unSubscribe() {
        if (mCompositeSubscription != null && !mCompositeSubscription.isUnsubscribed()) {
            mCompositeSubscription.unsubscribe();
        }
    }
}
