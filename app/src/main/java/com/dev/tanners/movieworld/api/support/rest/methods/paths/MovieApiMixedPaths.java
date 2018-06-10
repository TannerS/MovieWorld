package com.dev.tanners.movieworld.api.support.rest.methods.paths;

import com.dev.tanners.movieworld.api.model.movie.Movie;
import com.dev.tanners.movieworld.api.support.rest.methods.MovieApiMixed;

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
     * @param reviewId
     * @param options
     * @return
     */
    @GET(MovieApiMixed.METHOD)
    Call<Movie> getReviewsVideos(@Path("id") int reviewId, @QueryMap Map<String, String> options);
}