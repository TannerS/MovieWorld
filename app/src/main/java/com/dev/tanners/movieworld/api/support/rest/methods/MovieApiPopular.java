package com.dev.tanners.movieworld.api.support.rest.methods;

import android.content.Context;

import com.dev.tanners.movieworld.api.support.rest.MovieApi;

public class MovieApiPopular extends MovieApi {
    // Used in retrofit api annotation, so need to be static
    public static final String METHOD = "movie/popular";
    public static final String ID = "POPULAR";

    /**
     * @param mContext
     */
    public MovieApiPopular(Context mContext) {
        super(mContext);
    }
}

