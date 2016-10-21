package com.glooory.flatreader.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.glooory.flatreader.R;
import com.glooory.flatreader.base.MyApplication;
import com.glooory.flatreader.entity.ithome.ITHomeItemBean;
import com.glooory.flatreader.greendao.DaoSession;
import com.glooory.flatreader.greendao.ITHomeItemBeanDao;
import com.glooory.flatreader.net.ImageLoader;
import com.glooory.flatreader.util.DateUtils;
import com.glooory.flatreader.util.GreenDaoUtils;

/**
 * Created by Glooory on 2016/10/12 0012 13:16.
 */

public class ITHomeAdapter extends BaseQuickAdapter<ITHomeItemBean>{
    private Context mContext;
    private ITHomeItemBeanDao mITHomeDao;

    public ITHomeAdapter(Context context) {
        super(R.layout.cardview_ithome_item, null);
        this.mContext = context;
        DaoSession daoSession = ((MyApplication) context.getApplicationContext()).getDaoSession();
        mITHomeDao = daoSession.getITHomeItemBeanDao();
    }

    @Override
    protected void convert(BaseViewHolder holder, ITHomeItemBean bean) {

        ImageLoader.load(mContext, (ImageView) holder.getView(R.id.img_ithome_item), bean.getImage());

        if (GreenDaoUtils.isEntityExists(mITHomeDao, ITHomeItemBeanDao.Properties.Newsid.eq(bean.getNewsid()))) {
            ((TextView) holder.getView(R.id.tv_ithome_item_title))
                    .setTextColor(mContext.getResources().getColor(R.color.colorSecondaryText));
        } else {
            ((TextView) holder.getView(R.id.tv_ithome_item_title))
                    .setTextColor(mContext.getResources().getColor(R.color.colorPrimaryText));
        }

        holder.setText(R.id.tv_ithome_item_title, bean.getTitle())
                .setText(R.id.tv_ithome_item_pubdate, DateUtils.dateToITPattern(mContext, bean.getPostdate()))
                .setText(R.id.tv_ithome_item_commentcount, String.valueOf(bean.getCommentcount()))
                .addOnClickListener(R.id.card_ithome_item);
    }
}
