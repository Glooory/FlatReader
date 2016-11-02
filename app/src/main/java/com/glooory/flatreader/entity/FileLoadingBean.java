package com.glooory.flatreader.entity;

/**
 * Created by Glooory on 2016/11/1 0001 13:06.
 * 保存文件的总大小和下载进度的实体类
 */

public class FileLoadingBean {
    /**
     * 文件的总大小
     */
    private long total;

    /**
     * 当前已经下载了的大小
     */
    private long progress;

    public FileLoadingBean(long total, long progress) {
        this.total = total;
        this.progress = progress;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getProgress() {
        return progress;
    }

    public void setProgress(long progress) {
        this.progress = progress;
    }
}
