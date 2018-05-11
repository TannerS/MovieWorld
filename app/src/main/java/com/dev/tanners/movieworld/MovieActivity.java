package com.dev.tanners.movieworld;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import com.dev.tanners.movieworld.api.model.results.MovieResult;
import com.dev.tanners.movieworld.util.ImageDisplay;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * Details base class
 */
public class MovieActivity extends AppCompatActivity {
    // key to pass data between activities
    public static String MOVIE_ACTIVITY_BUNDLE_KEY = "ADDITIONAL_MOVIE_INFO";
    // data for current movie
    private MovieResult mMovieResult;

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
        ImageDisplay.loadImage(this, this.mMovieResult.getPoster_path(), R.drawable.ic_error, (ImageView) findViewById(R.id.poster_image));
        ImageDisplay.loadImage(this, this.mMovieResult.getBackdrop_path(), R.drawable.ic_error, (ImageView) findViewById(R.id.backsplah_image));

        // load views
        ((TextView) findViewById(R.id.plot_synopsis)).setText(this.mMovieResult.getOverview());
        ((TextView) findViewById(R.id.rating)).setText(String.valueOf(this.mMovieResult.getVote_average()));
        ((TextView) findViewById(R.id.release_date)).setText(this.mMovieResult.getRelease_date());

        // set title of toolbar for activity
        getSupportActionBar().setTitle(this.mMovieResult.getTitle());
    }

    /**
     * Get json bundle to object to load UI elements
     *
     * @throws IOException
     */
    private void getActivityObjectFromJson() throws IOException {
        String mMoveResultJson = getIntent().getStringExtra(MOVIE_ACTIVITY_BUNDLE_KEY);
        ObjectMapper mMapper = new ObjectMapper();
        this.mMovieResult = mMapper.readValue(mMoveResultJson, MovieResult.class);
    }
}
