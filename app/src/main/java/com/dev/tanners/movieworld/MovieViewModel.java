package com.dev.tanners.movieworld;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.NonNull;
import android.util.Log;

import com.dev.tanners.movieworld.api.model.movies.MovieResult;
import com.dev.tanners.movieworld.api.model.movies.MovieResultBase;
import com.dev.tanners.movieworld.api.rest.MovieApi;
import com.dev.tanners.movieworld.api.rest.MovieApiBase;
import com.dev.tanners.movieworld.api.rest.MovieApiListPaths;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * DISCLAIMER ***********************
 * Originally, this functionality was in its own activity, but wanting it to work with viewmodel,
 * would fix the caching issue on device rotation, this was implemented, but after research, it was known
 * that viewmodel can help prevent mem leaks on activities destroyed when doing async network calls.
 * With that said, I had to move all network functionality to this class to make this valid.
 * There may be a better way to implement this later, but for now this works until a future version gets released
 */
public class MovieViewModel extends AndroidViewModel {
    private List<MovieResult> mMovies;
    // retrofit interface object
    protected Callback<MovieResultBase> mResponseCallback;
    // interface for rest calls using retrofit
    protected MovieApiListPaths mMovieApiListPaths;
    // interface for rest calls
    protected MovieApi mMovieApi;
    public static final String SCROLL_PLACEMENT = "SCROLL_POSITION";
    public static final String INIT_DATA_CALL = "INIT_DATA_SET";

    /**
     * Constructor
     *
     * @param application
     */
    public MovieViewModel(@NonNull Application application) {
        super(application);
        mMovies = new ArrayList<MovieResult>();
        mMovieApi = new MovieApi(getApplication().getApplicationContext());
    }

    /**
     * Get movies
     *
     * @return
     */
    public List<MovieResult> getMovies()
    {
        return mMovies;
    }

    /**
     * Add data
     *
     * @param mMovies
     */
    public void addData(List<MovieResult> mMovies)
    {
        this.mMovies.addAll(mMovies);
    }

    /**
     * Load restful functionality
     *
     * @param mOnResultCallback
     */
    public void loadRest(OnResultCallback mOnResultCallback)
    {
        // set up callbacks for rest calls for recyclerview
        setUpRestCallback(mOnResultCallback);
        // set up rest calls connection to json parser and callbacks per rest api endpoint
        createApiCall();
    }

    /**
     https://stackoverflow.com/questions/42636247/how-can-i-return-data-in-method-from-retrofit-onresponse?utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa
     */
    public void createApiCall() {
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
     * load popular movies
     */
    public void getPopular()
    {
        mMovieApiListPaths.getPopular(
                mMovieApi.getQueries()
        ).enqueue(
                mResponseCallback
        );
    }

    /**
     * load top rated movies
     */
    public void getTop()
    {
        mMovieApiListPaths.getTop(
                mMovieApi.getQueries()
        ).enqueue(
                mResponseCallback
        );
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
                    // add new movies to viewmodel
                    addData(response.body().getResults());
                    // call callback after getting data
                    mOnResultCallback.onPostResults();
                } else {
                    // TODO create snackbar for error
//                    displayError();
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
                // TODO create snackbar for error
//                displayError();
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

    /**
     * increase restful api call's page
     */
    public void increasePage()
    {
        mMovieApi.increasePage();
    }
}
