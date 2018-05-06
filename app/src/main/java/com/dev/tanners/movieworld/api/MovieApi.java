package com.dev.tanners.movieworld.api;

import com.dev.tanners.movieworld.api.model.MovieRoot;

import java.util.HashMap;
import java.util.Map;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * http://square.github.io/retrofit/
 * http://www.vogella.com/tutorials/Retrofit/article.html
 */
public interface MovieApi {

    @GET(API_POPULAR_PATH)
    Call<MovieRoot> getPopular(@QueryMap Map<String, String> options);

    @GET(API_TOP_PATH)
    Call<MovieRoot> getTop(@QueryMap Map<String, String> options);

    public final static String TINY = "w92";
    public final static String XSMALL = "w154";
    public final static String SMALL = "w185";
    public final static String SMALL_MEDIUM  = "w342";
    public final static String MEDIUM  = "w500";
    public final static String LARGE  = "w780";
    public final static String ORIGINAL = "original";
    public final static String API_KEY = "api_key";
    public final static String KEY = "";
    public final static String API_BASE = "https://api.themoviedb.org/3";
    public final static String API_IMAGE_BASE = "https://image.tmdb.org/t/p";
    public final static String API_POPULAR_PATH = "movie/popular";
    public final static String API_TOP_PATH = "movie/top_rated";
    public final static String API_QUERY_OPTIONS_LANG = "language";
    public final static String API_QUERY_OPTIONS_LANG_ENG = "en-US";
    public final static String API_QUERY_OPTIONS_PAGE = "page";
    public final static String API_POPULAR = "POPULAR";
    public final static String API_TOP = "TOP";

    public static final HashMap<String, String> QUERIES = new HashMap<String, String>() {{
        put(MovieApi.API_KEY, MovieApi.KEY);
        put(MovieApi.API_QUERY_OPTIONS_LANG, MovieApi.API_QUERY_OPTIONS_LANG_ENG);
        // TODO need way to update later
        put(MovieApi.API_QUERY_OPTIONS_PAGE, Integer.toString(1));
    }};

}