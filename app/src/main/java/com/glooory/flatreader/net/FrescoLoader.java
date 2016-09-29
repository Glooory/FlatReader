package com.glooory.flatreader.net;

import android.content.Context;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.net.Uri;

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

        //请求参数， 主要配置url 和C层相关
        ImageRequestBuilder imageRequestBuilder = ImageRequestBuilder
                .newBuilderWithSource(Uri.parse(builder.mUrl))
                .setProgressiveRenderingEnabled(true);  //设置渐进式加载
        if (builder.mResizeOptions != null) {
            imageRequestBuilder.setResizeOptions(builder.mResizeOptions);
        }

        ImageRequest request = imageRequestBuilder.build();

        //初始化C层， 用于控制图片的加载， 是主要的实现控制类
        PipelineDraweeControllerBuilder builderC = Fresco.newDraweeControllerBuilder();

        if (builder.mUrlLow != null) {
            builderC.setLowResImageRequest(ImageRequest.fromUri(builder.mUrlLow));
        }

        builderC.setImageRequest(request);
        //配置渐进式加载
        builderC.setOldController(builder.mSimpleDraweeView.getController());

        setViewPerformance(builder, builderM, builderC);

        if (builder.mControllerListener != null) {
            builderC.setControllerListener(builder.mControllerListener);
        }

        DraweeController draweeController = builderC.build();

        if (builder.mBitmapDataSubscriber != null) {
            ImagePipeline imagePipeline = Fresco.getImagePipeline();

            DataSource<CloseableReference<CloseableImage>> dataSource =
                    imagePipeline.fetchDecodedImage(request, mSimpleDraweeView.getContext());
            dataSource.subscribe(builder.mBitmapDataSubscriber, CallerThreadExecutor.getInstance());
        }

        mSimpleDraweeView.setHierarchy(builderM.build());
        mSimpleDraweeView.setController(draweeController);
    }

    /**
     * 配置DraweeView的各种表现效果
     * 如 失败图、 重试图、圆角等
     * @param builder
     * @param builderM
     * @param builderC
     */
    private void setViewPerformance(Builder builder, GenericDraweeHierarchyBuilder builderM, PipelineDraweeControllerBuilder builderC) {

        //设置图片的缩放方式
        builderM.setActualImageScaleType(builder.mScaleType);

        if (builder.mScaleType == ScalingUtils.ScaleType.FOCUS_CROP) {
            builderM.setActualImageFocusPoint(new PointF(0f, 0f));
        }

        if (builder.mPlaceHolderImg != null) {
            builderM.setPlaceholderImage(builder.mPlaceHolderImg);
        }

        if (builder.mProgressBarImg != null) {
            Drawable progerssbarDrawable = new AutoRotateDrawable(builder.mProgressBarImg, 2000);
            builderM.setProgressBarImage(progerssbarDrawable);
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
        private String mUrlLow; // 低分辨率的图片地址

        private Drawable mPlaceHolderImg;  //占位图
        private Drawable mRetryImg;  //重试图
        private Drawable mProgressBarImg; // 进度图
        private Drawable mFailureImg; // 失败图
        private Drawable mBackgroundImg; // 背景图

        //缩放方式， 默认是center_crop
        private ScalingUtils.ScaleType mScaleType = ScalingUtils.ScaleType.CENTER_CROP;
        private boolean mIsCircle = false;  //是否是圆形图片
        private boolean mIsRadius = false;  //是否为圆角
        private boolean mIsBorder = false;  //是否有包边
        private float mRadius = 5; //默认的圆角半径
        private ResizeOptions mResizeOptions;

        private ControllerListener mControllerListener;  //图片加载的回调
        private BaseBitmapDataSubscriber mBitmapDataSubscriber;

        public Builder(Context context, SimpleDraweeView simpleDraweeView, String url) {
            this.mContext = context;
            this.mSimpleDraweeView = simpleDraweeView;
            this.mUrl = url;
        }

        public FrescoLoader build() {
            //图片不能同时设置为圆形和圆角矩形
            if (mIsCircle && mIsRadius) {
                throw new IllegalArgumentException("you cannot set the image circle and radius at the same time");
            }

            return new FrescoLoader(this);
        }

        public Builder setBitmapDataSubscriber(BaseBitmapDataSubscriber bitmapDataSubscriber) {
            this.mBitmapDataSubscriber = bitmapDataSubscriber;
            return this;
        }

        public Builder setUrlLow(String urlLow) {
            this.mUrlLow = urlLow;
            return this;
        }

        public Builder setPlaceHolderImg(Drawable placeHolderImg) {
            this.mPlaceHolderImg = placeHolderImg;
            return this;
        }

        public Builder setRetryImg(Drawable retryImg) {
            this.mRetryImg = retryImg;
            return this;
        }

        public Builder setProgressBarImg(Drawable progressBarImg) {
            this.mProgressBarImg = progressBarImg;
            return this;
        }

        public Builder setFailureImg(Drawable failureImg) {
            this.mFailureImg = failureImg;
            return this;
        }

        public Builder setBackgroundImg(Drawable backgroundImg) {
            this.mBackgroundImg = backgroundImg;
            return this;
        }

        public Builder setScaleType(ScalingUtils.ScaleType scaleType) {
            this.mScaleType = scaleType;
            return this;
        }

        public Builder setIsCircle(boolean isCircle) {
            this.mIsCircle = isCircle;
            return this;
        }

        public Builder setCircle(boolean isCircle, boolean isBorder) {
            this.mIsCircle = isCircle;
            this.mIsBorder = isBorder;
            return this;
        }

        public Builder setIsRadius(boolean isRadius) {
            this.mIsRadius = isRadius;
            return this;
        }

        public Builder setIsRadius(boolean isRadius, int radius) {
            this.mIsRadius = isRadius;
            this.mRadius = radius;
            return this;
        }

        public Builder setRadius(int radius) {
            this.mRadius = radius;
            return this;
        }

        public Builder setResizeOption(ResizeOptions resizeOption) {
            this.mResizeOptions = resizeOption;
            return this;
        }

        public Builder setControllerListener(ControllerListener controllerListener) {
            this.mControllerListener = controllerListener;
            return this;
        }
    }
}
