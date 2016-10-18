package com.glooory.flatreader.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.glooory.flatreader.R;
import com.glooory.flatreader.entity.ribao.RibaoStoryBean;
import com.glooory.flatreader.net.ImageLoader;

/**
 * Created by Glooory on 2016/9/30 0030 10:49.
 */

public class RibaoSectionAdapter extends BaseSectionQuickAdapter<RibaoStoryBean> {
    private Context mContext;

    public RibaoSectionAdapter(Context context) {
        super(R.layout.cardview_ribao_item, R.layout.view_section_header_ribao, null);
        this.mContext = context;
    }

    @Override
    protected void convertHead(BaseViewHolder holder, RibaoStoryBean bean) {
        holder.setText(R.id.tv_section_header_ribao, bean.header);
    }

    @Override
    protected void convert(BaseViewHolder holder, RibaoStoryBean bean) {
        if (bean.isMultipic()) {
            holder.getView(R.id.tv_multipic).setVisibility(View.VISIBLE);
        } else {
            holder.getView(R.id.tv_multipic).setVisibility(View.INVISIBLE);
        }
        holder.setText(R.id.tv_ribao_item_title, bean.getTitle())
                .addOnClickListener(R.id.cardview_ribao_item);

        ImageLoader.load(mContext, (ImageView) holder.getView(R.id.img_card_ribao_item), bean.getImages().get(0));
    }
}
