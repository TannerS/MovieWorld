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
import com.dev.tanners.movieworld.api.support.rest.lists.MovieApiList;
import com.dev.tanners.movieworld.api.support.rest.lists.paths.MovieApiListPaths;
import com.dev.tanners.movieworld.api.support.rest.MovieApiBase;
import com.dev.tanners.movieworld.api.support.rest.lists.MovieApiPopular;
import com.dev.tanners.movieworld.api.support.rest.lists.MovieApiTopRated;
import com.dev.tanners.movieworld.api.adapter.lists.MovieAdapter;
import com.dev.tanners.movieworld.api.model.list.MovieRoot;
import com.dev.tanners.movieworld.api.model.list.results.MovieResult;
import com.dev.tanners.movieworld.util.SimpleSnackBarBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
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
public abstract class MovieFragment extends Fragment {
    // number of grid columns
    protected final int mColumns = 2;
    // retrofit interface object
    protected Callback<MovieRoot> mResponseCallback;
    // interface for rest calls using retrofit
    protected MovieApiListPaths mMovieApiListPaths;
    // layout manager for endless scrolling
    protected GridLayoutManager mGridLayoutManager;
    // recyclerview
    protected RecyclerView mMovieRecyclerView;
    // used to check for loading new movies before loading more
    protected boolean loading;
    // adapter
    protected MovieAdapter mMovieAdapter;
    // progressbar for endless scrolling
    protected ProgressBar mProgressBar;
    // view for current fragment layout view
    protected View view;
    // state to show which list is loaded
    protected enum State {TOP, POP}
    protected State mState;
    // interface for rest calls
    protected MovieApiList mMovieApiList;
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
        loadResources(view);
        // set up callbacks for rest calls for recyclerview
        setUpRestCallback();
        // set up rest calls connection to json parser and callbacks per rest api endpoint
        createApiCall();
        // return view
        return view;
    }

    /**
     * Setup listener for endless scrolling, this code was from a previous project I had
     * The original source was lost but I found something similar
     * https://stackoverflow.com/questions/26543131/how-to-implement-endless-list-with-recyclerview?utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa
     *
     * @return
     */
    protected RecyclerView.OnScrollListener getListener(final MovieApiList mMovieApiList) {
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
                    // show progress bar to show data "should" be loading
                    mProgressBar.setVisibility(View.VISIBLE);
                    // setbool to show data is already doing a request
                    loading = true;
                    // depending on the type of request
                    if(mState == State.POP)
                    {
                        // increase page number so this will set age number
                        // in rest call to get more movies
                        mMovieApiList.increasePage();
                        loadList(MovieApiPopular.ID, mMovieApiList.getQueries());
                    }
                    else if(mState == State.TOP)
                    {
                        // increase page number so this will set age number
                        // in rest call to get more movies
                        mMovieApiList.increasePage();
                        loadList(MovieApiTopRated.ID, mMovieApiList.getQueries());
                    }
                }
            }
        };
    }

    /**
     https://stackoverflow.com/questions/42636247/how-can-i-return-data-in-method-from-retrofit-onresponse?utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa
     */
    protected void createApiCall() {
        // set up json parser for rest call
        ObjectMapper mMapper = new ObjectMapper();
        // build rest api call
        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(MovieApiBase.API_BASE)
                .addConverterFactory(JacksonConverterFactory.create(mMapper))
                .build();
        // align object with api interface that says how to make calls
        mMovieApiListPaths = mRetrofit.create(MovieApiListPaths.class);
    }

    /**
     * Callback for each rest url (top/popular)
     */
    public void setUpRestCallback()
    {
        mResponseCallback = new Callback<MovieRoot>() {
            /**
             * Invoked for a received HTTP response.
             * <p>
             * Note: An HTTP response may still indicate an application-level failure such as a 404 or 500.
             * Call {@link Response#isSuccessful()} to determine if the response indicates success.
             *
             * @param call
             * @param response
             */
            @Override
            public void onResponse(Call<MovieRoot> call, Response<MovieRoot> response) {
                if (response.isSuccessful()) {
                    // set up recyclerview
//                    setUpRecycler(response.body().getResults());
                    mMovieAdapter.updateAdapter(response.body().getResults());
                } else {
                    displayError();
                }
            }

            /**
             * Invoked when a network exception occurred talking to the server or when an unexpected
             * exception occurred creating the request or processing the response.
             *
             * @param call
             * @param t
             */
            @Override
            public void onFailure(Call<MovieRoot> call, Throwable t) {
                t.printStackTrace();
                displayError();
            }
        };
    }

    /**
     * Choose which list to load
     *
     * @param type
     */
    protected void loadList(String type, HashMap<String, String> mQueries)
    {
        // load data depending on the list currently in activity
        switch(type)
        {
            case MovieApiPopular.ID:
                // run callback and rest request in background as an initial start
                mMovieApiListPaths.getPopular(mQueries).enqueue(mResponseCallback);
                break;
            case MovieApiTopRated.ID:
                // run callback and rest request in background as an initial start
                mMovieApiListPaths.getTop(mQueries).enqueue(mResponseCallback);
                break;
        }
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
    }

    /**
     * Set up recyclerview
     */
    protected void setUpRecycler(MovieApiList mMovieApiList)
    {
        // call for data here
        mMovieRecyclerView = view.findViewById(R.id.fragment_gridview);
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
        mMovieAdapter = new MovieAdapter(
                mContext,
//                    mMovieResults,
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
                new MovieAdapter.IImageOnClickListener() {
                    @Override
                    public void onClick(MovieResult mMovieResult) {
                        try {
                            ObjectMapper mapper = new ObjectMapper();
                            // convert object to json
                            String mMovieResultJson = mapper.writeValueAsString(mMovieResult);
                            Intent intent = new Intent(mContext, MovieActivity.class);
                            intent.putExtra(MovieActivity.MOVIE_ACTIVITY_BUNDLE_KEY, mMovieResultJson);
                            startActivity(intent);
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
        // depending on the version of the OS, add listener to the recycler view
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP_MR1) {
            // set listener
            mMovieRecyclerView.addOnScrollListener(getListener(mMovieApiList));
        } else {
            // set listener
            mMovieRecyclerView.setOnScrollListener(getListener(mMovieApiList));
        }
        // set adapter
        mMovieRecyclerView.setAdapter(mMovieAdapter);


        // TODO double check if needed
        loading = false;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }
}
