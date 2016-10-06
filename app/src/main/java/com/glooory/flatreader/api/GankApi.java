package com.glooory.flatreader.api;

import com.glooory.flatreader.entity.gank.GankListBean;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Glooory on 2016/10/6 0006 12:15.
 */

public interface GankApi {

    //http://gank.io/api/data/数据类型/请求个数/第几页
    //http://gank.io/api/data/Android/10/1
    @GET("{category}/{size}/{page}")
    Observable<GankListBean> httpForGankList(@Path("category") String category,
                                             @Path("size") int size,
                                             @Path("page") int page);

}
