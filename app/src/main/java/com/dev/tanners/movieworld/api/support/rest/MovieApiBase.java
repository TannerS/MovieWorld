package com.dev.tanners.movieworld.api.support.rest;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import com.dev.tanners.movieworld.R;
import java.util.HashMap;

/**
 * Base class for common functionality upon the api
 */
public class MovieApiBase {
    // used to get application resources
    protected Context mContext;
    // holds common rest queries
    public HashMap<String, String> queries;
    protected final static String API_KEY = "api_key";
    public final static String API_BASE = "https://api.themoviedb.org/3/";
    protected final static String API_QUERY_OPTIONS_LANG = "language";
    protected final static String API_QUERY_OPTIONS_LANG_ENG = "en-US";

    public MovieApiBase(Context mContext) {
        this.mContext = mContext;
        queries = new HashMap<String, String>();
    }

    protected String getmApiKey() {
        return this.mContext.getResources().getString(R.string.restKey);
    }

}
