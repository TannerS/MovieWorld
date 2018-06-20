package com.dev.tanners.movieworld;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.dev.tanners.movieworld.api.rest.MovieApiPopular;

/**
 * Contains popular movies data
 */
public class PopularFragment extends MovieFragment {

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment PopularFragment.
     */
    public static PopularFragment newInstance() {
        return new PopularFragment();
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
        // let fragment know which state it is
        mState = State.POP;
        // set up object
        mMovieApi = new MovieApiPopular(mContext);
        // set up recycler view
        setUpRecycler(mMovieApi);
        // load initial view
        loadList(MovieApiPopular.ID, mMovieApi.getQueries());
        // return view
        return view;
    }
}
