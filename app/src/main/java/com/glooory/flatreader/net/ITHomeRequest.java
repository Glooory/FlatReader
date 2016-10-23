package com.glooory.flatreader.net;

import com.glooory.flatreader.api.ITHomeApi;
import com.glooory.flatreader.base.MyApplication;
import com.glooory.flatreader.util.NetworkUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

/**
 * Created by Glooory on 2016/10/22 0022 17:48.
 */

public class ITHomeRequest {
    private static final String CACHE_CONTROL = "Cache-Control";
    private static final Interceptor REWRITE_CACHE_CONTROL_INTERCEPTER = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            CacheControl.Builder cacheBuilder = new CacheControl.Builder();
            cacheBuilder.maxAge(0, TimeUnit.SECONDS);
            cacheBuilder.maxStale(365,TimeUnit.DAYS);
            CacheControl cacheControl = cacheBuilder.build();
            Request request = chain.request();
            if(!NetworkUtils.isNetworkAvaliable(MyApplication.getInstance())){
                request = request.newBuilder()
                        .cacheControl(cacheControl)
                        .build();
            }
            Response originalResponse = chain.proceed(request);
            if (NetworkUtils.isNetworkAvaliable(MyApplication.getInstance())) {
                int maxAge = 0; // read from cache
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader(CACHE_CONTROL)
                        .header("Cache-Control", "public ,max-age=" + maxAge)
                        .build();
            } else {
                int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader(CACHE_CONTROL)
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
        }
    };
    private static File httpCacheDir = new File(MyApplication.getInstance().getCacheDir(), "itHomeCache");
    private static int cacheSize = 20 * 1024 * 1024;
    private static Cache cache = new Cache(httpCacheDir, cacheSize);

    private static OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTER)
            .cache(cache)
            .build();

    private static ITHomeApi itHomeApi = null;

    public static ITHomeApi getITHomeApi() {
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

    private ITHomeRequest() {
    }
}
