package com.dev.tanners.movieworld.api.model.movie;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieResult
{
    protected int id;
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
