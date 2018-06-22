package com.dev.tanners.movieworld.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import com.dev.tanners.movieworld.db.config.DBConfig;

import java.util.Date;

@Entity(tableName = DBConfig.TABLE_NAME)
public class MovieEntry {
    @PrimaryKey(autoGenerate = true)
    // primary key
    private int id;
    // id for movie to look up onClick
    @ColumnInfo(name = "movie_id")
    private int movieId;
    // to show the image in list
    @ColumnInfo(name = "poster_url")
    private String posterUrl;
    // timestamp used for ordering
    @ColumnInfo(name = "timestamp")
    private Date timestamp;

    public MovieEntry(int id, int movieId, String posterUrl, Date timestamp) {
        this.id = id;
        this.movieId = movieId;
        this.posterUrl = posterUrl;
        this.timestamp = timestamp;
    }

    @Ignore
    public MovieEntry(int movieId, String posterUrl, Date timestamp) {
        this.id = id;
        this.movieId = movieId;
        this.posterUrl = posterUrl;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}

