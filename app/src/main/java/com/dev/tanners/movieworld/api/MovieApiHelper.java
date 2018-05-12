package com.dev.tanners.movieworld.api;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import com.dev.tanners.movieworld.R;
import java.util.HashMap;

/**
 * Helper class to hold references, keys, methods, and constants
 * for the api restfull api calls
 */
public class MovieApiHelper {
    // only used to get access to xml files for settings
    private Context mContext;
    // static member variables for any reason
    // not all may be used
    public final static String TINY = "w92";
    public final static String XSMALL = "w154";
    public final static String SMALL = "w185";
    public final static String SMALL_MEDIUM  = "w342";
    public final static String MEDIUM  = "w500";
    public final static String LARGE  = "w780";
    public final static String ORIGINAL = "original";
    private String mApiKey;
    private final static String API_KEY = "api_key";
    public final static String API_BASE = "https://api.themoviedb.org/3/";
    private final static String API_IMAGE_BASE = "https://image.tmdb.org/t/p/";
    public final static String API_POPULAR_PATH = "movie/popular";
    public final static String API_TOP_PATH = "movie/top_rated";
    private final static String API_QUERY_OPTIONS_LANG = "language";
    private final static String API_QUERY_OPTIONS_LANG_ENG = "en-US";
    private final static String API_QUERY_OPTIONS_PAGE = "page";
    public final static String API_POPULAR = "POPULAR";
    public final static String API_TOP = "TOP";
    // keep track of endless scrolling page
    private int api_top_page;
    private int api_pop_page;
    private HashMap<String, String> pop_queries;
    private HashMap<String, String> common_queries;
    private HashMap<String, String> top_queries;

    /**
     * Constructor
     */
    public MovieApiHelper(Context mContext) {
        // set member variables
        this.mContext = mContext;
        // set api key
        this.mApiKey = mContext.getResources().getString(R.string.restKey);
        // default page number
        api_top_page = 1;
        api_pop_page = 1;
        // set up common rest queries amongst all the calls we need
        common_queries = new HashMap<String, String>() {{
            put(MovieApiHelper.API_KEY, mApiKey);
            put(MovieApiHelper.API_QUERY_OPTIONS_LANG, MovieApiHelper.API_QUERY_OPTIONS_LANG_ENG);
        }};
        // set up popular rest queries
        pop_queries = new HashMap<String, String>() {{
            putAll(common_queries);
            put(MovieApiHelper.API_QUERY_OPTIONS_PAGE, Integer.toString(api_pop_page));
        }};
        // set up top rated rest queries
        top_queries = new HashMap<String, String>() {{
            putAll(common_queries);
            put(MovieApiHelper.API_QUERY_OPTIONS_PAGE, Integer.toString(api_top_page));
        }};
    }

    /**
     * @return
     */
    public HashMap<String, String> getPop_queries() {
        return pop_queries;
    }

    /**
     * @return
     */
    public HashMap<String, String> getTop_queries() {
        return top_queries;
    }

    /**
     * @return
     */
    public int getApi_top_page() {
        return api_top_page;
    }

    /**
     * @return
     */
    public int getApi_pop_page() {
        return api_pop_page;
    }

    /**
     * @return
     */
    public static String getApiQueryOptionsLang() {
        return API_QUERY_OPTIONS_LANG;
    }

    /**
     * @return
     */
    public static String getApiQueryOptionsLangEng() {
        return API_QUERY_OPTIONS_LANG_ENG;
    }

    /**
     * @return
     */
    public static String getApiQueryOptionsPage() {
        return API_QUERY_OPTIONS_PAGE;
    }

    /**
     * Used to keep track of the page number for the rest call for popular
     */
    public void increasePopPage()
    {
        int tempValue = Integer.parseInt(pop_queries.get(MovieApiHelper.API_QUERY_OPTIONS_PAGE));
        pop_queries.put(MovieApiHelper.API_QUERY_OPTIONS_PAGE, String.valueOf(++tempValue));
    }

    /**
     * Used to keep track of the page number for the rest call for top rated
     */
    public void increaseTopPage()
    {
        int tempValue = Integer.parseInt(top_queries.get(MovieApiHelper.API_QUERY_OPTIONS_PAGE));
        top_queries.put(MovieApiHelper.API_QUERY_OPTIONS_PAGE, String.valueOf(++tempValue));
    }

    /**
     * Image paths are relative, not absolute, this fixes that with a preset size.
     * Possible to add ability to pass in size, but for now not needed
     *
     * @param mUrl
     * @return
     */
    public static String formatPathToRestPath(String mUrl, String mSize)
    {
        Log.i("IMAGE_DEBUG", mUrl);

        return (new StringBuilder())
                .append(MovieApiHelper.API_IMAGE_BASE)
                .append("/")
                .append(mSize)
                .append("/")
                .append(mUrl).toString();
    }
}
