package com.glooory.flatreader.ui.storydetail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.glooory.flatreader.R;
import com.glooory.flatreader.base.BaseActivity;
import com.glooory.flatreader.constants.Constants;
import com.glooory.flatreader.entity.ribao.RibaoStoryBean;
import com.glooory.flatreader.entity.ribao.RibaoStoryContentBean;
import com.glooory.flatreader.net.FrescoLoader;
import com.glooory.flatreader.net.RetrofitHelpler;
import com.glooory.flatreader.net.SimpleSubscriber;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Glooory on 2016/10/1 0001 16:49.
 */

public class StoryDetailActivity extends BaseActivity {
    @BindView(R.id.img_story_pic)
    SimpleDraweeView mStoryHeadImg;
    @BindView(R.id.tv_story_detail_title)
    TextView mStoryTitleTv;
    @BindView(R.id.tv_story_detail_pic_author)
    TextView mPicAuthorTv;
    @BindView(R.id.toolbar_story)
    Toolbar mToolbar;
//    @BindView(R.id.webview_story)
//    WebView mWebview;
    @BindView(R.id.coordinator_story)
    CoordinatorLayout mCoordinator;
    @BindView(R.id.tv_temp_story_content)
    TextView mStoryContentTv;

    private String mStoryId;
    private String mTitleImgUrl;
    private String mTitle;
    private RibaoStoryBean mStroryBean;

    public static void launch(Activity activity, String storyId, SimpleDraweeView tranImg) {
        Intent intent = new Intent(activity, StoryDetailActivity.class);
        intent.putExtra(Constants.STORY_ID, storyId);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            tranImg.setTransitionName(activity.getString(R.string.shared_imga_transition_name));
//            activity.startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(
//                    activity, tranImg, activity.getString(R.string.shared_imga_transition_name)
//            ).toBundle());
//        } else {
            activity.startActivity(intent);
//        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);
        ButterKnife.bind(this);
        mStoryId = getIntent().getStringExtra(Constants.STORY_ID);
        initView();
        httpForStoryDetail();
    }

    private void initView() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        mStoryTitleTv.setText(mTitle);
    }

    private void httpForStoryDetail() {

        Logger.d(mStoryId);
        Subscription s = RetrofitHelpler.getInstance().getRibaoService()
                .getStoryContent(mStoryId)
                .map(new Func1<RibaoStoryContentBean, RibaoStoryContentBean>() {
                    @Override
                    public RibaoStoryContentBean call(RibaoStoryContentBean ribaoStoryContentBean) {
                        Logger.d(ribaoStoryContentBean.getTitle());
                        return ribaoStoryContentBean;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SimpleSubscriber<RibaoStoryContentBean>(mContext) {
                    @Override
                    public void onCompleted() {
                        httpForHeadImg();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d(e.getMessage());
//                        super.onError(e);
                    }

                    @Override
                    public void onNext(RibaoStoryContentBean bean) {
                        mStoryTitleTv.setText(bean.getTitle());
                        mPicAuthorTv.setText(bean.getImage_source());
                        mTitleImgUrl = bean.getImage();
                        mStoryContentTv.setText(bean.getBody());
                    }
                });


    }

    private void httpForHeadImg() {
        if (TextUtils.isEmpty(mTitleImgUrl)) {
            return;
        }
        Logger.d(mTitleImgUrl);
        mStoryHeadImg.setVisibility(View.VISIBLE);
        new FrescoLoader.Builder(mContext, mStoryHeadImg, mTitleImgUrl)
                .build();
    }
}
