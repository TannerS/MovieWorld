package com.dev.tanners.movieworld;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.View;

import com.dev.tanners.movieworld.api.ApiBuilder;
import com.dev.tanners.movieworld.api.ApiBuilderUtil;
import com.dev.tanners.movieworld.api.adapter.MovieAdapter;
import com.dev.tanners.movieworld.api.callback.IImageBackDropUrlCallback;
import com.dev.tanners.movieworld.api.callback.IImageOnClickListener;
import com.dev.tanners.movieworld.api.model.MovieRoot;
import com.dev.tanners.movieworld.api.model.results.MovieResult;
import com.dev.tanners.movieworld.api.rest.RestRequest;
import com.dev.tanners.movieworld.api.rest.callback.IRestObjectMappingCallback;
import com.dev.tanners.movieworld.api.util.MovieUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<MovieRoot> {
    // number of grid columns
    private final int mColumns = 2;
    // key to pass data between activities
    public static String MOVIE_ACTIVITY_KEY = "ADDITIONAL_MOVIE_INFO";
    // unique id for loader
    private static final int MOVIE_DB_LOADER = 100;
    // unique id for loader bundle
    private static final int MOVIE_DB_LOADER_BUNDLE = 200;

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // set up loader
        getSupportLoaderManager().initLoader(MOVIE_DB_LOADER, null, this);

        setContentView(R.layout.activity_main);
        // set up recyclerview
        setUpRecycler();


        setUpLoader();
    }

    /**
     * Set up loader to make rest api call for recyclerview
     */
    private void setUpLoader()
    {
        // TODO url here
//        movieLoaderInit(MOVIE_DB_LOADER_BUNDLE, new URL(ApiBuilderUtil.PopularMoviesUrl().toString()));
        Bundle mUrlBundle = movieLoaderInit(MOVIE_DB_LOADER_BUNDLE, ApiBuilderUtil.PopularMoviesUrl().toString());
        // get loadermanager that holds the loaders
        LoaderManager loaderManager = getSupportLoaderManager();
        // check if loader exist and/or get it
        Loader<String> mMovieLoader = loaderManager.getLoader(MOVIE_DB_LOADER);
        // if null, start it
        if (mMovieLoader == null) {
            loaderManager.initLoader(MOVIE_DB_LOADER, mUrlBundle, this);
        // else restart it
        } else {
            loaderManager.restartLoader(MOVIE_DB_LOADER, mUrlBundle, this);
        }


    }

    /**
     * Load bundle for loader.
     * This can be used each time loader data has changed
     * In our case, the data is the rest api call (top or popular)
     *
     * @param mKey
     * @param mData
     */
    private Bundle movieLoaderInit(int mKey, String mData)
    {
        // bundle to pass into the loader
        Bundle mUrlBundle = new Bundle();
        // put in key value pair for bundle
        // TODO depending on top or popular, ths would need to change
        // it is preference to pass in an int and convert to string
        // I like int as key better then string
        mUrlBundle.putString(Integer.toString(mKey), mData);
        // return bundle
        return mUrlBundle;
    }



    private void setUpRecycler()
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
            null,
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
                    .append(ApiBuilder.API_IMAGE_BASE)
                    .append("/")
                    .append(ApiBuilder.API_IMAGE_SIZES.get(ApiBuilder.LARGE))
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
     * Static class to prevent
     * Warning: This AsyncTask class should be static or leaks might occur
     *
     * This is async loader to grab the movie data for the adapter
     *
     */
    private static class MovieAsyncTaskLoader extends AsyncTaskLoader<MovieRoot> {
        private Bundle mArgs;

        public MovieAsyncTaskLoader(@NonNull Context context, Bundle mArgs) {
            super(context);
            this.mArgs = mArgs;
        }

        @Override
        protected void onStartLoading() {
            // check for data to start with
            if (this.mArgs == null) {
                return;
            }
            // start initial load
            forceLoad();
        }

        /**
         * Task to do as background thread
         *
         * @return
         */
        @Override
        public MovieRoot loadInBackground() {
            // string to hold response
            MovieRoot mResponse = null;
            // get search url
            String mSearchUrl = this.mArgs.getString(Integer.toString(MOVIE_DB_LOADER_BUNDLE));
            // check for empty string
            if (mSearchUrl == null || TextUtils.isEmpty((CharSequence) mSearchUrl)) {
                return null;
            }

            // make new rest request
            RestRequest mRestRequest = new RestRequest();
            // create object mapping of returned json
            try {
                // get json response
                mResponse = mRestRequest.restJsonMapping(
                        new HashMap<String, String>() {{
                            put("Accept-Language", "en-US,en;q=0.5");
                            put("Connection", "keep-alive");
                            put("Accept", "text/html,application/xhtml+xm…plication/xml;q=0.9,*/*;q=0.8");
                            put("content-type", "application/x-www-form-urlencoded; charset=utf-8");
                            put("Content-Language", "en-US");
                        }},
                        // url to request
                        // default is popular movies
                        new URL(mArgs.getString(Integer.toString(MOVIE_DB_LOADER_BUNDLE))),
                        // body, if post
                        null,
                        /*
                            This is call back to map response to json object, this is able to take
                            any class for other uses, not just for this app
                        */
                        new IRestObjectMappingCallback()
                        {
                            @Override
                            public MovieRoot getMappedObject(String response) throws IOException {
                                ObjectMapper objectMapper = new ObjectMapper();
                                // map objects from json
                                // TODO TEST
                                return objectMapper.readValue(response, MovieRoot.class);
                            }
                        }
                );
            } catch (IOException e) {
                e.printStackTrace();
            }

            return mResponse;
        }
    }

    /**
     *
     * Based on
     * https://github.com/udacity/ud851-Exercises/blob/f85245b6eca3c4f5b99756e9ee12dc52db07f4ca/app/src/main/java/com/example/android/asynctaskloader/MainActivity.java
     * @param id
     * @param args
     * @return
     */
    @NonNull
    @Override
    public Loader<MovieRoot> onCreateLoader(int id, @Nullable final Bundle args) {
        // return new instance of the custom asynctaskloader
        return new MovieAsyncTaskLoader(MainActivity.this, args);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<MovieRoot> loader, MovieRoot mData) {
        if (null == mData) {
            // TODO SHOW ERROR SNACKBAR HERE
        } else {
        // TODO UPDATE ADAPTER HERE

        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<MovieRoot> loader) {
    // N/A
    }
}




