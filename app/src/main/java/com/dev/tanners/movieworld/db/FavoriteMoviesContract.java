package com.dev.tanners.movieworld.db;

import android.provider.BaseColumns;

public class FavoriteMoviesContract
{
    private FavoriteMoviesContract() {};

    public static final class MovieEntry implements BaseColumns
    {
        public static final String TABLE_NAME = "movies";
        public static final String COLUMN_NAME_ID = "movie_id";
        public static final String COLUMN_NAME_POSTER = "poster_url";
        public static final String COLUMN_NAME_TIMESTAMP = "timestamp";
//        public static final boolean COLUMN_NAME_SAVED = false;
    }
}
