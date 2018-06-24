package com.dev.tanners.movieworld;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.dev.tanners.movieworld.api.adapters.MovieAdapterBase;
import com.dev.tanners.movieworld.api.adapters.MovieAdapterReview;
import com.dev.tanners.movieworld.api.adapters.MovieAdapterVideo;
import com.dev.tanners.movieworld.api.model.movies.MovieResult;
import com.dev.tanners.movieworld.api.model.movies.MovieResultAppend;
import com.dev.tanners.movieworld.api.model.reviews.MovieReview;
import com.dev.tanners.movieworld.api.model.videos.MovieVideo;
import com.dev.tanners.movieworld.api.rest.MovieApiBase;
import com.dev.tanners.movieworld.api.rest.MovieApi;
import com.dev.tanners.movieworld.api.rest.MovieApiMixed;
import com.dev.tanners.movieworld.api.rest.MovieApiMixedPaths;
import com.dev.tanners.movieworld.db.MovieDatabase;
import com.dev.tanners.movieworld.db.MovieExecutor;
import com.dev.tanners.movieworld.util.ImageDisplay;
import com.dev.tanners.movieworld.util.MovieLoader;
import com.dev.tanners.movieworld.util.SimpleSnackBarBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Details base class
 */
public class MovieActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Boolean>  {
//public class MovieActivity extends AppCompatActivity  {
    // key to pass data between activities
    public static String MOVIE_ACTIVITY_BUNDLE_KEY = "ADDITIONAL_MOVIE_INFO";
    // used for database to detect favorited movies
    public static String MOVIE_ACTIVITY_FAVORITE_KEY = "FAVORITE_MOVIE";
    // data for current movie (and for db)
    private MovieResultAppend mMovieResultAppend;
    // interface for rest calls
    private MovieApiMixedPaths mMovieApiMixed;
    // list that will hold reviews and trailers
    private MovieAdapterReview mMixedAdapterReview;
    private MovieAdapterVideo mMixedAdapterVideo;
    // instance to database
    private MovieDatabase mDb;
    // used to keep track of if movie is a favorite
    private boolean isFavorite;
    // reference to favorites icon
    private ImageView mFavStar;
    // movie id of current movie
    private int mMovieId;
    /*
     * This number will uniquely identify our Loader
     */
    private final int MOVIE_LOADER = 2;
    // used to show page is loading
    private ProgressBar mProgressBar;
    // basically the entire page's layout that will be visible after load
    private ConstraintLayout mMainLayout;

    /**
     * Entry point to load methods needed for activity
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        // load activity_toolbar
        mProgressBar = (ProgressBar) findViewById(R.id.movie_loading);
        mMainLayout = (ConstraintLayout) findViewById(R.id.main_container);
        setUpRecyclerViews();
        dbInit();
        getMovieId();
        createRestCall();
        getReviewsVideos();
    }

    /**
     * Load the loader (this will call data base for a query and wait to load page until finished)
     */
    private void loadLoader()
    {
        // create bundle to pass into loader
        Bundle mBundle = new Bundle();
        mBundle.putInt(MovieLoader.MOVIE_ID_BUNDLE_KEY, mMovieId);
        // use loader to get page data
        LoaderManager mLoaderManager = getSupportLoaderManager();
        Loader<Boolean> mMovieLoader = mLoaderManager.getLoader(MOVIE_LOADER);
        // check loader instance
        if(mMovieLoader != null)
            mLoaderManager.initLoader(MOVIE_LOADER, mBundle, this).forceLoad();
        else
            mLoaderManager.restartLoader(MOVIE_LOADER, mBundle, this).forceLoad();
    }

    /**
     * Load reviews and videos recyclerviews
     */
    private void setUpRecyclerViews()
    {
        mMixedAdapterReview = new MovieAdapterReview(this);
        mMixedAdapterVideo = new MovieAdapterVideo(this);

        setUpRecycler(
                mMixedAdapterReview,
                R.id.movie_reviews
        );

        setUpRecycler(
                mMixedAdapterVideo,
                R.id.movie_videos
        );
    }

