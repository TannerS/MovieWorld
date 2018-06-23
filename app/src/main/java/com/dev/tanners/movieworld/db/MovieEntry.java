package com.dev.tanners.movieworld.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import com.dev.tanners.movieworld.api.model.movies.MovieResult;
import com.dev.tanners.movieworld.db.config.DBConfig;

import java.util.Date;

@Entity(tableName = DBConfig.TABLE_NAME)
public class MovieEntry {
    @PrimaryKey(autoGenerate = true)
    // primary key
    protected int id;
    @ColumnInfo(name = "timestamp")
    protected Date timestamp;

//    @Ignore
//    public MovieEntry()
//    { }

//    public MovieEntry(int id, int movieId, float vote_average, String title, String poster_path, String backdrop_path, String overview, String release_date, Date timestamp) {
//        this.movieId = movieId;
//        this.vote_average = vote_average;
//        this.title = title;
//        this.poster_path = poster_path;
//        this.backdrop_path = backdrop_path;
//        this.overview = overview;
//        this.release_date = release_date;
//        this.id = id;
//        this.timestamp = timestamp;
//    }

//    @Ignore
//    public MovieEntry(int movieId, float vote_average, String title, String poster_path, String backdrop_path, String overview, String release_date, Date timestamp) {
//        this.movieId = movieId;
//        this.vote_average = vote_average;
//        this.title = title;
//        this.poster_path = poster_path;
//        this.backdrop_path = backdrop_path;
//        this.overview = overview;
//        this.release_date = release_date;
//        this.timestamp = timestamp;
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}

