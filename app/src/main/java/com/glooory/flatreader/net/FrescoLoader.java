package com.glooory.flatreader.net;

import android.content.Context;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.content.ContextCompat;

import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.drawable.AutoRotateDrawable;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

/**
 * Created by Glooory on 2016/9/29 0029 12:28.
 * 封装图片加载框架
 */

public class FrescoLoader {
    private SimpleDraweeView mSimpleDraweeView;
    private Context mContext;

    private FrescoLoader(Builder builder) {
        this.mContext = builder.mContext;
        this.mSimpleDraweeView = builder.mSimpleDraweeView;

        //初始化M层， 用于初始化图片中包含的数据
        GenericDraweeHierarchyBuilder builderM = new GenericDraweeHierarchyBuilder(mContext.getResources());

        //请求参数， 主要配置url和C层相关
        ImageRequestBuilder imageRequestBuilder = ImageRequestBuilder
                .newBuilderWithSource(Uri.parse(builder.mUrl))
                .setResizeOptions(builder.mResizeOption == null ? null : builder.mResizeOption);
//                .setProgressiveRenderingEnabled(true);//设置渐进式加载

        ImageRequest request = imageRequestBuilder.build();

        //初始化C层， 用于控制图片的加载  是主要的实现控制类
        PipelineDraweeControllerBuilder builderC = Fresco.newDraweeControllerBuilder();

        if (builder.mUrlLow != null) {
            builderC.setLowResImageRequest(ImageRequest.fromUri(builder.mUrlLow));
        }

        builderC.setImageRequest(request);
        //配置渐进式加载
//        builderC.setOldController(builder.mSimpleDraweeView.getController());

        setViewPerformance(builder, builderM, builderC);

        if (builder.mControllerListener != null) {
            builderC.setControllerListener(builder.mControllerListener);
        }

        DraweeController draweeController = builderC.build();

        if (builder.mBitmapDataSubscriber != null) {
            ImagePipeline imagePipline = Fresco.getImagePipeline();

            DataSource<CloseableReference<CloseableImage>> dataSource =
                    imagePipline.fetchDecodedImage(request, mSimpleDraweeView.getContext());
            dataSource.subscribe(builder.mBitmapDataSubscriber, CallerThreadExecutor.getInstance());
        }

        mSimpleDraweeView.setHierarchy(builderM.build());
        mSimpleDraweeView.setController(draweeController);
    }

    /**
     * 配置DraweeView的各种表现效果
     * 如  失败图  重试图  圆角或圆形
     * @param builder
     * @param builderM
     * @param builderC
     */
    private void setViewPerformance(Builder builder, GenericDraweeHierarchyBuilder builderM, PipelineDraweeControllerBuilder builderC) {

        //设置图片的缩放形式
        builderM.setActualImageScaleType(builder.mSacleType);
        if (builder.mSacleType == ScalingUtils.ScaleType.FOCUS_CROP) {
            builderM.setActualImageFocusPoint(new PointF(0f, 0f));
        }

        if (builder.mPlaceHolderImg != null) {
            builderM.setPlaceholderImage(builder.mPlaceHolderImg, ScalingUtils.ScaleType.CENTER);
        }

        if (builder.mProgressBarImg != null) {
            Drawable progressbarDrawable = new AutoRotateDrawable(builder.mProgressBarImg, 2000);
            builderM.setProgressBarImage(progressbarDrawable);
        }

        if (builder.mRetryImg != null) {
            builderC.setTapToRetryEnabled(true);
            builderM.setRetryImage(builder.mRetryImg);
        }

        if (builder.mFailureImg != null) {
            builderM.setFailureImage(builder.mFailureImg);
        }

        if (builder.mBackgroundImg != null) {
            builderM.setBackground(builder.mBackgroundImg);
        }

        if (builder.mIsCircle) {

            if (builder.mIsBorder) {
                //默认白色包边
                builderM.setRoundingParams(RoundingParams.asCircle().setBorder(0xFFFFFFFF, 2));
            } else {
                builderM.setRoundingParams(RoundingParams.asCircle());
            }
        }

        if (builder.mIsRadius) {
            builderM.setRoundingParams(RoundingParams.fromCornersRadius(builder.mRadius));
        }
    }

