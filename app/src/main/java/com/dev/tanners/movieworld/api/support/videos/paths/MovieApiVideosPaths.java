package com.dev.tanners.movieworld.api.support.videos.paths;

import com.dev.tanners.movieworld.api.model.videos.MovieVideoRoot;
import com.dev.tanners.movieworld.api.support.videos.MovieApiVideos;
import java.util.Map;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * http://square.github.io/retrofit/
 * http://www.vogella.com/tutorials/Retrofit/article.html
 */
public interface MovieApiVideosPaths {

    /**
     * Path to reviews for movies
     *
     * @param options
     * @return
     */
    @GET(MovieApiVideos.PATH)
    Call<MovieVideoRoot> getReviews(@QueryMap Map<String, String> options);
}
