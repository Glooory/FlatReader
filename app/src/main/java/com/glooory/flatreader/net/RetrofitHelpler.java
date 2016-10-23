package com.glooory.flatreader.net;

import com.glooory.flatreader.api.GankApi;
import com.glooory.flatreader.api.ITHomeApi;
import com.glooory.flatreader.api.RibaoApi;
import com.glooory.flatreader.base.MyApplication;
import com.glooory.flatreader.util.NetworkUtils;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

/**
 * Created by Glooory on 2016/9/29 0029 15:02.
 */

public class RetrofitHelpler {

    private static final String CACHE_CONTROL = "Cache-Control";
    private static Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Response originalResponse = chain.proceed(chain.request());
            if (NetworkUtils.isNetworkAvaliable(MyApplication.getInstance())) {
                int maxAge = 60; //在线缓存在一分钟内可以读取
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader(CACHE_CONTROL)
                        .header(CACHE_CONTROL, "public, max-age=" + maxAge)
                        .build();
            } else {
                int maxStale = 60 * 60 * 24 * 7 * 4; //没有网络连接时缓存保存四周
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader(CACHE_CONTROL)
                        .header(CACHE_CONTROL, "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
        }
    };
    private static File mRibaoHttpCache = new File(MyApplication.getInstance().getCacheDir(), "ribaoCache");
    private static int cacheSize = 10 * 10 * 1024;
    private static Cache cache = new Cache(mRibaoHttpCache, cacheSize);
    private static OkHttpClient client = new OkHttpClient.Builder()
            .addNetworkInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
            .addInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
            .cache(cache)
            .build();
    private static RetrofitHelpler retrofitHelpler;

    public volatile RibaoApi ribaoApi;
    public volatile GankApi gankApi;
    public volatile ITHomeApi itHomeApi;

    public static RetrofitHelpler getInstance() {
        if (retrofitHelpler == null) {
            synchronized (RetrofitHelpler.class) {
                if (retrofitHelpler == null) {
                    retrofitHelpler = new RetrofitHelpler();
                }
            }
        }
        return retrofitHelpler;
    }

    public RibaoApi getRibaoService() {
        if (ribaoApi == null) {
            synchronized (RibaoApi.class) {
                if (ribaoApi == null) {
                    ribaoApi = new Retrofit.Builder()
                            .baseUrl("http://news-at.zhihu.com/")
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .client(client)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build()
                            .create(RibaoApi.class);
                }
            }
        }
        return ribaoApi;
    }

    public GankApi getGankService() {
        if (gankApi == null) {
            synchronized (GankApi.class) {
                if (gankApi == null) {
                    gankApi = new Retrofit.Builder()
                            .baseUrl("http://gank.io/api/data/")
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .client(client)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build()
                            .create(GankApi.class);
                }
            }
        }
        return gankApi;
    }

    public ITHomeApi getItHomeApi() {
        if (itHomeApi == null) {
            synchronized (ITHomeApi.class) {
                if (itHomeApi == null) {
                    itHomeApi = new Retrofit.Builder()
                            .baseUrl("http://api.ithome.com/")
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .addConverterFactory(SimpleXmlConverterFactory.create())
                            .client(client)
                            .build()
                            .create(ITHomeApi.class);
                }
            }
        }
        return itHomeApi;
    }

}
