package com.dev.tanners.movieworld.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.dev.tanners.movieworld.db.config.DBConfig;

import java.sql.Date;

@Entity(tableName = DBConfig.TABLE_NAME)
public class MovieEntry {
    @PrimaryKey(autoGenerate = true)
    // primary key
    private int id;
    // id for movie to look up onClick
    @ColumnInfo(name = "movie_id")
    private String mMovieId;
    // to show the image in list
    @ColumnInfo(name = "poster_url")
    private String poster_url;
    // timestamp used for ordering
    @ColumnInfo(name = "timestamp")
    private Date timestamp;

    @Ignore
    public MovieEntry(String mMovieId, String poster_url, Date timestamp) {
        this.mMovieId = mMovieId;
        this.poster_url = poster_url;
        this.timestamp = timestamp;
    }

    public MovieEntry(int id, String mMovieId, String poster_url, Date timestamp) {
        this.id = id;
        this.mMovieId = mMovieId;
        this.poster_url = poster_url;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getmMovieId() {
        return mMovieId;
    }

    public void setmMovieId(String mMovieId) {
        this.mMovieId = mMovieId;
    }

    public String getPoster_url() {
        return poster_url;
    }

    public void setPoster_url(String poster_url) {
        this.poster_url = poster_url;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}

