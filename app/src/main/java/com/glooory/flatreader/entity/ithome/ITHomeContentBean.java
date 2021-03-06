package com.glooory.flatreader.entity.ithome;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

/**
 * Created by Glooory on 2016/10/13 0013 10:11.
 */

@Root(name = "rss", strict = false)
public class ITHomeContentBean {

    @Path("channel/item")
    @Element(name = "newssource")
    private String newssource;
    @Path("channel/item")
    @Element(name = "newsauthor")
    private String newsauthor;
    @Path("channel/item")
    @Element(name = "detail")
    private String detail;

    public String getNewssource() {
        return newssource;
    }

    public void setNewssource(String newssource) {
        this.newssource = newssource;
    }

    public String getAuthor() {
        return newsauthor;
    }

    public void setAuthor(String author) {
        this.newsauthor = author;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

}
