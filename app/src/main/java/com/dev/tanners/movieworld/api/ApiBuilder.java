package com.dev.tanners.movieworld.api;

import android.net.Uri;

import com.dev.tanners.movieworld.api.util.DefaultValueTreeMap;

/**
 * A class that holds static constants of themoviedb restful api urls partials
 */
public class ApiBuilder {
    // image sizes
    public static String TINY = "TINY";
    public static String XSMALL = "EXTRA SMALL";
    public static String SMALL = "SMALL";
    public static String SMALL_MEDIUM  = "SMALL MEDIUM";
    public static String MEDIUM  = "MEDIUM";
    public static String LARGE  = "LARGE";
    public static String ORIGINAL = "original";
    public static String API_KEY = "api_key=";
    public static String API_BASE = "https://api.themoviedb.org/3";
    public static String API_IMAGE_BASE = "https://image.tmdb.org/t/p";
    public static String API_POPULAR_PARTIAL = "movie/popular";
    public static String API_TOP_PARTIAL = "movie/top_rated";
    public static String API_QUERY_OPTIONS_LANG = "language=en-US";
    public static String API_QUERY_OPTIONS_PAGE = "page=";

    /*
        This is a way to get the sizes of the images for the url,
        this is designed to be like an api in the sense that your limited options
        and this extends a custom class to have a default value,
        although for this project, this is not needed, but i try to design
        my objects as if they can be used in other projects, so its best to allow
        customization and rules for the objects, such that if the wrong size is chosen
        or no size, it still gets value, a default one
     */
    // TODO think of better way, constants maybe?
    public static DefaultValueTreeMap<String, String> API_IMAGE_SIZES = new DefaultValueTreeMap<String, String>(MEDIUM) {{
        put(TINY,"w92");
        put(XSMALL,"w154");
        put(SMALL,"w185");
        put(SMALL_MEDIUM,"w342");
        put(MEDIUM,"w500");
        put(LARGE,"w780");
        put(ORIGINAL, ORIGINAL);
    }};
}
