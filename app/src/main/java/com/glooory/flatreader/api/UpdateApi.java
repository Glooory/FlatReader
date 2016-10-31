package com.glooory.flatreader.api;

import com.glooory.flatreader.entity.VersionInfoBean;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by Glooory on 2016/10/31 0031 19:40.
 */

public interface UpdateApi {
    //检查是否有新版本
    //https://github.com/Glooory/FlatReader/tree/master/release/updateinfo.json
    @GET("tree/master/release/updateinfo.json")
    Observable<VersionInfoBean> checkUpdateInfo();
}
