package com.dev.tanners.movieworld.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.dev.tanners.movieworld.MovieActivity;
import com.dev.tanners.movieworld.MovieViewModel;
import com.dev.tanners.movieworld.R;
import com.dev.tanners.movieworld.api.adapters.MovieAdapter;
import com.dev.tanners.movieworld.api.model.movies.MovieResult;
import com.dev.tanners.movieworld.util.SimpleSnackBarBuilder;

import static com.dev.tanners.movieworld.MovieViewModel.INIT_DATA_CALL;
import static com.dev.tanners.movieworld.MovieViewModel.SCROLL_PLACEMENT;

/**
 * Contains top/popular movies data
 */
public class MovieFragment extends MovieFragmentList {
    public enum State {POP, TOP};
    private String mState;
    private static final String ARG = "STATE";
    private MovieViewModel mMovieViewModel;
    private Parcelable mRecyclerviewLayoutSavedState;
    // keep track if method has been called atleast once
    private boolean hasGotInitData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // get state to tell which state to use for the rest call
        if (getArguments() != null) {
            mState = getArguments().getString(ARG);
        }
        // load the init data value
        if(savedInstanceState != null)
            hasGotInitData = savedInstanceState.getBoolean(INIT_DATA_CALL);
        // load viewmodel
        loadViewModelData();
        // there are two calls to get data for the list
        // init call and on scroll, so this handles the first call and only
        // any other calls is when the continuous on scroll callback gets hit
        if(!hasGotInitData) {
            // need to run this once, any other call to this is in the scroll callback
            setRestCall();
            hasGotInitData = true;
        }
    }

    /**
     * Load view model functionality
     */
    private void loadViewModelData()
    {
        // create viewmodel
        mMovieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);

        mMovieViewModel.loadRest(new MovieViewModel.OnResultCallback() {
            @Override
            public void onPostResults() {
                // show progress bar to show data "should" be loading
                mProgressBar.setVisibility(View.GONE);
                // setbool to show data is already doing a request
                loading = false;
                // update adapter from data inside the viewmodel
                mMovieAdapter.updateAdapterRemovedOrAdded(mMovieViewModel.getMovies());
                // since the data is in a background thread, you need to restore the state in that thread
                // this was mentioned in here: https://stackoverflow.com/questions/27816217/how-to-save-recyclerviews-scroll-position-using-recyclerview-state
                mMovieRecyclerView.getLayoutManager().onRestoreInstanceState(mRecyclerviewLayoutSavedState);
            }

            @Override
            public void displayMessage() {
                displayError();
            }
        });
    }

    /**
     * During device rotation
     */
    @Override
    public void onResume() {
        super.onResume();
        // reload adapter with view model's cached movie data
        mMovieAdapter.updateAdapterRemovedOrAdded(mMovieViewModel.getMovies());
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MovieFragmentPopular.
     */
    public static MovieFragment newInstance(State mState) {
        MovieFragment fragment = new MovieFragment();
        Bundle args = new Bundle();
        args.putString(ARG, mState.name());
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Common rest call setup
     */
    private void setRestCall()
    {
        if(State.POP.name().equals(mState))
        {
            // in rest call to get more movies
            mMovieViewModel.getPopular();
        }
        else if(State.TOP.name().equals(mState))
        {
            // in rest call to get more movies
            mMovieViewModel.getTop();
        }
        else
            throw new IllegalArgumentException();
    }

    /**
     * Saves state of recyclerview position
     *
     * All credit goes too Patrick at https://stackoverflow.com/questions/27816217/how-to-save-recyclerviews-scroll-position-using-recyclerview-state
     *
     * @param outState
     */
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        // save position state
        outState.putParcelable(SCROLL_PLACEMENT, mMovieRecyclerView.getLayoutManager().onSaveInstanceState());
        outState.putBoolean(INIT_DATA_CALL, hasGotInitData);
    }

    /**
     * Restores state of recyclerview position
     *
     * All credit goes too Patrick at https://stackoverflow.com/questions/27816217/how-to-save-recyclerviews-scroll-position-using-recyclerview-state
     *
     * @param savedInstanceState
     */
    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            // restore position state
            mRecyclerviewLayoutSavedState = savedInstanceState.getParcelable(SCROLL_PLACEMENT);
            mMovieRecyclerView.getLayoutManager().onRestoreInstanceState(mRecyclerviewLayoutSavedState);
        }
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
        // set up recycler view
        setUpRecycler(new ListScrollListenerCallback() {
                @Override
                public void onScroll() {
                    // increase page number
                    mMovieViewModel.increasePage();
                    // show progress bar to show data "should" be loading
                    mProgressBar.setVisibility(View.VISIBLE);
                    // setbool to show data is already doing a request
                    loading = true;
                    // set up rest call
                    setRestCall();
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
                    // here since data online can change, the only info sent to other activity
                    // is the movie id to load that data
                    intent.putExtra(MovieActivity.MOVIE_ACTIVITY_BUNDLE_KEY, mMovieResult.getId());
                    startActivity(intent);
                }
            }
        );
        // return view
        return view;
    }


    /**
     * Error snackbar
     */
    protected void displayError() {
        SimpleSnackBarBuilder.createAndDisplaySnackBar(view.findViewById(R.id.main_root_container),
                getString(R.string.loading_image_error),
                Snackbar.LENGTH_INDEFINITE,
                getString(R.string.loading_image_error_dismiss));
    }
}

