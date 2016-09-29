package com.glooory.flatreader.api;

import android.database.Observable;

import com.glooory.flatreader.entity.ribao.RibaoIStoriesBean;
import com.glooory.flatreader.entity.ribao.RibaoStoryContentBean;

import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Glooory on 2016/9/29 0029 14:52.
 */

public interface RibaoApi {

    //获取当天的最新日报内容
    //http://news-at.zhihu.com/api/4/news/latest
    @GET("api/4/news/latest")
    Observable<RibaoIStoriesBean> getLatest();

    //获取指定日期前一天的日报内容
    //http://news-at.zhihu.com/api/4/news/before/20160929
    @GET("api/4/news/before/{date}")
    Observable<RibaoIStoriesBean> getByDate(@Path("date") String date);

    //根据story的id获取该story的详细内容
    //http://news-at.zhihu.com/api/4/news/8839540
    @GET("api/4/news/{storyId}")
    Observable<RibaoStoryContentBean> getStoryContent(@Path("storyId") String storyId);
}
