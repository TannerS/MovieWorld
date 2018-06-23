package com.dev.tanners.movieworld.api.model.movies;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.dev.tanners.movieworld.db.MovieEntry;
import com.dev.tanners.movieworld.db.TimestampConverter;
import com.dev.tanners.movieworld.db.config.DBConfig;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * Movie model
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity(tableName = DBConfig.TABLE_NAME)
@TypeConverters(TimestampConverter.class)
public class MovieResult extends MovieEntry
{
    @PrimaryKey
    protected int id;
    protected float vote_average;
    protected String title;
    protected String poster_path;
    protected String backdrop_path;
    protected String overview;
    protected String release_date;
    // used for UI to keep track of favorite movies and non favorite movies
    // this will always be true in db, but other movies that use this class use it
    protected boolean is_favorite;

    /**
     * Constructor
     * used for json serialization/de-serialization
     */
    @Ignore
    public MovieResult() {
        // for json de-serialization
    }

    /**
     * Constructor
     *
     * Used for db entity
     *
     * @param id
     * @param vote_average
     * @param title
     * @param poster_path
     * @param backdrop_path
     * @param overview
     * @param release_date
     * @param timestamp
     * @param is_favorite
     */
    public MovieResult(int id, float vote_average, String title, String poster_path, String backdrop_path, String overview, String release_date, Date timestamp, boolean is_favorite) {
        this.id = id;
        this.vote_average = vote_average;
        this.title = title;
        this.poster_path = poster_path;
        this.backdrop_path = backdrop_path;
        this.overview = overview;
        this.release_date = release_date;
        this.timestamp = timestamp;
        this.is_favorite = is_favorite;
    }

    /**
     * Constructor
     *
     * Used for internal
     *
     * @param vote_average
     * @param title
     * @param poster_path
     * @param backdrop_path
     * @param overview
     * @param release_date
     * @param timestamp
     * @param is_favorite
     */
    @Ignore
    public MovieResult(float vote_average, String title, String poster_path, String backdrop_path, String overview, String release_date, Date timestamp, boolean is_favorite) {
        this.vote_average = vote_average;
        this.title = title;
        this.poster_path = poster_path;
        this.backdrop_path = backdrop_path;
        this.overview = overview;
        this.release_date = release_date;
        this.timestamp = timestamp;
        this.is_favorite = is_favorite;
    }

    /**
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return
     */
    public boolean isIs_favorite() {
        return is_favorite;
    }

    /**
     * @param is_favorite
     */
    public void setIs_favorite(boolean is_favorite) {
        this.is_favorite = is_favorite;
    }



    /**
     * @return
     */
    public float getVote_average() {
        return vote_average;
    }

    /**
     * @param vote_average
     */
    public void setVote_average(float vote_average) {
        this.vote_average = vote_average;
    }

    /**
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return
     */
    public String getPoster_path() {
        return poster_path;
    }

    /**
     * @param poster_path
     */
    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    /**
     * @return
     */
    public String getBackdrop_path() {
        return backdrop_path;
    }

    /**
     * @param backdrop_path
     */
    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    /**
     * @return
     */
    public String getOverview() {
        return overview;
    }

    /**
     * @param overview
     */
    public void setOverview(String overview) {
        this.overview = overview;
    }

    /**
     * @return
     */
    public String getRelease_date() {
        return release_date;
    }

    /**
     * @param release_date
     */
    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }
}
