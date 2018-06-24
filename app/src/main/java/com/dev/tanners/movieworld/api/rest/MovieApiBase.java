package com.dev.tanners.movieworld.api.rest;

import android.content.Context;

import com.dev.tanners.movieworld.R;
import java.util.HashMap;

/**
 * Base class for common functionality upon the rest api
 */
public class MovieApiBase {
    // holds common rest queries
    public HashMap<String, String> queries;
    protected final static String API_KEY = "api_key";
    public final static String API_BASE = "https://api.themoviedb.org/3/";
    protected final static String API_QUERY_OPTIONS_LANG = "language";
    protected final static String API_QUERY_OPTIONS_LANG_ENG = "en-US";
    private Context mContext;

    /**
     */
    public MovieApiBase(Context mContext) {
        queries = new HashMap<String, String>();
        this.mContext = mContext;
    }

    /**
     * Get api key
     * @return
     */
    protected String getmApiKey() {
        return this.mContext.getResources().getString(R.string.restKey);
    }
}
