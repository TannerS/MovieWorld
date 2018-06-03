package com.dev.tanners.movieworld.api.model.reviews;

import com.dev.tanners.movieworld.api.model.reviews.results.MovieReview;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;

/**
 * Movie review model class
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieReviewRoot {
    private int id;
    private int page;
    private int total_pages;
    private int total_results;
    private ArrayList<MovieReview> results;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    public ArrayList<MovieReview> getResults() {
        return results;
    }
    public void setResults(ArrayList<MovieReview> results) {
        this.results = results;
    }

    public MovieReviewRoot() {

    }
}

