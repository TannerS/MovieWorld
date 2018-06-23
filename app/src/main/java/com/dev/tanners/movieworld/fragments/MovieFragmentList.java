package com.dev.tanners.movieworld.fragments;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.dev.tanners.movieworld.R;
import com.dev.tanners.movieworld.api.adapters.MovieAdapterDefault;
import com.dev.tanners.movieworld.api.adapters.MovieAdapterGeneric;
import com.dev.tanners.movieworld.util.SimpleSnackBarBuilder;

/**
 * Base fragment class for common functionality for sub classes
 * */
public class MovieFragmentList extends MovieFragmentRoot {
    // layout manager for endless scrolling
    protected GridLayoutManager mGridLayoutManager;
    // recyclerview
    protected RecyclerView mMovieRecyclerView;
    // used to check for loading new movies before loading more
    protected boolean loading;
    // adapter
    protected MovieAdapterDefault mMovieAdapterDefault;
    // progressbar for endless scrolling
    protected ProgressBar mProgressBar;
    // number of grid columns
    protected int mColumns = 2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = super.onCreateView(inflater, container, savedInstanceState);
        // load resources
        loadResources(view);
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

    /**
     * Load needed resources
     */
    protected void loadResources(View view)
    {
        mProgressBar = view.findViewById(R.id.loading_progressbar);
        mMovieRecyclerView = view.findViewById(R.id.fragment_gridview);
        loading = false;
    }

    /**
     * Set up recyclerview
     */
    protected void setUpRecycler(final ListScrollListenerCallback mCallback, MovieAdapterDefault.OnClickListener mOnClickListener)
    {
        /*
            Credit for loading gridview
            https://stackoverflow.com/questions/40587168/simple-android-grid-example-using-recyclerview-with-gridlayoutmanager-like-the?utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa
         */
        mGridLayoutManager = new GridLayoutManager(mContext, mColumns);
        // smooth scrolling to help with endless scrolling
        mGridLayoutManager.setSmoothScrollbarEnabled(true);
        // set up with layout manager
        mMovieRecyclerView.setLayoutManager(mGridLayoutManager);
        // create adapter
        mMovieAdapterDefault = new MovieAdapterDefault(
                mContext,
                mOnClickListener
        );

        if(mCallback != null)
            setUpRecyclerCallback(mCallback);

        // set adapter
        mMovieRecyclerView.setAdapter(mMovieAdapterDefault);
    }

    private void setUpRecyclerCallback(final ListScrollListenerCallback mCallback)
    {
        // depending on the version of the OS, add listener to the recycler view
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP_MR1) {
            // set listener
            mMovieRecyclerView.addOnScrollListener(getListener(mCallback));
        } else {
            // set listener
            mMovieRecyclerView.setOnScrollListener(getListener(mCallback));
        }
    }

    /**
     * Setup listener for endless scrolling, this code was from a previous project I had
     * The original source was lost but I found something similar
     * https://stackoverflow.com/questions/26543131/how-to-implement-endless-list-with-recyclerview?utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa
     *
     * @return
     */
    protected RecyclerView.OnScrollListener getListener(final ListScrollListenerCallback mCallback) {
        return new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                // make sure user is not already loading more
                if (loading) {
                    return;
                }
                // keep track of position
                int mVisibleCount = mGridLayoutManager.getChildCount();
                int mTotalCount = mGridLayoutManager.getItemCount();
                int mPastCount = mGridLayoutManager.findFirstVisibleItemPosition();
                // if at bottom of list, and there is not an already network call updating the adatper,
                // and all those results are updated, update the list with next set of results
                if ((mPastCount + mVisibleCount >= mTotalCount) && !loading) {
                    mCallback.onScroll();
                }
            }
        };
    }

    public interface ListScrollListenerCallback
    {
        public void onScroll();
    }
}
