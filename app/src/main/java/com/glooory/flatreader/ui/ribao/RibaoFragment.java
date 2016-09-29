package com.glooory.flatreader.ui.ribao;

import com.glooory.flatreader.base.BaseFragment;

/**
 * Created by Glooory on 2016/9/29 0029 17:38.
 */

public class RibaoFragment extends BaseFragment implements RibaoContract.View {
    private RibaoContract.Presenter mPresenter;

    @Override
    public void setPresenter(RibaoContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void showProgressDialog() {

    }

    @Override
    public void hideProgressDialog() {

    }

    @Override
    public void showError(String errorMessage) {

    }
}
