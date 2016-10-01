package com.glooory.flatreader.entity;

/**
 * Created by Glooory on 2016/10/1 0001 10:20.
 * http 请求错误信息的实体类
 */

public class ErrorResponse {

    private int code;
    private String error;

    public void setCode(int code) {
        this.code = code;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getCode() {
        return code;
    }

    public String getError() {
        return error;
    }

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "code=" + code +
                ", error='" + error + '\'' +
                '}';
    }

}
