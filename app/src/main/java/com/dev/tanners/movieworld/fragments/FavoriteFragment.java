package com.dev.tanners.movieworld.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dev.tanners.movieworld.MovieActivity;
import com.dev.tanners.movieworld.api.adapters.MovieAdapter;
import com.dev.tanners.movieworld.api.model.movies.MovieResult;
import com.dev.tanners.movieworld.db.MovieDatabase;
import com.dev.tanners.movieworld.db.MovieEntry;

import java.util.List;

/**
 * Contains popular movies data
 */
public class FavoriteFragment extends MovieFragmentList {
    // instance to database
    private MovieDatabase mDb;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment FavoriteFragment.
     */
    public static FavoriteFragment newInstance() {
        return new FavoriteFragment();
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

        setUpRecycler(
            null,
            new MovieAdapter.OnClickListener() {
                @Override
                public void onClick(MovieResult mMovieResult) {
                    Intent intent = new Intent(mContext, MovieActivity.class);
                    intent.putExtra(MovieActivity.MOVIE_ACTIVITY_BUNDLE_KEY, mMovieResult.getMovieId());
                    startActivity(intent);
                }
            }
        );

        mMovieAdapter.updateAdapter(fetchMovies());
    }

    private List<MovieResult> fetchMovies()
    {
        return mDb.getMovieDao().loadAllFavoriteMovies().getValue();
    }
}