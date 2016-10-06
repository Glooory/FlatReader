package com.glooory.flatreader.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.glooory.flatreader.R;
import com.glooory.flatreader.entity.gank.GankBean;
import com.glooory.flatreader.util.DateUtils;

/**
 * Created by Glooory on 2016/10/6 0006 12:48.
 */

public class GankAdapter extends BaseQuickAdapter<GankBean> {
    private Context mContext;

    public GankAdapter(Context context) {
        super(R.layout.cardview_gank_item, null);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder holder, GankBean bean) {
        holder.setText(R.id.tv_card_gankitem_title, bean.getDesc())
                .setText(R.id.tv_card_gankitem_pubtime, DateUtils.dateToMd("yyyy-MM-dd", "MM月dd日", bean.getPublishedAt().substring(0, 10)))
                .setText(R.id.tv_card_gankitem_author, bean.getWho())
                .addOnClickListener(R.id.card_gank);
    }
}
