package com.dev.tanners.movieworld.api;

import java.util.HashMap;

public class MovieApiHelper {
    // static member variables for any reason
    public final static String TINY = "w92";
    public final static String XSMALL = "w154";
    public final static String SMALL = "w185";
    public final static String SMALL_MEDIUM  = "w342";
    public final static String MEDIUM  = "w500";
    public final static String LARGE  = "w780";
    public final static String ORIGINAL = "original";
    public final static String KEY = "";
    public final static String API_KEY = "api_key";
    public final static String API_BASE = "https://api.themoviedb.org/3/";
    public final static String API_IMAGE_BASE = "https://image.tmdb.org/t/p/";
    public final static String API_POPULAR_PATH = "movie/popular";
    public final static String API_TOP_PATH = "movie/top_rated";
    public final static String API_QUERY_OPTIONS_LANG = "language";
    public final static String API_QUERY_OPTIONS_LANG_ENG = "en-US";
    public final static String API_QUERY_OPTIONS_PAGE = "page";
    public final static String API_POPULAR = "POPULAR";
    public final static String API_TOP = "TOP";
    // keep track of endless scrolling page
    private int api_top_page;
    private int api_pop_page;
    private HashMap<String, String> pop_queries;
    private HashMap<String, String> common_queries;
    private HashMap<String, String> top_queries;

    public MovieApiHelper() {
        api_top_page = 1;
        api_pop_page = 1;

        common_queries = new HashMap<String, String>() {{
            put(MovieApiHelper.API_KEY, MovieApiHelper.KEY);
            put(MovieApiHelper.API_QUERY_OPTIONS_LANG, MovieApiHelper.API_QUERY_OPTIONS_LANG_ENG);
        }};

        pop_queries = new HashMap<String, String>() {{
            putAll(common_queries);
            put(MovieApiHelper.API_QUERY_OPTIONS_PAGE, Integer.toString(api_pop_page));
        }};

        top_queries = new HashMap<String, String>() {{
            putAll(common_queries);
            put(MovieApiHelper.API_QUERY_OPTIONS_PAGE, Integer.toString(api_top_page));
        }};

    }

    public HashMap<String, String> getPop_queries() {
        return pop_queries;
    }

    public HashMap<String, String> getTop_queries() {
        return top_queries;
    }


    public int getApi_top_page() {
        return api_top_page;
    }

    public int getApi_pop_page() {
        return api_pop_page;
    }

    public static String getApiQueryOptionsLang() {
        return API_QUERY_OPTIONS_LANG;
    }

    public static String getApiQueryOptionsLangEng() {
        return API_QUERY_OPTIONS_LANG_ENG;
    }

    public static String getApiQueryOptionsPage() {
        return API_QUERY_OPTIONS_PAGE;
    }

    public void increasePopPage()
    {
        int tempValue = Integer.parseInt(pop_queries.get(MovieApiHelper.API_QUERY_OPTIONS_PAGE));
        pop_queries.put(MovieApiHelper.API_QUERY_OPTIONS_PAGE, String.valueOf(++tempValue));
    }

    public void increaseTopPage()
    {
        int tempValue = Integer.parseInt(top_queries.get(MovieApiHelper.API_QUERY_OPTIONS_PAGE));
        top_queries.put(MovieApiHelper.API_QUERY_OPTIONS_PAGE, String.valueOf(++tempValue));
    }

    public void resetPopPage()
    {
        pop_queries.put(MovieApiHelper.API_QUERY_OPTIONS_PAGE, String.valueOf(1));
    }

    public void resetTopPage()
    {
        top_queries.put(MovieApiHelper.API_QUERY_OPTIONS_PAGE, String.valueOf(1));
    }
}
