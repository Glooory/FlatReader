package com.glooory.flatreader.entity.ribao;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by Glooory on 2016/9/28 0028 14:25.
 * 知乎日报的日报Story实体类
 */

public class RibaoStoryBean {

    /**
     * image : http://pic4.zhimg.com/d96abdb5bb61859332aaf3e593f65eff.jpg
     * type : 0
     * id : 8836412
     * ga_prefix : 092808
     * title : 给你卡拿去刷，因为银行用信用卡赚钱的方式太多了
     */

    private int type;
    private String id;
    private String ga_prefix;
    private String title;
    private List<String> images;

    public static RibaoStoryBean objectFromData(String str) {

        return new Gson().fromJson(str, RibaoStoryBean.class);
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGa_prefix() {
        return ga_prefix;
    }

    public void setGa_prefix(String ga_prefix) {
        this.ga_prefix = ga_prefix;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

}
