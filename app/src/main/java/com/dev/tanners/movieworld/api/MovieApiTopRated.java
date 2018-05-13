package com.dev.tanners.movieworld.api;

import android.content.Context;

public class MovieApiTopRated extends MovieApiList {
    // Used in retrofit api annotation, so need to be static
    public static final String METHOD = "movie/top_rated";
    public static final String ID = "TOP";

    public MovieApiTopRated(Context mContext) {
        super(mContext);
    }
}
