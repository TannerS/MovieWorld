package com.dev.tanners.movieworld;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.dev.tanners.movieworld.api.adapter.MovieAdapter;
import com.dev.tanners.movieworld.api.model.movie.MovieResult;
import com.dev.tanners.movieworld.api.model.movie.MovieRoot;
import com.dev.tanners.movieworld.api.support.rest.MovieApi;
import com.dev.tanners.movieworld.api.support.rest.MovieApiBase;
import com.dev.tanners.movieworld.api.support.rest.methods.MovieApiPopular;
import com.dev.tanners.movieworld.api.support.rest.methods.MovieApiTopRated;
import com.dev.tanners.movieworld.api.support.rest.methods.paths.MovieApiListPaths;
import com.dev.tanners.movieworld.util.SimpleSnackBarBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Base fragment class for common functionality for sub classes
 * */
public class MovieFragmentRoot extends Fragment {
    // number of grid columns
    protected int mColumns = 2;
    // view for current fragment layout view
    protected View view;
    // current activity context
    protected Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_movie, container, false);
        // load resources
//        loadResources(view);
        // return view
        return view;
    }

//    /**
//     * Error snackbar
//     */
//    protected void displayError() {
//        SimpleSnackBarBuilder.createAndDisplaySnackBar(view.findViewById(R.id.main_root_container),
//                getString(R.string.loading_image_error),
//                Snackbar.LENGTH_INDEFINITE,
//                getString(R.string.loading_image_error_dismiss));
//    }

//    /**
//     * Load needed resources
//     */
//    protected void loadResources(View view)
//    {
//        mProgressBar = view.findViewById(R.id.loading_progressbar);
//    }

    /**
     * Set up recyclerview
     */
    // TODO may need to remove
//    protected void setUpRecycler(MovieApi mMovieApi)
//    {
//        // call for data here
//        mMovieRecyclerView = view.findViewById(R.id.fragment_gridview);
//        /*
//            Credit for loading gridview
//            https://stackoverflow.com/questions/40587168/simple-android-grid-example-using-recyclerview-with-gridlayoutmanager-like-the?utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa
//         */
//        mGridLayoutManager = new GridLayoutManager(mContext, mColumns);
//        // smooth scrolling to help with endless scrolling
//        mGridLayoutManager.setSmoothScrollbarEnabled(true);
//        // set up with layout manager
//        mMovieRecyclerView.setLayoutManager(mGridLayoutManager);
//        // create adapter
//        mMovieAdapter = new MovieAdapter(
//                mContext,
//                /*
//                    This does not have to be done via a callback but, I like to have
//                    the activity calling the adapter as the main point
//                    to enter configuration for the adapter.
//
//                    In this case, it is easier option then passing in "context" into the class
//                    and hard coding the callback for onclick. I feel this is a good option so that,
//                    giving the user more control over configuring the way it acts
//                    without editing the adapter, a user should be able to change the callback
//                    if needed
//                 */
//                new MovieAdapter.IImageOnClickListener() {
//                    @Override
//                    public void onClick(MovieResult mMovieResult) {
//                        Intent intent = new Intent(mContext, MovieActivity.class);
//                        intent.putExtra(MovieActivity.MOVIE_ACTIVITY_BUNDLE_KEY, mMovieResult.getId());
//                        startActivity(intent);
//                    }
//                }
//        );
//        // depending on the version of the OS, add listener to the recycler view
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP_MR1) {
//            // set listener
//            mMovieRecyclerView.addOnScrollListener(getListener(mMovieApi));
//        } else {
//            // set listener
//            mMovieRecyclerView.setOnScrollListener(getListener(mMovieApi));
//        }
//        // set adapter
//        mMovieRecyclerView.setAdapter(mMovieAdapter);
//
//        loading = false;
//    }

    /**
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }
}
