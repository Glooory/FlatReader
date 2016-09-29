package com.glooory.flatreader.base;

import android.content.Context;
import android.support.v4.app.Fragment;

/**
 * Created by Glooory on 2016/9/29 0029 17:30.
 */

public class BaseFragment extends Fragment {
    protected Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }
}
