package com.glooory.flatreader.adapter;

import android.content.Context;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.glooory.flatreader.R;
import com.glooory.flatreader.base.MyApplication;
import com.glooory.flatreader.entity.gank.GankBean;
import com.glooory.flatreader.greendao.DaoSession;
import com.glooory.flatreader.greendao.GankBeanDao;
import com.glooory.flatreader.util.DateUtils;
import com.glooory.flatreader.util.GreenDaoUtils;

/**
 * Created by Glooory on 2016/10/6 0006 12:48.
 */

public class GankAdapter extends BaseQuickAdapter<GankBean> {
    private Context mContext;
    private GankBeanDao mGankDao;

    public GankAdapter(Context context) {
        super(R.layout.cardview_gank_item, null);
        this.mContext = context;
        DaoSession daoSession = ((MyApplication) context.getApplicationContext()).getDaoSession();
        mGankDao = daoSession.getGankBeanDao();
    }

    @Override
    protected void convert(BaseViewHolder holder, GankBean bean) {
        if (GreenDaoUtils.isEntityExists(mGankDao, GankBeanDao.Properties._id.eq(bean.get_id()))) {
            ((TextView) holder.getView(R.id.tv_card_gankitem_title))
                    .setTextColor(mContext.getResources().getColor(R.color.colorSecondaryText));
        } else {
            ((TextView) holder.getView(R.id.tv_card_gankitem_title))
                    .setTextColor(mContext.getResources().getColor(R.color.colorPrimaryText));
        }
        holder.setText(R.id.tv_card_gankitem_title, bean.getDesc())
                .setText(R.id.tv_card_gankitem_pubtime, DateUtils.dateToPattern("yyyy-MM-dd", "MM月dd日", bean.getPublishedAt().substring(0, 10)))
                .setText(R.id.tv_card_gankitem_author, bean.getWho())
                .addOnClickListener(R.id.card_gank);
    }
}