    /**
     * Get json bundle to object to load UI elements
     *
     * @throws IOException
     */
    private void getMovieId() {
        // get movie object json string
        mMovieId = getIntent().getIntExtra(MOVIE_ACTIVITY_BUNDLE_KEY, -1);
    }

    /**
     * Callback for movie review
     */
    private Callback<MovieResultAppend> setUpMovieCallback()
    {
        return new Callback<MovieResultAppend>() {
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
            public void onResponse(Call<MovieResultAppend> call, Response<MovieResultAppend> response) {
                if (response.isSuccessful()) {
                    // get result
                    mMovieResultAppend = response.body();
                    // load page with newly fetched data
                    setUpPageDetails();
                    // load reviews and videos into adapter
                    setUpAdapterData();
                    // set loader to check for favorite object
                    loadLoader();
                    // load toolbar
                    setUpToolbar();
                } else {
                    displayMessage(R.string.loading_reviews_error);
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
            public void onFailure(Call<MovieResultAppend> call, Throwable t) {
                t.printStackTrace();
                displayMessage(R.string.loading_reviews_error);
            }
        };
    }

    /**
     * Error snackbar
     */
    private void displayMessage(int mStringId) {
        SimpleSnackBarBuilder.createAndDisplaySnackBar(findViewById(R.id.movie_detail_root),
                getString(mStringId),
                Snackbar.LENGTH_INDEFINITE,
                getString(R.string.loading_image_error_dismiss));
    }

    /**
     * load rest api (retrofit) with object mapper and method call based on type
     * https://stackoverflow.com/questions/42636247/how-can-i-return-data-in-method-from-retrofit-onresponse?utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa
     *
     */
    private void createRestCall() {
        // set up json parser for rest call
        ObjectMapper mMapper = new ObjectMapper();
        // build rest api call
        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(MovieApiBase.API_BASE)
                .addConverterFactory(JacksonConverterFactory.create(mMapper))
                .build();

        // align object with api interface that says how to make calls
        mMovieApiMixed = mRetrofit.create(MovieApiMixedPaths.class);
    }

    /**
     * Set up recyclerviews
     */
    private void setUpRecycler(MovieAdapterBase mAdapter, int mLayout)
    {
        // call for data here
        RecyclerView mRecyclerView = findViewById(mLayout);
        // load list layout
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setSmoothScrollbarEnabled(true);
        // set up with layout manager
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        // set adapter
        mRecyclerView.setAdapter(mAdapter);
    }

    /**
     * Get videos and reviews for current selected movie
     */
    private void getReviewsVideos()
    {
        // run callback and rest request in background as an initial start
        mMovieApiMixed.getReviewsVideos(
                mMovieId, (
                        new MovieApiMixed(this)
                ).queries
        ).enqueue (
                setUpMovieCallback()
        );
    }

    /**
     * Set up toolbar to display movie title
     */
    private void setUpToolbar()
    {
        setSupportActionBar((Toolbar) findViewById(R.id.main_toolbar));
//        TextView mToolbarTitle = findViewById(R.id.main_toolbar_title);
//        mToolbarTitle.setText(this.mMovieResultAppend.getTitle());
        // set item_title of activity_toolbar for activity
//        getSupportActionBar().setTitle(this.mMovieResultAppend.getTitle());
//        getSupportActionBar().setSubtitle(this.mMovieResultAppend.getRelease_date());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     * Set page UI with data
     */
    private void setUpPageDetails()
    {
        // load views
        ((TextView) findViewById(R.id.plot_synopsis)).setText(this.mMovieResultAppend.getOverview());
        ((TextView) findViewById(R.id.rating)).setText((String.valueOf(this.mMovieResultAppend.getVote_average()) + " / 10"));

        // call helper method to load images
        // since image is passed in as relative path
        // and not absolute
        ImageDisplay.loadImage(
                this,
                MovieApi.formatPathToRestPath(
                        mMovieResultAppend.getBackdrop_path(),
                        MovieApi.MEDIUM
                ),
                R.drawable.ic_error,
                (ImageView) findViewById(R.id.backsplash_image)
        );

        mFavStar = (ImageView) findViewById(R.id.favorite_star);

        mFavStar.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!isFavorite)
                    {
                        // change icon to show saved movie
                        mFavStar.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_favorite_filled));
                        // save movie
                        saveCurrentMovie();
                        // display message to UI
                        displayMessage(R.string.movie_saved_message);
                    }
                    else
                    {
                        // change icon to show removed movie
                        mFavStar.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_favorite_outline));
                        // remove movie
                        delCurrentMovie();
                        // display message to UI
                        displayMessage(R.string.movie_removed_message);
                    }
                    // be sure to toggle the value for every click
                    isFavorite = !isFavorite;
                }
            }
        );
    }

    /**
     * Set up UI with actual reviews and videos adapters
     */
    private void setUpAdapterData()
    {
        ArrayList<MovieReview> mReviews = mMovieResultAppend.getReviews().getResults();
        ArrayList<MovieVideo> mVideos = mMovieResultAppend.getVideos().getResults();

        // display adapter if data, else textview message
        if(mReviews == null || mReviews.size() == 0) {
            ((TextView) findViewById(R.id.no_reviews)).setVisibility(View.VISIBLE);
            ((RecyclerView) findViewById(R.id.movie_reviews)).setVisibility(View.GONE);
        }
        else
            mMixedAdapterReview.updateAdapterAdded(mReviews);

        // display adapter if data, else textview message
        if(mVideos == null || mVideos.size() == 0) {
            ((TextView) findViewById(R.id.no_videos)).setVisibility(View.VISIBLE);
            ((RecyclerView) findViewById(R.id.movie_videos)).setVisibility(View.GONE);
        }
        else
            mMixedAdapterVideo.updateAdapterAdded(mVideos);
    }

    /**
     * Init the database object
     */
    private void dbInit()
    {
        mDb = MovieDatabase.getInstance(getApplicationContext());
    }

    /**
     * Save current movie to db
     */
    private void saveCurrentMovie()
    {
        // get executor to be able to run insert on separate thread
        MovieExecutor.getInstance().mDiskIO().execute(new Runnable() {
            @Override
            public void run() {
                // create time stamp
                mMovieResultAppend.setTimestamp(new Date());
                // insert movie
                mDb.getMovieDao().insertMovie((MovieResult) mMovieResultAppend);
            }
        });
    }

    /**
     * Del current movie from db
     */
    private void delCurrentMovie()
    {
        // get executor to be able to run insert on separate thread
        MovieExecutor.getInstance().mDiskIO().execute(new Runnable() {
            @Override
            public void run() {
                // now need to change the fav bool since it is just deleted
                mDb.getMovieDao().deleteMovie((MovieResult) mMovieResultAppend);
            }
        });
    }

    /**
     * Load loader
     * @param id
     * @param args
     * @return
     */
    @NonNull
    @Override
    public Loader<Boolean> onCreateLoader(int id, @Nullable Bundle args) {
        return new MovieLoader(this, args);
    }

    /**
     * The loader looks to see if movie is a favorite, this will do the needed
     * task after those results
     *
     * @param loader
     * @param data
     */
    @Override
    public void onLoadFinished(@NonNull Loader<Boolean> loader, Boolean data) {
        // data is the result if move is a favorite or not
        if(data)
        {
            // set bool that will be used for the onclick to favorite or un-favorite a movie
            isFavorite = true;
            // set image to show it is a favorite
            mFavStar.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_favorite_filled));
        }
        // since everything is loaded at this point, show the page
        mProgressBar.setVisibility(View.GONE);
        mMainLayout.setVisibility(View.VISIBLE);
    }

    /**
     * @param loader
     */
    @Override
    public void onLoaderReset(@NonNull Loader<Boolean> loader) {
        // not needed
    }
}
