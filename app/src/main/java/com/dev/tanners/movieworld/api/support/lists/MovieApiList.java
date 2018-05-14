package com.dev.tanners.movieworld.api.support.lists;

import android.content.Context;

import com.dev.tanners.movieworld.api.MovieApiBase;

import java.util.HashMap;

public class MovieApiList extends MovieApiBase {
    private int mCurrentPage;

    public HashMap<String, String> getQueries() {
        return queries;
    }

    public final static String API_IMAGE_BASE = "https://image.tmdb.org/t/p/";
    public final static String API_QUERY_OPTIONS_PAGE = "page";
    public final static String TINY = "w92";
    public final static String XSMALL = "w154";
    public final static String SMALL = "w185";
    public final static String SMALL_MEDIUM = "w342";
    public final static String MEDIUM = "w500";
    public final static String LARGE = "w780";
    public final static String ORIGINAL = "original";

    public MovieApiList(Context mContext) {
        super(mContext);
        // default page number
        mCurrentPage = 1;
        // set up default query parameters
        queries.put(API_KEY, getmApiKey());
        queries.put(API_QUERY_OPTIONS_LANG, API_QUERY_OPTIONS_LANG_ENG);
        queries.put(API_QUERY_OPTIONS_PAGE, Integer.toString(mCurrentPage));
    }

    /**
     * Get current page for api call
     *
     * @return
     */
    protected int getmCurrentPage() {
        return mCurrentPage;
    }

    /**
     * Set current page for api call
     *
     * @param mCurrentPage
     */
    protected void setmCurrentPage(int mCurrentPage) {
        this.mCurrentPage = mCurrentPage;
    }

    /**
     * Increase page
     */
    public void increasePage() {
        int tempValue = Integer.parseInt(queries.get(API_QUERY_OPTIONS_PAGE));
        queries.put(queries.get(API_QUERY_OPTIONS_PAGE), String.valueOf(++tempValue));
    }

    /**
     * Image paths are relative, not absolute, this fixes that with a preset size.
     * Possible to add ability to pass in size, but for now not needed
     *
     * @param mUrl
     * @param mSize
     * @return
     */
    public static String formatPathToRestPath(String mUrl, String mSize)
    {
        return (new StringBuilder())
                .append(API_IMAGE_BASE)
                .append("/")
                .append(mSize)
                .append("/")
                .append(mUrl).toString();
    }
}
