package com.dev.tanners.movieworld.api.support.videos;

import android.content.Context;
import com.dev.tanners.movieworld.api.MovieApiBase;

public class MovieApiVideos extends MovieApiBase
{
    public static final String PATH = "/movie/{id}/videos";

    public MovieApiVideos(Context mContext) {
        super(mContext);
        // set up default query parameters
        queries.put(API_KEY, getmApiKey());
        queries.put(API_QUERY_OPTIONS_LANG, API_QUERY_OPTIONS_LANG_ENG);
        // TODO maybe sometime soon, add multiple pages / endless scrolling, but for now, first page is fine
    }
}
