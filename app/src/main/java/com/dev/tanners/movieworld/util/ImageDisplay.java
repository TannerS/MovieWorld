package com.dev.tanners.movieworld.util;

import android.content.Context;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

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
     * @param mError
     * @param mImageView
     */
    public static void loadImage(Context mContext, String mResource, int mError, ImageView mImageView) {
        Glide.with(mContext)
                .load(mResource)
                .apply(new RequestOptions()
                        .fitCenter()
                        .error(mError)
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC))
                .transition(new DrawableTransitionOptions()
                        .crossFade())
                .into(mImageView);
    }
}
