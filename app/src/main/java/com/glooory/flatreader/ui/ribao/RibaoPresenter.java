package com.glooory.flatreader.ui.ribao;

/**
 * Created by Glooory on 2016/9/29 0029 17:39.
 */

public class RibaoPresenter implements RibaoContract.Presenter {
    private final RibaoContract.View mView;

    public RibaoPresenter(RibaoContract.View view) {
        this.mView = view;
        this.mView.setPresenter(this);
    }



    @Override
    public void detachView() {

    }
}
