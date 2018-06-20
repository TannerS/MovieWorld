package com.dev.tanners.movieworld.api.rest;

import android.content.Context;

/**
 * This will handle the rest call to get reviews and videos at once
 */
public class MovieApiMixed extends MovieApiBase
{
    public static final String METHOD = "movie/{id}";
    // this will be used to make the rest call for reviews and videos into one call
    public final static String API_QUERY_OPTIONS_APPEND = "append_to_response";
    // this is the same as using  /movie/{id}/videos endpoint &&  /movie/{id}/reviews into one CALL
    // this is more efficient, easier to handle, and works better on my design flow
    public final static String API_QUERY_OPTIONS_APPEND_OPTIONS = "reviews,videos";

    /**
     * @param mContext
     */
    public MovieApiMixed(Context mContext) {
        super(mContext);
        // set up default query parameters
        queries.put(API_KEY, getmApiKey());
        queries.put(API_QUERY_OPTIONS_LANG, API_QUERY_OPTIONS_LANG_ENG);
        queries.put(API_QUERY_OPTIONS_APPEND, API_QUERY_OPTIONS_APPEND_OPTIONS);
    }
}
