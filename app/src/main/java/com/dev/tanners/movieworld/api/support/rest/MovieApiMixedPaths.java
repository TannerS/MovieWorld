package com.dev.tanners.movieworld.api.support.rest;

import com.dev.tanners.movieworld.api.model.reviews.MovieReviewRoot;
import com.dev.tanners.movieworld.api.model.videos.MovieVideoRoot;
import java.util.Map;

//import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * http://square.github.io/retrofit/
 * http://www.vogella.com/tutorials/Retrofit/article.html
 * https://medium.com/@adinugroho/chaining-multiple-retrofit-call-using-rxjava-177b64c8103e
 */
public interface MovieApiMixedPaths {

    /**
     * Path to reviews for movies
     *
     * @param options
     * @return
     */
    @GET(MovieApiMixed.REVIEW_PATH)
    Call<MovieReviewRoot> getReviews(@Path("id") int reviewId, @QueryMap Map<String, String> options);
     /**
     * Path to reviews for movies
     *
     * @param options
     * @return
     */
    @GET(MovieApiMixed.VIDEO_PATH)
    Call<MovieVideoRoot> getVideos(@Path("id") int videoId, @QueryMap Map<String, String> options);
}