package com.glooory.flatreader.util;

import com.glooory.flatreader.entity.ErrorResponse;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.net.UnknownHostException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.adapter.rxjava.HttpException;

/**
 * Created by Glooory on 2016/10/1 0001 9:58.
 */

public class ErrorInfoUtils {

    public static String parseHttpErrorInfo(Throwable throwable) {
        String errorMsg = throwable.getMessage();

        if (throwable instanceof HttpException) {
            //如果是Retrofit 的 http 错误，则转换类型， 获取信息
            HttpException exception = (HttpException) throwable;
            ResponseBody responseBody = exception.response().errorBody();
            MediaType type = responseBody.contentType();

            //如果是application/json 类型的数据，则解析返回内容
            if (type.type().equals("application") && type.subtype().equals("json")) {
                try {
                    ErrorResponse response = new Gson().fromJson(responseBody.toString(), ErrorResponse.class);
                    errorMsg = "错误代码：" + response.getCode();
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }
            }
        } else {
            if (throwable instanceof UnknownHostException) {
                errorMsg = "无法连接到服务器";
            }
        }
        return errorMsg;
    }

}
