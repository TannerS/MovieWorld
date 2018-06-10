package com.dev.tanners.movieworld.api.model.reviews;

import com.dev.tanners.movieworld.api.model.reviews.results.MovieReview;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;

/**
 * Movie review model class
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieReviewRoot {
    private int page;
    private int total_pages;
    private int total_results;
    private ArrayList<MovieReview> results;

    /**
     *
     */
    public MovieReviewRoot() {

    }

    /**
     * @return
     */
    public int getPage() {
        return page;
    }

    /**
     * @param page
     */
    public void setPage(int page) {
        this.page = page;
    }

    /**
     * @return
     */
    public int getTotal_pages() {
        return total_pages;
    }

    /**
     * @param total_pages
     */
    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    /**
     * @return
     */
    public int getTotal_results() {
        return total_results;
    }

    /**
     * @param total_results
     */
    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    /**
     * @return
     */
    public ArrayList<MovieReview> getResults() {
        return results;
    }

    /**
     * @param results
     */
    public void setResults(ArrayList<MovieReview> results) {
        this.results = results;
    }
}

