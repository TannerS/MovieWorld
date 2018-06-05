package com.dev.tanners.movieworld.api.support;

import android.net.Uri;

public class ThumbnailBuilder {

    public enum Scheme {
        HTTPS("https"),
        HTTP("http");

        private String mScheme;

        Scheme(String mScheme) {
            this.mScheme = mScheme;
        }

        public String getScheme() {
            return mScheme;
        }
    }

    private static String BASE = "img.youtube.com";
    private static String BASE_PARTIAL = "vi";

    public enum Thumbnail {
        STANDARD_FILENAME("0.jpg"),
        THUMB1_FILENAME("1.jpg"),
        THUMB2_FILENAME("2.jpg"),
        THUMB3_FILENAME("3.jpg"),
        DEFAULT_FILENAME("default.jpg"),
        HQ_FILENAME("hqdefault.jpg"),
        MQ_FILENAME("mqdefault.jpg"),
        SQ_FILENAME("sddefault.jpg"),
        MAX_HQ_FILENAME("maxresdefault.jpg");

        private String mThumbnail;

        Thumbnail(String mThumbnail) {
            this.mThumbnail = mThumbnail;
        }

        public String getThumbnail() {
            return mThumbnail;
        }
    }

    public static String buildThumbnail(Scheme mScheme, String mVideoId, Thumbnail mThumbnail)
    {
        return (new Uri.Builder())
                .scheme(mScheme.getScheme())
                .authority(BASE)
                .appendPath(BASE_PARTIAL)
                .appendPath(mVideoId)
                .appendPath(mThumbnail.getThumbnail())
                .build()
                .toString();
    }
}
