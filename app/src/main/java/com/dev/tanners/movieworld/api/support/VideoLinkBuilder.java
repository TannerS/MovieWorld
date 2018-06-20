package com.dev.tanners.movieworld.api.support;

import android.net.Uri;

/**
 * Support class to build the url for youtube videos
 */
public class VideoLinkBuilder {
    public static final String BASE = "www.youtube.com";
    public static final String BASE_SCHEME = "https";
    public static final String BASE_PATH = "watch";
    public static final String VIDEO_QUERY = "v";

    public static Uri buildVideoUrl(String mKey) {
        return (new Uri.Builder())
                .scheme(BASE_SCHEME)
                .authority(BASE)
                .appendPath(BASE_PATH)
                .appendQueryParameter(VIDEO_QUERY, mKey)
                .build();
    }
}
