package com.glooory.flatreader.net;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by Glooory on 2016/10/12 0012 13:18.
 */

public class ImageLoader {

    public static void load(Context context, ImageView imageView, String url) {
        Glide.with(context).load(url).into(imageView);
    }

}
