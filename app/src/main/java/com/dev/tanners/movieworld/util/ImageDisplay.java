package com.dev.tanners.movieworld.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.dev.tanners.movieworld.MainActivity;
import com.dev.tanners.movieworld.R;

/**
 * Used for helper image methods
 */
public class ImageDisplay
{
    /**
     * Load image into imageview
     *
     * @param mContext
     * @param mResource
     * @param mImageView
     */
    public static void loadImage(Context mContext, String mResource, ImageView mImageView, int mPlaceHolder) {
        // load method without placeholder, replacing it with a default placeholder
        loadImage(mContext, mResource, R.color.transparent, mImageView, mPlaceHolder);
    }

    /**
     * Load image into imageview
     *
     * @param mContext
     * @param mResource
     * @param mError
     * @param mImageView
     */
    public static void loadImage(Context mContext, String mResource, int mError, ImageView mImageView, int mPlaceHolder) {
        // https://github.com/bumptech/glide/issues/1484
        if(!((Activity) mContext).isFinishing()) {
            Glide.with(mContext)
                    .load(mResource)
                    .apply(new RequestOptions()
                            .fitCenter()
                            .error(mError)
                            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                            .placeholder(mPlaceHolder)
                    )
                    .transition(new DrawableTransitionOptions()
                            .crossFade())
                    .into(mImageView);
        }
    }

    /**
     * Load image into imageview
     *
     * @param mContext
     * @param mResource
     * @param mError
     * @param mImageView
     */
    public static void loadImage(Context mContext, String mResource, int mError, ImageView mImageView) {
        // load method without placeholder, replacing it with a default placeholder
        loadImage(mContext,  mResource, mError, mImageView, Color.TRANSPARENT);
    }
}
