package com.dev.tanners.movieworld;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.dev.tanners.movieworld.api.Util.JsonUtil;
import com.dev.tanners.movieworld.api.adapter.MovieAdapter;
import com.dev.tanners.movieworld.api.callback.IImageBackDropUrlCallback;
import com.dev.tanners.movieworld.api.callback.IImageOnClickListener;
import com.dev.tanners.movieworld.api.config.MovieConfig;
import com.dev.tanners.movieworld.api.model.results.MovieResult;
import com.fasterxml.jackson.core.JsonProcessingException;

public class MainActivity extends AppCompatActivity {
    private final int mColumns = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpRecycler();
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

        MovieAdapter mMovieAdapter = new MovieAdapter(
            this,
            null/* TODO, pas url here for image? */,
            new IImageBackDropUrlCallback() {
                @Override
                public String formatUrl(String mUrl) {
                    return (new StringBuilder())
                    .append(MovieConfig.API_IMAGE_BASE)
                    .append("/")
                    .append(MovieConfig.API_IMAGE_SIZES.get(MovieConfig.LARGE))
                    .append("/")
                    .append(mUrl).toString();
                }
            },
            new IImageOnClickListener() {
                @Override
                public void onClick(MovieResult mMovieResult) {
                    try {
                        String mMovieObjectJson = JsonUtil.movieObjectToJson(mMovieResult);
                        Intent intent = new Intent(MainActivity.this, MovieActivity.class);
                        intent.putExtra(MovieConfig.ACTIVITY_KEY, mMovieObjectJson);
                        startActivity(intent);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                }
            }
        );

        mMovieRecyclerView.setAdapter(mMovieAdapter);


        // TODO add https://stackoverflow.com/questions/26543131/how-to-implement-endless-list-with-recyclerview?utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa
//            // depending on the version of the OS, add listener to the recycler view
//            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP_MR1) {
//                mRecyclerView.addOnScrollListener(getListener());
//            }
//            else
//            {
//                mRecyclerView.setOnScrollListener(getListener());
//            }
//        }

    }



}
