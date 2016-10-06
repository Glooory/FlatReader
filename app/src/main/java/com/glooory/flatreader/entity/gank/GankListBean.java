package com.glooory.flatreader.entity.gank;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by Glooory on 2016/10/6 0006 12:17.
 */

public class GankListBean {


    /**
     * error : false
     * results : []
     */

    private boolean error;
    private List<GankBean> results;

    public static GankListBean objectFromData(String str) {

        return new Gson().fromJson(str, GankListBean.class);
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<GankBean> getResults() {
        return results;
    }

    public void setResults(List<GankBean> results) {
        this.results = results;
    }

}
