package com.dev.tanners.movieworld.api.rest;

import android.content.Context;

/**
 * This will handle the rest call to get popular movies
 */
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

