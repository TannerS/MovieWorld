package com.dev.tanners.movieworld;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import com.dev.tanners.movieworld.api.MovieApiBase;
import com.dev.tanners.movieworld.api.model.reviews.MovieReviewRoot;
import com.dev.tanners.movieworld.api.support.lists.MovieApiList;
import com.dev.tanners.movieworld.api.model.list.results.MovieResult;
import com.dev.tanners.movieworld.api.support.lists.paths.MovieApiListPaths;
import com.dev.tanners.movieworld.api.support.reviews.MovieApiReviews;
import com.dev.tanners.movieworld.api.support.reviews.paths.MovieApiReviewsPaths;
import com.dev.tanners.movieworld.util.ImageDisplay;
import com.dev.tanners.movieworld.util.SimpleSnackBarBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.HashMap;
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
    private MovieResult mMovieResult;
    // retrofit interface object
    private Callback<MovieReviewRoot> mReviewResponseCallback;
    // interface for rest calls
    private MovieApiReviewsPaths mMovieApiReviewsPaths;

    /**
     * Entry point to load methods needed for activity
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        // load toolbar
        setSupportActionBar( (Toolbar) findViewById(R.id.main_toolbar));
        // extract json of Movie object for UI from previous activity
        try {
            getActivityObjectFromJson();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // call helper method to load images
        // since image is passed in as realtive path
        // and not absolute
        ImageDisplay.loadImage(
                this,
                MovieApiList.formatPathToRestPath(
                        mMovieResult.getPoster_path(),
                        MovieApiList.MEDIUM
                ),
                R.drawable.ic_error,
                (ImageView) findViewById(R.id.poster_image)
        );
        ImageDisplay.loadImage(
                this,
                MovieApiList.formatPathToRestPath(
                        mMovieResult.getBackdrop_path(),
                        MovieApiList.MEDIUM
                ),
                R.drawable.ic_error,
                (ImageView) findViewById(R.id.backsplah_image));
        // load views
        ((TextView) findViewById(R.id.plot_synopsis)).setText(this.mMovieResult.getOverview());
        ((TextView) findViewById(R.id.rating)).setText((String.valueOf(this.mMovieResult.getVote_average()) + " / 10"));
        ((TextView) findViewById(R.id.release_date)).setText(this.mMovieResult.getRelease_date());

        // set title of toolbar for activity
        getSupportActionBar().setTitle(this.mMovieResult.getTitle());

        // set up callbacks for rest calls for recyclerview
        setUpReviewRestCallback();
        // set up rest calls connection to json parser and callbacks per rest api endpoint
        createApiReviewCall();
        // load reviews
        loadReviewList((new MovieApiReviews(this)).queries);
    }

    /**
     * Get json bundle to object to load UI elements
     *
     * @throws IOException
     */
    private void getActivityObjectFromJson() throws IOException {
        // get movie object json string
        String mMoveResultJson = getIntent().getStringExtra(MOVIE_ACTIVITY_BUNDLE_KEY);
        // create object mapper
        ObjectMapper mMapper = new ObjectMapper();
        // map json to object
        this.mMovieResult = mMapper.readValue(mMoveResultJson, MovieResult.class);
    }

    /**
     * Callback for movie review
     */
    private void setUpReviewRestCallback()
    {
        mReviewResponseCallback = new Callback<MovieReviewRoot>() {
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
            public void onResponse(Call<MovieReviewRoot> call, Response<MovieReviewRoot> response) {
                if (response.isSuccessful()) {
                    // set up recyclerview
//                    setUpRecycler(response.body().getResults());
//                    mMovieAdapter.updateAdapter(response.body().getResults());
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
            public void onFailure(Call<MovieReviewRoot> call, Throwable t) {
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
        https://stackoverflow.com/questions/42636247/how-can-i-return-data-in-method-from-retrofit-onresponse?utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa
     */
    protected void createApiReviewCall() {
        // set up json parser for rest call
        ObjectMapper mMapper = new ObjectMapper();
        // build rest api call
        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(MovieApiBase.API_BASE)
                .addConverterFactory(JacksonConverterFactory.create(mMapper))
                .build();
        // align object with api interface that says how to make calls
        mMovieApiReviewsPaths = mRetrofit.create(MovieApiReviewsPaths.class);
    }

    /**
     * Choose which list to load
     */
    protected void loadReviewList(HashMap<String, String> mQueries)
    {
        // run callback and rest request in background as an initial start
        mMovieApiReviewsPaths.getReviews(mQueries).enqueue(mReviewResponseCallback);
    }
}
