package com.dev.tanners.movieworld.api.rest;

import android.content.Context;

/**
 * This will handle the rest call to get the top rated
 */
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
