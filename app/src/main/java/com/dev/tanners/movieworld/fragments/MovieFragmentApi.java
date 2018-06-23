package com.dev.tanners.movieworld.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dev.tanners.movieworld.MovieActivity;
import com.dev.tanners.movieworld.api.adapters.MovieAdapter;
import com.dev.tanners.movieworld.api.model.movies.MovieResult;
import com.dev.tanners.movieworld.api.rest.MovieApiTopRated;

/**
 * Contains top/opular movies data
 */
public class MovieFragmentApi extends MovieFragmentNetwork {
    public enum State {POP, TOP};
    private String mState;
    private static final String ARG = "STATE";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mState = getArguments().getString(ARG);
        }

        loadRest(new OnResultCallback() {
            @Override
            public void onPostResults() {
                // show progress bar to show data "should" be loading
                mProgressBar.setVisibility(View.GONE);
                // setbool to show data is already doing a request
                loading = false;
            }
        });

    }

    public MovieFragmentApi() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MovieFragmentPopular.
     */
    public static MovieFragmentApi newInstance(State mState) {
        MovieFragmentApi fragment = new MovieFragmentApi();
        Bundle args = new Bundle();
        args.putString(ARG, mState.name());
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Common rest call setup
     */
    private void setRestInterface()
    {
        if(State.POP.name().equals(mState))
        {
            // in rest call to get more movies
            mMovieApiListPaths.getPopular(
                    mMovieApi.getQueries()
            ).enqueue(
                    mResponseCallback
            );
        }
        else if(State.TOP.name().equals(mState))
        {
            // in rest call to get more movies
            mMovieApiListPaths.getTop(
                    mMovieApi.getQueries()
            ).enqueue(
                    mResponseCallback
            );
        }
        else
            throw new IllegalArgumentException();
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
        // set up object
        mMovieApi = new MovieApiTopRated(mContext);
        // set up recycler view
        setUpRecycler(new ListScrollListenerCallback() {
                @Override
                public void onScroll() {
                    // increase page number
                    mMovieApi.increasePage();


                    // show progress bar to show data "should" be loading
                    mProgressBar.setVisibility(View.VISIBLE);
                    // setbool to show data is already doing a request
                    loading = true;

                    setRestInterface();
                }
            },
              /*
                This does not have to be done via a callback but, I like to have
                the activity calling the adapter as the main point
                to enter configuration for the adapter.

                In this case, it is easier option then passing in "context" into the class
                and hard coding the callback for onclick. I feel this is a good option so that,
                giving the user more control over configuring the way it acts
                without editing the adapter, a user should be able to change the callback
                if needed
             */
            new MovieAdapter.OnClickListener() {
                @Override
                public void onClick(MovieResult mMovieResult) {
                    Intent intent = new Intent(mContext, MovieActivity.class);
                    intent.putExtra(MovieActivity.MOVIE_ACTIVITY_BUNDLE_KEY, mMovieResult.getMovieId());
                    startActivity(intent);
                }
            }
        );
        // load rest
        setRestInterface();
        // return view
        return view;
    }
}

