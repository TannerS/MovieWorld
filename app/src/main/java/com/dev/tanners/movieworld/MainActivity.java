package com.dev.tanners.movieworld;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import java.util.HashMap;

import com.dev.tanners.movieworld.api.model.MovieRoot;
import com.dev.tanners.movieworld.api.rest.RestRequest;
import com.dev.tanners.movieworld.api.rest.callback.IRestObjectMappingCallback;
import com.dev.tanners.movieworld.api.util.JsonUtil;
import com.dev.tanners.movieworld.api.adapter.MovieAdapter;
import com.dev.tanners.movieworld.api.callback.IImageBackDropUrlCallback;
import com.dev.tanners.movieworld.api.callback.IImageOnClickListener;
import com.dev.tanners.movieworld.api.rest.config.RestConfig;
import com.dev.tanners.movieworld.api.model.results.MovieResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    // number of grid columns
    private final int mColumns = 2;
    public static String MOVIE_ACTIVITY_KEY = "ADDITIONAL_MOVIE_INFO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpRecycler();
    }

    // TODO into asynctask
    private void getGridData() throws IOException {
        String tempUrl = "";
        // make new rest request
        RestRequest mRestRequest = new RestRequest();
        // create object mapping of returned json
        mRestRequest.restJsonMapping(
            new HashMap<String, String>() {{
                // ADD HEADERS HERE
                }},
                // url to request
            "URL HERE",
                // body, if post
                null,
                /*
                    This is call back to map response to json object, this is able to take
                    any class for other uses, not just for this app
                */
                new IRestObjectMappingCallback()
                {
                    @Override
                    public List<MovieRoot> getMappedObject(String response) throws IOException {
                        ObjectMapper objectMapper = new ObjectMapper();
                        // map objects from json
                        // TODO TEST
                        return (List<MovieRoot>) objectMapper.readValue(response, MovieRoot.class);
                    }
                }
        );
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
                    .append(RestConfig.API_IMAGE_BASE)
                    .append("/")
                    .append(RestConfig.API_IMAGE_SIZES.get(RestConfig.LARGE))
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
                        String mMovieObjectJson = JsonUtil.movieObjectToJson(mMovieResult);
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
}




