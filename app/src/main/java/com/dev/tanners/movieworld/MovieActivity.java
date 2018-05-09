package com.dev.tanners.movieworld;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
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

        loadImage(this.mMovieResult.getPoster_path(), (ImageView) findViewById(R.id.poster_image));
        loadImage(this.mMovieResult.getBackdrop_path(), (ImageView) findViewById(R.id.backsplah_image));

        ((TextView) findViewById(R.id.plot_synopsis)).setText(this.mMovieResult.getOverview());
        ((TextView) findViewById(R.id.Title)).setText(this.mMovieResult.getTitle());
        ((TextView) findViewById(R.id.rating)).setText(String.valueOf(this.mMovieResult.getVote_average()));
        ((TextView) findViewById(R.id.release_date)).setText(this.mMovieResult.getRelease_date());
    }

    private void getActivityObjectFromJson() throws IOException {
        String mMoveResultJson = getIntent().getStringExtra(MOVIE_ACTIVITY_BUNDLE_KEY);
        ObjectMapper mMapper = new ObjectMapper();
        this.mMovieResult = mMapper.readValue(mMoveResultJson, MovieResult.class);
    }

    private void loadImage(String mUrl, ImageView mImageview)
    {
        Glide.with(this)
                .load(mUrl)
                .apply(new RequestOptions().fitCenter()
//                     TODO error place handler
//                            .error()
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC))
                .transition(new DrawableTransitionOptions().crossFade())
                .into(mImageview);

    }
}
