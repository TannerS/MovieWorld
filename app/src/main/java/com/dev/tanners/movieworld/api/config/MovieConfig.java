package com.dev.tanners.movieworld.api.config;

import java.util.TreeMap;

public class MovieConfig {
    public static String TINY = "TINY";
    public static String XSMALL = "EXTRA SMALL";
    public static String SMALL = "SMALL";
    public static String SMALL_MEDIUM  = "SMALL MEDIUM";
    public static String MEDIUM  = "MEDIUM";
    public static String LARGE  = "LARGE";
    public static String ORIGINAL = "original";

    public static String ACTIVITY_KEY = "ADDTIONAL_MOVIE_INFO";

    public static String API_BASE = "https://api.themoviedb.org/3";
    public static String API_IMAGE_BASE = "https://image.tmdb.org/t/p";
    public static TreeMap<String, String> API_IMAGE_SIZES = new TreeMap<String, String>() {{
        put(TINY,"w92");
        put(XSMALL,"w154");
        put(SMALL,"w185");
        put(SMALL_MEDIUM,"w342");
        put(MEDIUM,"w500");
        put(LARGE,"w780");
        put(ORIGINAL, ORIGINAL);
    }};
}
