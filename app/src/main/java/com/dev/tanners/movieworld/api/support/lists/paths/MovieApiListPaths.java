package com.dev.tanners.movieworld.api.support.lists.paths;

import com.dev.tanners.movieworld.api.support.lists.MovieApiPopular;
import com.dev.tanners.movieworld.api.support.lists.MovieApiTopRated;
import com.dev.tanners.movieworld.api.model.list.MovieRoot;
import java.util.Map;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * http://square.github.io/retrofit/
 * http://www.vogella.com/tutorials/Retrofit/article.html
 */
public interface MovieApiListPaths {

    /**
     * Path to popular movie rest call
     *
     * @param options
     * @return
     */
    @GET(MovieApiPopular.METHOD)
    Call<MovieRoot> getPopular(@QueryMap Map<String, String> options);

    /**
     * Path to top rated movie rest call
     *
     * @param options
     * @return
     */
    @GET(MovieApiTopRated.METHOD)
    Call<MovieRoot> getTop(@QueryMap Map<String, String> options);
}