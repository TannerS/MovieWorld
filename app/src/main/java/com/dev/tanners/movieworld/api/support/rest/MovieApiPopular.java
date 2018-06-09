package com.dev.tanners.movieworld.api.support.rest;

import android.content.Context;

public class MovieApiPopular extends MovieApiList {
    // Used in retrofit api annotation, so need to be static
    public static final String METHOD = "movie/popular";
    public static final String ID = "POPULAR";

    public MovieApiPopular(Context mContext) {
        super(mContext);
    }
}

