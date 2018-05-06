package com.dev.tanners.movieworld.api;

import android.net.Uri;

import com.dev.tanners.movieworld.api.util.DefaultValueTreeMap;

/**
 * Sub class with the Popular api call features.
 * Such as page number to be incremented if needed
 */
public class ApiBuilderUtil extends ApiBuilder {
    // to update page to display more results
    private static int POPULAR_PAGE_COUNT = 1;
    private static int TOP_PAGE_COUNT = 1;

    /**
     * Rest url to popular movies
     *
     * @return
     */
    public static Uri PopularMoviesUrl()
    {
        Uri.Builder mBuilder = new Uri.Builder();

        return mBuilder.scheme(API_BASE)
                .path(API_POPULAR_PARTIAL)
                .query(API_KEY)
                .query(API_QUERY_OPTIONS_LANG)
                .query(API_QUERY_OPTIONS_PAGE + POPULAR_PAGE_COUNT)
                .build();
    }

    /**
     * Rest url to top movies
     *
     * @return
     */
    public static Uri TopMoviesUrl()
    {
        Uri.Builder mBuilder = new Uri.Builder();

        return mBuilder.scheme(API_BASE)
                .path(API_POPULAR_PARTIAL)
                .query(API_KEY)
                .query(API_QUERY_OPTIONS_LANG)
                .query(API_QUERY_OPTIONS_PAGE + TOP_PAGE_COUNT)
                .build();
    }
}
