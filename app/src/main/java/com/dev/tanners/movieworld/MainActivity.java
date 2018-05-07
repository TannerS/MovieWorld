package com.dev.tanners.movieworld;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.dev.tanners.movieworld.api.MovieApiHelper;
import com.dev.tanners.movieworld.api.adapter.MovieAdapter;
import com.dev.tanners.movieworld.api.callback.IImageBackDropUrlCallback;
import com.dev.tanners.movieworld.api.callback.IImageOnClickListener;
import com.dev.tanners.movieworld.api.model.MovieRoot;
import com.dev.tanners.movieworld.api.model.results.MovieResult;
import com.dev.tanners.movieworld.api.MovieApi;
import com.dev.tanners.movieworld.util.SimpleSnackBarBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class MainActivity extends AppCompatActivity{
    // number of grid columns
    private final int mColumns = 2;
    // retrofit interface object
    private Callback<MovieRoot> mResponseCallback;
    // interface for rest calls using retrofit
    private MovieApi mMovieApi;
    // layout manager for endless scrolling
    private GridLayoutManager mGridLayoutManager;
    // recyclerview
    private RecyclerView mMovieRecyclerView;
    // used to check for loading new movies before loading more
    private boolean loading;
    // used to help keep track of pages
    MovieApiHelper mApiHelper;
    // state to show which list is loaded
    enum State {TOP, POP}
    private State mState;
    // adapter
    private MovieAdapter mMovieAdapter;
    // progressbar for endless scrolling
    private ProgressBar mProgressBar;

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // load resources
        loadResources();
        // set up callbacks for rest calls for recyclerview
        setUpRestCallback();
        // set up rest calls connection to json parser and callbacks per rest api endpoint
        createApi();
        // load initial view
        loadList(MovieApiHelper.API_POPULAR);
    }

    /**
     * Setup listener for endless scrolling, this code was from a previous project I had
     * The original source was lost but I found something similar
     * https://stackoverflow.com/questions/26543131/how-to-implement-endless-list-with-recyclerview?utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa
     *
     * @return
     */
    private RecyclerView.OnScrollListener getListener() {
        return new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                // make sure user is not already loading more
                if (loading) {
                    return;
                }
                int mVisibleCount = mGridLayoutManager.getChildCount();
                int mTotalCount = mGridLayoutManager.getItemCount();
                int mPastCount = mGridLayoutManager.findFirstVisibleItemPosition();
                // if at bottom of list, and there is not an already network call updating the adatper,
                // and all those results are updated, update the list with next set of results
                if ((mPastCount + mVisibleCount >= mTotalCount) && !loading) {

                    mProgressBar.setVisibility(View.VISIBLE);
                    loading = true;

                   if(mState == State.POP)
                   {
                       // increase page number so this will set age number
                       // in rest call to get more movies
                       mApiHelper.increasePopPage();
                       loadList(MovieApiHelper.API_POPULAR);
                   }
                   else if(mState == State.TOP)
                   {
                       // increase page number so this will set age number
                       // in rest call to get more movies
                       mApiHelper.increaseTopPage();
                       loadList(MovieApiHelper.API_TOP);
                   }

                }
            }
        };
    }

    /**
     * Set up recyclerview
     */
    private void setUpRecycler(ArrayList<MovieResult> mMovieResults)
    {
        // this is a check for endless scrolling
        // if this is not null, then update adapter, dont create it
        if(mMovieRecyclerView == null) {


            Log.i("ADAPTER", "DEBUG 4");


            // call for data here
            mMovieRecyclerView = findViewById(R.id.activity_main_gridview);
        /*
            Credit for loading gridview
            https://stackoverflow.com/questions/40587168/simple-android-grid-example-using-recyclerview-with-gridlayoutmanager-like-the?utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa
         */
            mGridLayoutManager = new GridLayoutManager(this, mColumns);
            // smooth scrolling to help with endless scrolling
            mGridLayoutManager.setSmoothScrollbarEnabled(true);
            // set up with layout manager
            mMovieRecyclerView.setLayoutManager(mGridLayoutManager);
            // create adapter
            mMovieAdapter = new MovieAdapter(
                    this,
                    mMovieResults,
            /*
                The rest call has the path of the image for said movie
                as a relative path, this callback is set to be able to pass in
                the configuration for the image to be added to the url
                of that relative path to get the full url for said movie image.

                This does not have to be done via a callback but, I like to have
                the activity calling the adapter as the main point
                to enter configuration for the adapter
            */
                    new IImageBackDropUrlCallback() {
                        @Override
                        public String formatUrl(String mUrl) {
                            return (new StringBuilder())
                                    .append(MovieApiHelper.API_IMAGE_BASE)
//                                    .append("/")
                                    .append(MovieApiHelper.MEDIUM)
//                                    .append("/")
                                    .append(mUrl).toString();
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
                    new IImageOnClickListener() {
                        @Override
                        public void onClick(MovieResult mMovieResult) {
                            try {
                                ObjectMapper mapper = new ObjectMapper();
                                String mMovieResultJson = mapper.writeValueAsString(mMovieResult);
                                Intent intent = new Intent(MainActivity.this, MovieActivity.class);
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
                mMovieRecyclerView.addOnScrollListener(getListener());
            } else {
                mMovieRecyclerView.setOnScrollListener(getListener());
            }
            // set adapter
            mMovieRecyclerView.setAdapter(mMovieAdapter);
        }
        else
        {
            // update adapter
            mMovieAdapter.updateAdapter(mMovieResults);
            // this assumes the progressbar is visible from earlier
            // so since data is updated, hide it
            mProgressBar.setVisibility(View.GONE);
            // reset loading so user can load more on scroll
        }
        // prevents loading more before previous request is done
        loading = false;
    }

    /**
     https://stackoverflow.com/questions/42636247/how-can-i-return-data-in-method-from-retrofit-onresponse?utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa
     */
    private void createApi() {
        // set up json parser for rest call
        ObjectMapper mMapper = new ObjectMapper();
        // build rest api call
        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(MovieApiHelper.API_BASE)
                .addConverterFactory(JacksonConverterFactory.create(mMapper))
//                .setLogLevel(BuildConfig.DEBUG)
                .build();
        // align object with api interface that says how to make calls
        mMovieApi = mRetrofit.create(MovieApi.class);
        // set up helper
        mApiHelper = new MovieApiHelper();
    }

    /**
     * Callback for each rest url (top/popular)
     */
    private void setUpRestCallback()
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
                Log.i("RETROCALL_URL", call.request().url().toString());
                // set up recyclerview
                setUpRecycler(response.body().getResults());
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
    private void loadList(String type)
    {
        // load data depending on the list currently in activity
        switch(type)
        {
            case MovieApiHelper.API_POPULAR:
                // load state
                mState = State.POP;
                // run callback and rest request in background as an initial start
                mMovieApi.getPopular(mApiHelper.getPop_queries()).enqueue(mResponseCallback);
                break;
            case MovieApiHelper.API_TOP:
                // load state
                mState = State.TOP;
                // run callback and rest request in background as an initial start
                mMovieApi.getTop(mApiHelper.getTop_queries()).enqueue(mResponseCallback);
                break;
        }
    }

    /**
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    /**
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId())
        {
            case R.id.popular_movies_menu_item:
                // reset page number
                mApiHelper.resetPopPage();
                // TODO consider caching results or just always reloading them
                loadList(MovieApiHelper.API_POPULAR);
                break;

            case R.id.top_movies_menu_item:
                // reset page number
                mApiHelper.resetTopPage();
                // TODO consider caching results or just always reloading them
                loadList(MovieApiHelper.API_TOP);
                break;

            case R.id.credits_menu_item:
                // TODO make activity or dialog to give credits to any material that request it
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Error snackbar
     */
    public void displayError() {
        SimpleSnackBarBuilder.createAndDisplaySnackBar(findViewById(R.id.main_root_container),
                "Error loading page data",
                Snackbar.LENGTH_INDEFINITE,
                "CLOSE");
    }

    private void loadResources()
    {
        mProgressBar = findViewById(R.id.loading_progressbar);
    }
}




