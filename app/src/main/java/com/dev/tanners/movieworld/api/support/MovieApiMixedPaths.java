package com.dev.tanners.movieworld.api.support;

import com.dev.tanners.movieworld.api.model.reviews.MovieReviewRoot;
import com.dev.tanners.movieworld.api.model.videos.MovieVideoRoot;
import java.util.Map;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * http://square.github.io/retrofit/
 * http://www.vogella.com/tutorials/Retrofit/article.html
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
