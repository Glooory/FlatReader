package com.glooory.flatreader.entity.ithome;

import android.os.Parcel;
import android.os.Parcelable;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by Glooory on 2016/10/12 0012 12:25.
 *
 */

@Root(name = "item")
public class ITHomeItemBean implements Parcelable {

    @Attribute(name = "t", required = false)
    private String t;
    @Element(name = "newsid")
    private String newsid;
    @Element(name = "title")
    private String title;
    @Element(name = "c", required = false)
    private String c;
    @Element(required = false, name = "v")
    private String v;
    @Element(name = "url")
    private String url;
    @Element(name = "postdate")
    private String postdate;
    @Element(name = "image")
    private String image;
    @Element(required = false, name = "description")//处理可能为空的情况
    private String description;
    @Element(required = false, name = "hitcount")
    private int hitcount;
    @Element(required = false, name = "commentcount")
    private int commentcount;
    @Element(required = false, name = "forbidcomment")
    private String forbidcomment;
    @Element(required = false, name = "tags")
    private String tags;
    @Element(required = false, name = "z")
    private String z;
    @Element(required = false, name = "cid")
    private int cid;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.t);
        dest.writeString(this.newsid);
        dest.writeString(this.title);
        dest.writeString(this.c);
        dest.writeString(this.v);
        dest.writeString(this.url);
        dest.writeString(this.postdate);
        dest.writeString(this.image);
        dest.writeString(this.description);
        dest.writeInt(this.hitcount);
        dest.writeInt(this.commentcount);
        dest.writeString(this.forbidcomment);
        dest.writeString(this.tags);
        dest.writeString(this.z);
        dest.writeInt(this.cid);
    }

    public ITHomeItemBean() {
    }

    protected ITHomeItemBean(Parcel in) {
        this.t = in.readString();
        this.newsid = in.readString();
        this.title = in.readString();
        this.c = in.readString();
        this.v = in.readString();
        this.url = in.readString();
        this.postdate = in.readString();
        this.image = in.readString();
        this.description = in.readString();
        this.hitcount = in.readInt();
        this.commentcount = in.readInt();
        this.forbidcomment = in.readString();
        this.tags = in.readString();
        this.z = in.readString();
        this.cid = in.readInt();
    }

    public static final Parcelable.Creator<ITHomeItemBean> CREATOR = new Parcelable.Creator<ITHomeItemBean>() {
        @Override
        public ITHomeItemBean createFromParcel(Parcel source) {
            return new ITHomeItemBean(source);
        }

        @Override
        public ITHomeItemBean[] newArray(int size) {
            return new ITHomeItemBean[size];
        }
    };

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getZ() {
        return z;
    }

    public void setZ(String z) {
        this.z = z;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getForbidcomment() {
        return forbidcomment;
    }

    public void setForbidcomment(String forbidcomment) {
        this.forbidcomment = forbidcomment;
    }

    public int getCommentcount() {
        return commentcount;
    }

    public void setCommentcount(int commentcount) {
        this.commentcount = commentcount;
    }

    public int getHitcount() {
        return hitcount;
    }

    public void setHitcount(int hitcount) {
        this.hitcount = hitcount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPostdate() {
        return postdate;
    }

    public void setPostdate(String postdate) {
        this.postdate = postdate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getV() {
        return v;
    }

    public void setV(String v) {
        this.v = v;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNewsid() {
        return newsid;
    }

    public void setNewsid(String newsid) {
        this.newsid = newsid;
    }

    public String getT() {
        return t;
    }

    public void setT(String t) {
        this.t = t;
    }
}
