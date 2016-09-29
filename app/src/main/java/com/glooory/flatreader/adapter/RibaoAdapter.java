package com.glooory.flatreader.adapter;

import android.content.Context;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.glooory.flatreader.R;
import com.glooory.flatreader.entity.ribao.RibaoStoryBean;
import com.glooory.flatreader.net.FrescoLoader;
import com.orhanobut.logger.Logger;

/**
 * Created by Glooory on 2016/9/29 0029 18:07.
 */

public class RibaoAdapter extends BaseQuickAdapter<RibaoStoryBean> {
    private Context mContext;

    public RibaoAdapter(Context context) {
        super(R.layout.cardview_ribao_item, null);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder holder, RibaoStoryBean bean) {
        holder.setText(R.id.tv_ribao_item_title, bean.getTitle());

        Logger.d(bean.getImages().get(0));
        new FrescoLoader.Builder(mContext,
                (SimpleDraweeView) holder.getView(R.id.img_card_ribao_item),
                bean.getImages().get(0))
                .build();
    }
}
