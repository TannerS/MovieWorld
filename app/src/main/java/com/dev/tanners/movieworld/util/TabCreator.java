package com.dev.tanners.movieworld.util;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;

/**
 * Tab builder util
 */
public class TabCreator {

    /**
     * Simple custom tab builder
     *
     * @param mContext
     * @param mUrl
     * @param mColor
     */
    public static void buildAndLaunchCustomTab(final Context mContext, final String mUrl, int mColor)
    {
        new CustomTabsIntent.Builder()
                .setToolbarColor(
                        ContextCompat.getColor(
                                mContext,
                                mColor)
                ).build()
                .launchUrl(
                        mContext,
                        Uri.parse(mUrl)
                );
    }
}