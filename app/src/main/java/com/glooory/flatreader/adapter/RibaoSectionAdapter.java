package com.glooory.flatreader.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.glooory.flatreader.R;
import com.glooory.flatreader.base.MyApplication;
import com.glooory.flatreader.entity.ribao.RibaoStoryBean;
import com.glooory.flatreader.greendao.DaoSession;
import com.glooory.flatreader.greendao.RibaoStoryBeanDao;
import com.glooory.flatreader.net.ImageLoader;
import com.glooory.flatreader.util.GreenDaoUtils;

/**
 * Created by Glooory on 2016/9/30 0030 10:49.
 */

public class RibaoSectionAdapter extends BaseSectionQuickAdapter<RibaoStoryBean> {
    private Context mContext;
    private RibaoStoryBeanDao mRibaoDao;

    public RibaoSectionAdapter(Context context) {
        super(R.layout.cardview_ribao_item, R.layout.view_section_header_ribao, null);
        this.mContext = context;
        DaoSession daoSession = ((MyApplication) context.getApplicationContext()).getDaoSession();
        mRibaoDao = daoSession.getRibaoStoryBeanDao();
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
        if (GreenDaoUtils.isEntityExists(mRibaoDao, RibaoStoryBeanDao.Properties.Id.eq(bean.getId()))) {
            ((TextView) holder.getView(R.id.tv_ribao_item_title))
                    .setTextColor(mContext.getResources().getColor(R.color.colorSecondaryText));
        } else {
            ((TextView) holder.getView(R.id.tv_ribao_item_title))
                    .setTextColor(mContext.getResources().getColor(R.color.colorPrimaryText));
        }
        holder.setText(R.id.tv_ribao_item_title, bean.getTitle())
                .addOnClickListener(R.id.cardview_ribao_item);

        ImageLoader.load(mContext, (ImageView) holder.getView(R.id.img_card_ribao_item), bean.getImages().get(0));
    }
}
