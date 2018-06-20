package com.dev.tanners.movieworld.api.rest;

import com.dev.tanners.movieworld.api.model.movies.MovieResultAppend;

import java.util.Map;

//import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * This is a wrapper api call interface, instead of /reviews, /videos, this can handle one call for both
 */
public interface MovieApiMixedPaths {
    /**
     * @param reviewId
     * @param options
     * @return
     */
    @GET(MovieApiMixed.METHOD)
    Call<MovieResultAppend> getReviewsVideos(@Path("id") int reviewId, @QueryMap Map<String, String> options);
}