    //构造器
    public static class Builder {
        private Context mContext;
        private SimpleDraweeView mSimpleDraweeView;
        private String mUrl;

        private String mUrlLow;//低分辨率图片地址

        private Drawable mPlaceHolderImg;//占位图
        private Drawable mProgressBarImg;//loading图
        private Drawable mRetryImg;//重试图
        private Drawable mFailureImg;//失败图
        private Drawable mBackgroundImg;//背景图

        private ScalingUtils.ScaleType mSacleType = ScalingUtils.ScaleType.CENTER_CROP;
        private boolean mIsCircle = false; //是否是圆形图片
        private boolean mIsRadius = false; //是否有圆角
        private boolean mIsBorder = false; //是否有包边
        private float mRadius = 5; //默认的圆角半径
        private ResizeOptions mResizeOption;

        private ControllerListener mControllerListener; //图片加载的回调

        private BaseBitmapDataSubscriber mBitmapDataSubscriber;

        //Construtor
        public Builder(Context context, SimpleDraweeView simpleDraweeView, String url) {
            this.mContext = context;
            this.mSimpleDraweeView = simpleDraweeView;
            this.mUrl = url;
        }

        public FrescoLoader build() {
            //图片不能同时设置成圆形和带圆角的
            if (mIsCircle && mIsRadius) {
                throw new IllegalArgumentException("you cannot set the image circle or radius at the same time");
            }
            return new FrescoLoader(this);
        }

        public Builder setBitmapDataSubscriber(BaseBitmapDataSubscriber subscriber) {
            this.mBitmapDataSubscriber = subscriber;
            return this;
        }

        public Builder setUrlLow(String urlLow) {
            this.mUrlLow = urlLow;
            return this;
        }

        public Builder setActualImageScaleType(ScalingUtils.ScaleType scaleType) {
            this.mSacleType = scaleType;
            return this;
        }

        public Builder setPlaceHolderImage(Drawable placeHolder) {
            this.mPlaceHolderImg = placeHolder;
            return this;
        }

        public Builder setProgressbarImage(Drawable progressbarImage) {
            this.mProgressBarImg = progressbarImage;
            return this;
        }

        public Builder setRetryImage(Drawable retryImage) {
            this.mRetryImg = retryImage;
            return this;
        }

        public Builder setFailureIamge(Drawable failureIamge) {
            this.mFailureImg = failureIamge;
            return this;
        }

        public Builder setBackgroundImage(Drawable backgroundImage) {
            this.mBackgroundImg = backgroundImage;
            return this;
        }

        public Builder setBackgroundImageColor(int colorId) {
            Drawable color = ContextCompat.getDrawable(mContext, colorId);
            this.mBackgroundImg = color;
            return this;
        }

        public Builder setIsCircle(boolean isCircle) {
            this.mIsCircle = isCircle;
            return this;
        }

        public Builder setIsCircle(boolean isCircle, boolean isBorder) {
            this.mIsCircle = isCircle;
            this.mIsBorder = isBorder;
            return this;
        }

        public Builder setIsRadius(boolean isRadius) {
            this.mIsRadius = isRadius;
            return this;
        }

        public Builder setIsRadius(boolean isRadius, float radius) {
            this.mRadius = radius;
            return setIsRadius(isRadius);
        }

        public Builder setRadius(float radius) {
            this.mRadius = radius;
            return this;
        }

        public Builder setResizeOptions(ResizeOptions resizeOptions) {
            this.mResizeOption = resizeOptions;
            return this;
        }

        public Builder setControllerListenrr(ControllerListener listenrr) {
            this.mControllerListener = listenrr;
            return this;
        }

        public Builder setScaleType(ScalingUtils.ScaleType scaleType) {
            this.mSacleType = scaleType;
            return this;
        }
    }
}
