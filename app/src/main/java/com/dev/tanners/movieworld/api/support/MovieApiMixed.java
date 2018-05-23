package com.dev.tanners.movieworld.api.support;

import android.content.Context;

import com.dev.tanners.movieworld.api.MovieApiBase;

public class MovieApiMixed extends MovieApiBase
{
    public static final String VIDEO_PATH = "movie/{id}/videos";
    public static final String REVIEW_PATH = "movie/{id}/reviews";

    public MovieApiMixed(Context mContext) {
        super(mContext);
        // set up default query parameters
        queries.put(API_KEY, getmApiKey());
        queries.put(API_QUERY_OPTIONS_LANG, API_QUERY_OPTIONS_LANG_ENG);
    }
}
