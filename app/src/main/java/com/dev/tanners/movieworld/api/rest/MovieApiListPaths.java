package com.dev.tanners.movieworld.api.rest;

import com.dev.tanners.movieworld.api.model.movies.MovieResultBase;
import java.util.Map;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Interface for rest calls for popular and top rated movies
 */
public interface MovieApiListPaths {

    /**
     * Path to popular movie rest call
     *
     * @param options
     * @return
     */
    @GET(MovieApiPopular.METHOD)
    Call<MovieResultBase> getPopular(@QueryMap Map<String, String> options);

    /**
     * Path to top rated movie rest call
     *
     * @param options
     * @return
     */
    @GET(MovieApiTopRated.METHOD)
    Call<MovieResultBase> getTop(@QueryMap Map<String, String> options);
}