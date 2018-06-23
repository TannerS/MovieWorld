package com.dev.tanners.movieworld.api.model.movies;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.dev.tanners.movieworld.db.config.DBConfig;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * Movie model
 */
@Entity(tableName = DBConfig.TABLE_NAME)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieResult
{
    @ColumnInfo(name = "movie_id")
    protected int movieId;
    protected float vote_average;
    protected String title;
    protected String poster_path;
    protected String backdrop_path;
    protected String overview;
    protected String release_date;

    /**
     *
     */
    public MovieResult() {
    }

    /**
     * @return
     */
    @JsonProperty("id")
    public int getMovieId() {
        return movieId;
    }

    /**
     * @param movieId
     */
    public void setMovieId(int movieId) {
        this.movieId = movieId;
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
