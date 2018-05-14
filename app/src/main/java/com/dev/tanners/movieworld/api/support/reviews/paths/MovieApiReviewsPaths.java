package com.dev.tanners.movieworld.api.support.reviews.paths;

import com.dev.tanners.movieworld.api.model.reviews.MovieReviewRoot;
import com.dev.tanners.movieworld.api.support.reviews.MovieApiReviews;
import java.util.Map;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * http://square.github.io/retrofit/
 * http://www.vogella.com/tutorials/Retrofit/article.html
 */
public interface MovieApiReviewsPaths {

    /**
     * Path to reviews for movies
     *
     * @param options
     * @return
     */
    @GET(MovieApiReviews.PATH)
    Call<MovieReviewRoot> getReviews(@QueryMap Map<String, String> options);
}
