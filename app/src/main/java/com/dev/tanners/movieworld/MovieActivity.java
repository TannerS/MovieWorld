package com.dev.tanners.movieworld;

import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.dev.tanners.movieworld.api.adapter.MixedAdapterBase;
import com.dev.tanners.movieworld.api.adapter.MixedAdapterReview;
import com.dev.tanners.movieworld.api.adapter.MixedAdapterVideo;
import com.dev.tanners.movieworld.api.model.movie.Movie;
import com.dev.tanners.movieworld.api.model.reviews.results.MovieReview;
import com.dev.tanners.movieworld.api.model.videos.results.MovieVideo;
import com.dev.tanners.movieworld.api.support.rest.MovieApiBase;
import com.dev.tanners.movieworld.api.support.rest.MovieApiList;
import com.dev.tanners.movieworld.api.support.rest.paths.MovieApiMixedPaths;
import com.dev.tanners.movieworld.util.ImageDisplay;
import com.dev.tanners.movieworld.util.SimpleSnackBarBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;

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
    // data for current movie
    private Movie mMovie;
    // interface for rest calls
    private MovieApiMixedPaths mMovieApiMixed;
    // list that will hold reviews and trailers
    private MixedAdapterReview mMixedAdapterReview;
    private MixedAdapterVideo mMixedAdapterVideo;
    private int mMovieId;
    private boolean mFavSelection;

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
        getActivityExtra();
        setUpRecyclerViews();
        createRestCall();
        getReviewsVideos();
    }

    private void setUpRecyclerViews()
    {
        mMixedAdapterReview = new MixedAdapterReview(this);
        mMixedAdapterVideo = new MixedAdapterVideo(this);

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
    private void getActivityExtra() {
        // get movie object json string
        mMovieId = getIntent().getIntExtra(MOVIE_ACTIVITY_BUNDLE_KEY, -1);
    }

    /**
     * Callback for movie review
     */
    private Callback<Movie> setUpMovieCallback()
    {
        return new Callback<Movie>() {
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
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if (response.isSuccessful()) {
                    mMovie = response.body();
                    setUpToolbar();
                    setUpPageDetails();
                    setUpAdapterData();
                } else {
                    displayError(R.string.loading_reviews_error);
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
            public void onFailure(Call<Movie> call, Throwable t) {
                t.printStackTrace();
                displayError(R.string.loading_reviews_error);
            }
        };
    }

    /**
     * Error snackbar
     */
    private void displayError(int mStringId) {
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
    private void setUpRecycler(MixedAdapterBase mAdapter, int mLayout)
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

    private void getReviewsVideos()
    {
        // run callback and rest request in background as an initial start
        mMovieApiMixed.getReviewsVideos(
                mMovieId, (
                        new com.dev.tanners.movieworld.api.support.rest.MovieApiMixed(this)
                ).queries
        ).enqueue (
                setUpMovieCallback()
        );
    }

    private void setUpToolbar()
    {
        // set item_title of activity_toolbar for activity
        getSupportActionBar().setTitle(this.mMovie.getTitle());
        getSupportActionBar().setSubtitle("What is this for?");
    }

    private void setUpPageDetails()
    {
        // load views
        ((TextView) findViewById(R.id.plot_synopsis)).setText(this.mMovie.getOverview());
        ((TextView) findViewById(R.id.rating)).setText((String.valueOf(this.mMovie.getVote_average()) + " / 10"));
        ((TextView) findViewById(R.id.release_date)).setText(this.mMovie.getRelease_date());

        // call helper method to load images
        // since image is passed in as relative path
        // and not absolute
        ImageDisplay.loadImage(
                this,
                MovieApiList.formatPathToRestPath(
                        mMovie.getBackdrop_path(),
                        MovieApiList.MEDIUM
                ),
                R.drawable.ic_error,
                (ImageView) findViewById(R.id.backsplash_image)
        );

        ((ImageView) findViewById(R.id.favorite_star)).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                     // TODO open intent, save info
                    }
                }
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
                            //TODO do more
                        }
                        else
                        {
                            mFavStar.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_favorite_filled));
                            //TODO do more
                        }

                        mFavSelection = !mFavSelection;
                    }
                }
        );

    }

    private void setUpAdapterData()
    {
        ArrayList<MovieReview> mReviews = mMovie.getReviews().getResults();
        ArrayList<MovieVideo> mVideos = mMovie.getVideos().getResults();

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
}

