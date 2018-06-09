package com.dev.tanners.movieworld.api.model.movie;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;

/**
 * Movie model for popular and top rated movies
 */
@JsonIgnoreProperties(ignoreUnknown = true)

public class MovieRoot {
    private int page;
    private int total_results;
    private int total_pages;
    private ArrayList<MovieResult> results;

    public MovieRoot() {
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public ArrayList<MovieResult> getResults() {
        return results;
    }

    public void setResults(ArrayList<MovieResult> results) {
        this.results = results;
    }

}