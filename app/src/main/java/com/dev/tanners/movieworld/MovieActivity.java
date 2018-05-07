package com.dev.tanners.movieworld;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.dev.tanners.movieworld.api.model.results.MovieResult;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class MovieActivity extends AppCompatActivity {
    // key to pass data between activities
    public static String MOVIE_ACTIVITY_BUNDLE_KEY = "ADDITIONAL_MOVIE_INFO";
    // data for current movie
    private MovieResult mMovieResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        try {
            getActivityObjectFromJson();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }

    private void getActivityObjectFromJson() throws IOException {
        String mMoveResultJson = getIntent().getStringExtra(MOVIE_ACTIVITY_BUNDLE_KEY);
        ObjectMapper mMapper = new ObjectMapper();
        this.mMovieResult = mMapper.readValue(mMoveResultJson, MovieResult.class);
    }
}
