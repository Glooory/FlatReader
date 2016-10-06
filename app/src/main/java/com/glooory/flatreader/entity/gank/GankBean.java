package com.glooory.flatreader.entity.gank;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by Glooory on 2016/10/6 0006 12:21.
 */

public class GankBean {


    /**
     * _id : 57edb5f6421aa95de3b8ab26
     * createdAt : 2016-09-30T08:46:46.119Z
     * desc : 很实用的一个库，帮你做 ListView / RecyclerView 等组件的状态维护，比如：无数据 / 网络出现问题 / 数据获取成功等。
     * images : ["http://img.gank.io/bc62534e-99f0-48bb-9914-51955479b93b"]
     * publishedAt : 2016-09-30T11:46:31.941Z
     * source : chrome
     * type : Android
     * url : https://github.com/WassimBenltaief/FlowLayout
     * used : true
     * who : 代码家
     */

    private String _id;
    private String createdAt;
    private String desc;
    private String publishedAt;
    private String source;
    private String type;
    private String url;
    private boolean used;
    private String who;
    private List<String> images;

    public static GankBean objectFromData(String str) {

        return new Gson().fromJson(str, GankBean.class);
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
