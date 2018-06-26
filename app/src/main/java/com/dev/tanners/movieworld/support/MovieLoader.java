package com.dev.tanners.movieworld.support;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;
import com.dev.tanners.movieworld.api.model.movies.MovieResult;
import com.dev.tanners.movieworld.db.MovieDatabase;

public class MovieLoader extends AsyncTaskLoader<Boolean> {
    // instance to database
    private MovieDatabase mDb;
    // movie id
    private int mMovieId;
    // movie id key
    public static String MOVIE_ID_BUNDLE_KEY = "MOVIE_ID";

    /**
     * Constructor
     * @param context
     * @param mBundle
     */
    public MovieLoader(@NonNull Context context, Bundle mBundle) {
        super(context);
        // can't be used if bundle is empty
        if (mBundle == null)
            return;
        // get movie id from bundle
        mMovieId = mBundle.getInt(MOVIE_ID_BUNDLE_KEY);
        // load data base instance
        mDb = MovieDatabase.getInstance(context);
    }

    /**
     * load this in background, to query database
     *
     * @return
     */
    @Nullable
    @Override
    public Boolean loadInBackground() {
        return isFavorite();
    }

    /**
     * Movies can be loaded from db or rest,
     * the rest movies do not have the favorite
     * variable set even if it is in db, since
     * it is loaded from rest not db, so we do a
     * simple query to check for the page properties
     * can be loaded correctly
     *
     * @return
     */
    private boolean isFavorite()
    {
        // call database to query for the object, this will check if it exist
        MovieResult mMovieResult = mDb.getMovieDao().loadMovieById(mMovieId);
        // return result if movie is in db
        return mMovieResult != null;
    }
}
