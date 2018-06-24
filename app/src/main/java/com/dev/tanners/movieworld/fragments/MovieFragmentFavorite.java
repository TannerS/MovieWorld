package com.dev.tanners.movieworld.fragments;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dev.tanners.movieworld.MovieActivity;
import com.dev.tanners.movieworld.api.adapters.MovieAdapter;
import com.dev.tanners.movieworld.api.model.movies.MovieResult;
import com.dev.tanners.movieworld.db.MovieDatabase;

import java.util.List;

/**
 * Contains popular movies data
 */
public class MovieFragmentFavorite extends MovieFragmentList {
    // instance to database
    private MovieDatabase mDb;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment MovieFragmentFavorite.
     */
    public static MovieFragmentFavorite newInstance() {
        return new MovieFragmentFavorite();
    }

    /**
     * Load methods after view has been loaded
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        init();
        // return view
        return view;
    }

    private void init()
    {
        mDb = MovieDatabase.getInstance(getContext());
        // set up recycler functionality
        setUpRecycler(
            null,
            new MovieAdapter.OnClickListener() {
                @Override
                public void onClick(MovieResult mMovieResult) {
                    Intent intent = new Intent(mContext, MovieActivity.class);
                    intent.putExtra(MovieActivity.MOVIE_ACTIVITY_BUNDLE_KEY, mMovieResult.getId());
                    startActivity(intent);
                }
            }
        );
        // get all database movies
        LiveData<List<MovieResult>> movies = fetchMovies();
        // set observer that will update adapter if changes
        movies.observe(getActivity(), new Observer<List<MovieResult>>() {
            /**
             * Will update adapter on change
             *
             * @param movieResults
             */
            @Override
            public void onChanged(@Nullable List<MovieResult> movieResults) {
                // update adapter
                mMovieAdapter.updateAdapterRemovedOrAdded(movieResults);
            }
        });
    }

    /**
     * Get all favorites movies from db
     *
     * @return
     */
    private LiveData<List<MovieResult>> fetchMovies()
    {
        // load all favorite movies
        return mDb.getMovieDao().loadAllFavoriteMovies();
    }
}