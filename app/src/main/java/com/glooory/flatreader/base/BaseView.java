package com.glooory.flatreader.base;

/**
 * Created by Glooory on 2016/9/28 0028 13:43.
 */

public interface BaseView<T> {

    void setPresenter(T presenter);

    void showProgressDialog();

    void hideProgressDialog();

    void showError(String errorMessage);

}
