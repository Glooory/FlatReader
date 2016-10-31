package com.glooory.flatreader.entity;

import com.google.gson.Gson;

/**
 * Created by Glooory on 2016/10/31 0031 19:29.
 */

public class VersionInfoBean {

    /**
     * versioncode : 103
     * versionname : 1.0.3
     * downloadurl : www.github.com/xxxx
     * releaseinfo : add new content module
     */

    private int versioncode;
    private String versionname;
    private String downloadurl;
    private String releaseinfo;

    public static VersionInfoBean objectFromData(String str) {

        return new Gson().fromJson(str, VersionInfoBean.class);
    }

    public int getVersioncode() {
        return versioncode;
    }

    public void setVersioncode(int versioncode) {
        this.versioncode = versioncode;
    }

    public String getVersionname() {
        return versionname;
    }

    public void setVersionname(String versionname) {
        this.versionname = versionname;
    }

    public String getDownloadurl() {
        return downloadurl;
    }

    public void setDownloadurl(String downloadurl) {
        this.downloadurl = downloadurl;
    }

    public String getReleaseinfo() {
        return releaseinfo;
    }

    public void setReleaseinfo(String releaseinfo) {
        this.releaseinfo = releaseinfo;
    }
}
