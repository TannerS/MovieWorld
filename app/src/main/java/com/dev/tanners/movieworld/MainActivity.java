package com.dev.tanners.movieworld;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import com.dev.tanners.movieworld.api.adapter.MovieAdapter;
import com.dev.tanners.movieworld.api.callback.IImageBackDropUrlCallback;
import com.dev.tanners.movieworld.api.callback.IImageOnClickListener;
import com.dev.tanners.movieworld.api.model.MovieRoot;
import com.dev.tanners.movieworld.api.model.results.MovieResult;
import com.dev.tanners.movieworld.api.MovieApi;
import com.dev.tanners.movieworld.api.util.MovieUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.HashMap;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class MainActivity extends AppCompatActivity{
    // number of grid columns
    private final int mColumns = 2;
    // key to pass data between activities
    public static String MOVIE_ACTIVITY_KEY = "ADDITIONAL_MOVIE_INFO";
    // unique id for loader
    private static final int MOVIE_DB_LOADER = 100;
    // unique id for loader bundle
    private static final int MOVIE_DB_LOADER_BUNDLE = 200;
    // retrofit interface object
    private Callback<MovieRoot> mResponseCallback;
    // interface for rest calls using retrofit
    private MovieApi mMovieApi;

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // set up callbacks for rest calls for recyclerview
        setUpRestCallback();
        // set up rest calls connection to json parser and callbacks per rest api endpoint
        createApi();
        // run callback and rest request in background as an initial start
        mMovieApi.getPopular(MovieApi.QUERIES).enqueue(mResponseCallback);




        // set up callback for popular method
//        mMovieApi.getPopular(MovieApi.QUERIES).enqueue(mResponseCallback);
        // set up callback for top method
//        mMovieApi.getTop(MovieApi.QUERIES).enqueue(mResponseCallback);
    }

    /**
     * Set up recyclerview
     */
    private void setUpRecycler(ArrayList<MovieResult> mMovieResults)
    {
       // call for data here
        RecyclerView mMovieRecyclerView = findViewById(R.id.activity_main_gridview);
        /*
            Credit for loading gridview
            https://stackoverflow.com/questions/40587168/simple-android-grid-example-using-recyclerview-with-gridlayoutmanager-like-the?utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa

            Credit for staggered items
            StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(3, LinearLayoutManager.VERTICAL);
         */
//        mMovieRecyclerView.setLayoutManager(new GridLayoutManager(this, mColumns));
        mMovieRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(mColumns, LinearLayoutManager.VERTICAL));
        // create adapter
        MovieAdapter mMovieAdapter = new MovieAdapter(
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
                    .append(MovieApi.API_IMAGE_BASE)
                    .append("/")
                    .append(MovieApi.LARGE)
                    .append("/")
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
                        String mMovieObjectJson = MovieUtil.movieObjectToJson(mMovieResult);
                        Intent intent = new Intent(MainActivity.this, MovieActivity.class);
                        intent.putExtra(MOVIE_ACTIVITY_KEY, mMovieObjectJson);
                        startActivity(intent);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                }
            }
        );
        // set adapter
        mMovieRecyclerView.setAdapter(mMovieAdapter);
    }

    /**
     https://stackoverflow.com/questions/42636247/how-can-i-return-data-in-method-from-retrofit-onresponse?utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa
     */
    private void createApi() {
        // set up json parser for rest call
        ObjectMapper mMapper = new ObjectMapper();
        // build rest api call
        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(MovieApi.API_BASE)
                .addConverterFactory(JacksonConverterFactory.create(mMapper))
                .build();
        // align object with api interface that says how to make calls
        mMovieApi = mRetrofit.create(MovieApi.class);
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
                Log.i("RETROFIT API CALL", "Error: " + t.getLocalizedMessage());
                t.printStackTrace();
                // TODO snackbar?
            }
        };
    }
}




