package com.glooory.flatreader.api;


import com.glooory.flatreader.entity.ithome.ITHomeContentBean;
import com.glooory.flatreader.entity.ithome.ITResponse;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Glooory on 2016/10/12 0012 12:42.
 */

public interface ITHomeApi {

    //请求IT之家最新的数据
    //http://api.ithome.com/xml/newslist/news.xml
    @GET("xml/newslist/news.xml")
    Observable<ITResponse> getITHomeNewest();

    //请求更多IT之家的新闻数据
    //http://api.ithome.com/xml/newslist/news_05bffc036ce4305d.xml
    @GET("xml/newslist/news_{lastItemId}.xml")
    Observable<ITResponse> getITHomeMore(@Path("lastItemId") String lastItemId);

    //根据 newsId 请求具体的新闻内容
    //http://api.ithome.com/xml/newscontent/264/048.xml
    @GET("xml/newscontent/{newsId}.xml")
    Observable<ITHomeContentBean> getNewsContent(@Path("newsId") String newsId);


}
