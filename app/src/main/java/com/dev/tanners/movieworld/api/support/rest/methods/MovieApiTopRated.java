package com.dev.tanners.movieworld.api.support.rest.methods;

import android.content.Context;

import com.dev.tanners.movieworld.api.support.rest.MovieApi;

public class MovieApiTopRated extends MovieApi {
    // Used in retrofit api annotation, so need to be static
    public static final String METHOD = "movie/top_rated";
    public static final String ID = "TOP";

    /**
     * @param mContext
     */
    public MovieApiTopRated(Context mContext) {
        super(mContext);
    }
}
