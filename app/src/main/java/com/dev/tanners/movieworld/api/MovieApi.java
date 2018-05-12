package com.dev.tanners.movieworld.api;

import com.dev.tanners.movieworld.api.model.MovieRoot;
import java.util.Map;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * http://square.github.io/retrofit/
 * http://www.vogella.com/tutorials/Retrofit/article.html
 */
public interface MovieApi {

    /**
     * Path to popular movie rest call
     *
     * @param options
     * @return
     */
    @GET(MovieApiHelper.API_POPULAR_PATH)
    Call<MovieRoot> getPopular(@QueryMap Map<String, String> options);

    /**
     * Path to top rated movie rest call
     *
     * @param options
     * @return
     */
    @GET(MovieApiHelper.API_TOP_PATH)
    Call<MovieRoot> getTop(@QueryMap Map<String, String> options);
}