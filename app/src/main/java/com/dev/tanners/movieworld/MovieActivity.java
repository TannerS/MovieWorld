package com.dev.tanners.movieworld;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import com.dev.tanners.movieworld.api.adapters.MovieAdapterBase;
import com.dev.tanners.movieworld.api.adapters.MovieAdapterReview;
import com.dev.tanners.movieworld.api.adapters.MovieAdapterVideo;
import com.dev.tanners.movieworld.api.model.movies.MovieResultAppend;
import com.dev.tanners.movieworld.api.model.reviews.MovieReview;
import com.dev.tanners.movieworld.api.model.videos.MovieVideo;
import com.dev.tanners.movieworld.api.rest.MovieApiBase;
import com.dev.tanners.movieworld.api.rest.MovieApi;
import com.dev.tanners.movieworld.api.rest.MovieApiMixed;
import com.dev.tanners.movieworld.api.rest.MovieApiMixedPaths;
import com.dev.tanners.movieworld.db.MovieDatabase;
import com.dev.tanners.movieworld.db.MovieEntry;
import com.dev.tanners.movieworld.util.ImageDisplay;
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
public class MovieActivity extends AppCompatActivity {
    // key to pass data between activities
    public static String MOVIE_ACTIVITY_BUNDLE_KEY = "ADDITIONAL_MOVIE_INFO";
    // data for current movie (and for db)
    private MovieResultAppend mMovieResultAppend;
    // interface for rest calls
    private MovieApiMixedPaths mMovieApiMixed;
    // list that will hold reviews and trailers
    private MovieAdapterReview mMixedAdapterReview;
    private MovieAdapterVideo mMixedAdapterVideo;
    // TODO need to read database to set this later
    private boolean mFavSelection;
    // instance to database
    private MovieDatabase mDb;

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
        setSupportActionBar((Toolbar) findViewById(R.id.main_toolbar));
//        getMovieId();
        setUpRecyclerViews();
        createRestCall();
        getReviewsVideos();
        finalSetUp();
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
    private int getMovieId() {
        // get movie object json string
        return getIntent().getIntExtra(MOVIE_ACTIVITY_BUNDLE_KEY, -1);
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
                    mMovieResultAppend = response.body();
                    setUpToolbar();
                    setUpPageDetails();
                    setUpAdapterData();
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
        SimpleSnackBarBuilder.createAndDisplaySnackBar(findViewById(R.id.main_root_container),
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
                getMovieId(), (
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
        // set item_title of activity_toolbar for activity
        getSupportActionBar().setTitle(this.mMovieResultAppend.getTitle());
        getSupportActionBar().setSubtitle(this.mMovieResultAppend.getRelease_date());
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

        //TODO if already fav, change start below icon, based on what is in db

        final ImageView mFavStar = (ImageView) findViewById(R.id.favorite_star);

        mFavStar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!mFavSelection)
                        {
                            mFavStar.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_favorite_outline));
                            saveCurrentMovie();
                            displayMessage(R.string.movie_saved_message);
                        }
                        else
                        {
                            mFavStar.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_favorite_filled));




                        }

                        mFavSelection = !mFavSelection;
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

        if(mReviews == null || mReviews.size() == 0) {
            ((TextView) findViewById(R.id.no_reviews)).setVisibility(View.VISIBLE);
            ((RecyclerView) findViewById(R.id.movie_reviews)).setVisibility(View.GONE);

        }
        else
            mMixedAdapterReview.updateAdapter(mReviews);

        if(mVideos == null || mVideos.size() == 0) {
            ((TextView) findViewById(R.id.no_videos)).setVisibility(View.VISIBLE);
            ((RecyclerView) findViewById(R.id.movie_videos)).setVisibility(View.GONE);
        }
        else
            mMixedAdapterVideo.updateAdapter(mVideos);
    }

    /**
     * Fix for page loaded reviews and videos last causing the page to scroll to mid/bottom
     */
    private void finalSetUp()
    {
        // TODO change / fix later
        // fix for late loading elements scrolling bar to bottom instead of on top
        ((ScrollView) findViewById(R.id.movie_detail_root)).postDelayed(new Runnable() {
            @Override
            public void run() {
                ((ScrollView) findViewById(R.id.movie_detail_root)).fullScroll(ScrollView.FOCUS_UP);
            }
        },
        2000);
    }

    private void saveCurrentMovie()
    {
        MovieEntry mMovieEntry = new MovieEntry(mMovieResultAppend.getId(), mMovieResultAppend.getPoster_path(), new Date());
        mDb.getMovieDao().insertMovie(mMovieEntry);
    }
}

