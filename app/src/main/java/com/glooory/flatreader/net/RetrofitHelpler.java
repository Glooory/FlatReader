package com.glooory.flatreader.net;

import com.glooory.flatreader.api.RibaoApi;
import com.glooory.flatreader.base.MyApplication;
import com.glooory.flatreader.util.NeworkUtil;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Glooory on 2016/9/29 0029 15:02.
 */

public class RetrofitHelpler {

    private static final Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Response originalResponse = chain.proceed(chain.request());
            if (NeworkUtil.isNetworkAvaliable(MyApplication.getInstance())) {
                int maxAge = 60;  //在线缓存在一分钟内可以读取
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();
            } else {
                int maxStale = 60 * 60 * 24 * 28;  //离线时缓存保存四周
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
        }
    };

    public static volatile RetrofitHelpler retrofitHelpler;
    private static File httpCacheDirectory = new File(MyApplication.getInstance().getCacheDir(), "ribaocache");
    private static int cacheSize = 20 * 1024 * 1024;
    private static Cache cache = new Cache(httpCacheDirectory, cacheSize);
    private static OkHttpClient client = new OkHttpClient.Builder()
            .addNetworkInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
            .addInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
            .cache(cache)
            .build();

    public volatile RibaoApi ribaoApi;

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

}
