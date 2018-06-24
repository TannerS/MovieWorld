package com.dev.tanners.movieworld;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import com.dev.tanners.movieworld.api.model.movies.MovieResult;
import com.dev.tanners.movieworld.db.MovieDatabase;

import java.util.List;

public class FavoritesViewModel extends AndroidViewModel {
    private LiveData<List<MovieResult>> mMovies;

    public FavoritesViewModel(@NonNull Application application) {
        super(application);
        MovieDatabase mMovieDatabase = MovieDatabase.getInstance(this.getApplication());
        mMovies = mMovieDatabase.getMovieDao().loadAllFavoriteMovies();
    }

    /**
     * Get movies
     *
     * @return
     */
    public LiveData<List<MovieResult>> getMovies()
    {
        return mMovies;
    }

}
