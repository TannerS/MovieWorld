package com.dev.tanners.movieworld.api.support.rest;

import android.content.Context;

public class MovieApiMixed extends MovieApiBase
{
    public static final String MOVIE = "movie/{id}";
    public final static String API_QUERY_OPTIONS_APPEND = "append_to_response";
    // this is the same as using  /movie/{id}/videos endpoint &&  /movie/{id}/reviews into one CALL
    public final static String API_QUERY_OPTIONS_APPEND_OPTIONS = "reviews,videos";

    public MovieApiMixed(Context mContext) {
        super(mContext);
        // set up default query parameters
        queries.put(API_KEY, getmApiKey());
        queries.put(API_QUERY_OPTIONS_LANG, API_QUERY_OPTIONS_LANG_ENG);
        queries.put(API_QUERY_OPTIONS_APPEND, API_QUERY_OPTIONS_APPEND_OPTIONS);
    }
}
