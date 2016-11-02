package com.glooory.flatreader.api;

import com.glooory.flatreader.entity.VersionInfoBean;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Glooory on 2016/10/31 0031 19:40.
 */

public interface UpdateApi {
    //检查是否有新版本
    //https://raw.githubusercontent.com/Glooory/Glooory.github.io/master/releases/FlatReader/UpdateInfo.json
    @GET("UpdateInfo.json")
    Observable<VersionInfoBean> checkUpdateInfo();

    @GET("{path}")
    Call<ResponseBody> downloadFile(@Path("path") String path);
}
