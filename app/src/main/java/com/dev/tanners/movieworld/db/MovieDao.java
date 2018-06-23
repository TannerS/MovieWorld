package com.dev.tanners.movieworld.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.dev.tanners.movieworld.api.model.movies.MovieResult;
import com.dev.tanners.movieworld.db.config.DBConfig;

import java.util.List;

@Dao
public interface MovieDao {
    /**
     * Load all favorite movies in db
     *
     * @return
     */
    @Query(DBConfig.GET_ALL_MOVIES_QUERY)
//    LiveData<List<MovieResult>> loadAllFavoriteMovies();
    List<MovieResult> loadAllFavoriteMovies();

    /**
     * Insert new favorite movie
     *
     * @param mMovieEntry
     */
    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insertMovie(MovieResult mMovieEntry);

    /**
     * Update movie object in db by replacing it
     *
     * @param mMovieEntry
     */
    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateMovie(MovieResult mMovieEntry);

    /**
     * Delete a favorite movie
     *
     * @param mMovieEntry
     */
    @Delete
    void deleteMovie(MovieResult mMovieEntry);

    /**
     * Get favorite movie by id
     *
     * @param id
     * @return
     */
    @Query(DBConfig.GET_MOVIE_BY_ID_QUERY)
    LiveData<MovieResult> loadMovieById(int id);
}



