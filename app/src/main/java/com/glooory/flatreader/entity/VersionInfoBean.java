package com.glooory.flatreader.entity;

import com.google.gson.Gson;

/**
 * Created by Glooory on 2016/10/31 0031 19:29.
 */

public class VersionInfoBean {

    /**
     * versioncode : 104
     * versionname : 1.0.4
     * downloadurl : https://github.com/Glooory/Glooory.github.io/blob/master/releases/FlatReader/flatreader_1.0.1_universal.apk?raw=true
     * filename : flatreader_1.0.4_universal.apk
     * releaseinfo : 1.加入下载测试链接
     2.添加更新
     3.优化多处用户体验
     * size : 3.96M
     */

    private int versioncode;
    private String versionname;
    private String downloadurl;
    private String filename;
    private String releaseinfo;
    private String size;

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

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getReleaseinfo() {
        return releaseinfo;
    }

    public void setReleaseinfo(String releaseinfo) {
        this.releaseinfo = releaseinfo;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
