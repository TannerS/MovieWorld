package com.dev.tanners.movieworld.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.dev.tanners.movieworld.api.rest.MovieApi;
import com.dev.tanners.movieworld.api.rest.MovieApiListPaths;
import com.dev.tanners.movieworld.api.rest.MovieApiBase;
import com.dev.tanners.movieworld.api.model.movies.MovieResultBase;
import com.fasterxml.jackson.databind.ObjectMapper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Base fragment class for common functionality for sub classes
 * */
public class MovieFragmentNetwork extends MovieFragmentList {
    // retrofit interface object
    protected Callback<MovieResultBase> mResponseCallback;
    // interface for rest calls using retrofit
    protected MovieApiListPaths mMovieApiListPaths;
    // interface for rest calls
    protected MovieApi mMovieApi;


    /**
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = super.onCreateView(inflater, container, savedInstanceState);
        // set up rest helper
        mMovieApi = new MovieApi(getContext());
        // return view
        return view;
    }

    /**
     * Load restful functionality
     *
     * @param mOnResultCallback
     */
    protected void loadRest(OnResultCallback mOnResultCallback)
    {
        // set up callbacks for rest calls for recyclerview
        setUpRestCallback(mOnResultCallback);
        // set up rest calls connection to json parser and callbacks per rest api endpoint
        createApiCall();
        // set up rest helper
    }

    /**
     https://stackoverflow.com/questions/42636247/how-can-i-return-data-in-method-from-retrofit-onresponse?utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa
     */
    protected void createApiCall() {
        // set up json parser for rest call
        ObjectMapper mMapper = new ObjectMapper();
        // build rest api call
        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(MovieApiBase.API_BASE)
                .addConverterFactory(JacksonConverterFactory.create(mMapper))
                .build();
        // align object with api interface that says how to make calls
        mMovieApiListPaths = mRetrofit.create(MovieApiListPaths.class);
    }

    /**
     * Callback for each rest url (top/popular)
     */
    public void setUpRestCallback(final OnResultCallback mOnResultCallback)
    {
        mResponseCallback = new Callback<MovieResultBase>() {
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
            public void onResponse(Call<MovieResultBase> call, Response<MovieResultBase> response) {
                if (response.isSuccessful()) {
                    // set up recyclerview
                    mMovieAdapter.updateAdapterAdded(response.body().getResults());
                    mOnResultCallback.onPostResults();
                } else {
                    displayError();
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
            public void onFailure(Call<MovieResultBase> call, Throwable t) {
                t.printStackTrace();
                displayError();
            }
        };
    }

    /**
     * OnClick callback for list updating
     */
    public interface OnResultCallback {
        /**
         * execute
         */
        public void onPostResults();
    }
}
