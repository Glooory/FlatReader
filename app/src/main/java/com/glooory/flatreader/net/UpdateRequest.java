package com.glooory.flatreader.net;

import com.glooory.flatreader.api.UpdateApi;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Glooory on 2016/10/31 0031 19:43.
 */

public class UpdateRequest {
    private static UpdateApi updateApi = null;

    public static UpdateApi getUpdateApi() {
        if (updateApi == null) {
            synchronized (UpdateApi.class) {
                if (updateApi == null) {
                    updateApi = new Retrofit.Builder()
                            .baseUrl("https://raw.githubusercontent.com/Glooory/FlatReader/")
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .client(new OkHttpClient())
                            .build()
                            .create(UpdateApi.class);
                }
            }
        }
        return updateApi;
    }

}
